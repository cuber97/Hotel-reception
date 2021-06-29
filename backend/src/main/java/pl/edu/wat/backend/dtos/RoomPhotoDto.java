package pl.edu.wat.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomPhotoDto {
    private String photoName;
    private String photo;
}
