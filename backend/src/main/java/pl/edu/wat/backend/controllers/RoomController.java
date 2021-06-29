package pl.edu.wat.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.backend.dtos.RoomDto;
import pl.edu.wat.backend.dtos.RoomPhotoDto;
import pl.edu.wat.backend.services.RoomService;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
public class RoomController {
    private final RoomService roomService;
    private final ServletContext servletContext;

    @Autowired
    public RoomController(RoomService roomService, ServletContext servletContext) {
        this.roomService = roomService;
        this.servletContext = servletContext;
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDto>> getRooms() {
        return new ResponseEntity<>(roomService.getRooms(), HttpStatus.OK);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity getRoomById(@PathVariable("id") Integer id) {
        RoomDto roomDto = roomService.getRoomById(id);
        if (roomDto == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    @PostMapping("/rooms")
    public ResponseEntity addRoom(@RequestParam("photo") MultipartFile file, @RequestParam("room") String room) throws IOException {
        RoomDto roomDto = new ObjectMapper().readValue(room, RoomDto.class);

        boolean exist = new File(servletContext.getRealPath("/room-photos")).exists();
        if (!exist) {
            new File(servletContext.getRealPath("/room-photos")).mkdir();
        }

        String fileName = file.getOriginalFilename();
        String modifiedFileName = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
        File serverFile = new File(servletContext.getRealPath("/room-photos/" + File.separator + modifiedFileName));
        try {
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        roomDto.setPhotoFileName(modifiedFileName);
        if (roomService.addRoom(roomDto)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/rooms/{roomId}")
    public ResponseEntity updateRoom(@PathVariable("roomId") Integer roomId,@RequestParam("photo") MultipartFile file, @RequestParam("room") String room) throws IOException {
        RoomDto roomDto = new ObjectMapper().readValue(room, RoomDto.class);

        boolean exist = new File(servletContext.getRealPath("/room-photos")).exists();
        if (!exist) {
            new File(servletContext.getRealPath("/room-photos")).mkdir();
        }

        String fileName = file.getOriginalFilename();
        String modifiedFileName = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
        File serverFile = new File(servletContext.getRealPath("/room-photos/" + File.separator + modifiedFileName));
        try {
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        roomDto.setRoomId(roomId);
        roomDto.setPhotoFileName(modifiedFileName);
        roomService.updateRoom(roomDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity deleteRoom(@PathVariable("id") Integer id) {
        roomService.deleteRoomById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("rooms/photos")
    public ResponseEntity<List<RoomPhotoDto>> getRoomPhotos() {
        List<RoomPhotoDto> roomPhotoDtos = new ArrayList<>();
        String path = servletContext.getRealPath("/room-photos");
        File fileFolder = new File(path);
        if (fileFolder != null) {
            for (File file : fileFolder.listFiles()) {
                if (!file.isDirectory()) {
                    String encodeBase64 = null;
                    try {
                        String extension = FilenameUtils.getExtension(file.getName());
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] bytes = new byte[(int) file.length()];
                        fileInputStream.read(bytes);
                        encodeBase64 = Base64.getEncoder().encodeToString(bytes);
                        roomPhotoDtos.add(new RoomPhotoDto(file.getName(), "data:image/" + extension + ";base64," + encodeBase64));
                        fileInputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new ResponseEntity<>(roomPhotoDtos, HttpStatus.OK);
    }
}
