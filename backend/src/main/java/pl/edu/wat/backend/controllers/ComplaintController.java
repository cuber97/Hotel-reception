package pl.edu.wat.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.backend.dtos.ComplaintDto;
import pl.edu.wat.backend.dtos.CustomerDto;
import pl.edu.wat.backend.services.ComplaintService;

import java.util.List;

@RestController
public class ComplaintController {
    private final ComplaintService complaintService;

    @Autowired
    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintDto>> getAllComplaints() {
        List<ComplaintDto> complaintDtos = complaintService.getComplaints();
        return new ResponseEntity<>(complaintDtos, HttpStatus.OK);
    }

    @GetMapping("/complaints/customer/{customerId}")
    public ResponseEntity<List<ComplaintDto>> getComplaintsForCustomer(@PathVariable("customerId") Integer customerId) {
        List<ComplaintDto> complaintDtos = complaintService.getComplaintsForCustomer(customerId);
        return new ResponseEntity<>(complaintDtos, HttpStatus.OK);
    }

    @GetMapping("/complaints/{complaintId}")
    public ResponseEntity getComplaint(@PathVariable("complaintId") Integer complaintId) {
        ComplaintDto complaintDto = complaintService.getComplaintById(complaintId);
        if (complaintDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(complaintDto, HttpStatus.OK);
    }

    @PostMapping("/complaints")
    public ResponseEntity addComplaint(@RequestBody ComplaintDto complaintDto) {
        if (complaintService.addComplaint(complaintDto)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/complaints")
    public ResponseEntity updateComplaint(@RequestBody ComplaintDto complaintDto) {
        complaintService.updateComplaint(complaintDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/complaints/{complaintId}")
    public ResponseEntity deleteComplaint(@PathVariable("complaintId") Integer complaintId) {
        complaintService.deleteComplaintById(complaintId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
