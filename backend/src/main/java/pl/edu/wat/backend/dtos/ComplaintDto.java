package pl.edu.wat.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class ComplaintDto {
    private Integer complaintId;
    private Date creationDate;
    private Date lastUpdateDate;
    private Boolean hasBeenRead;
    private String content;
    private CustomerDto customer;
}
