package pl.edu.wat.backend.services;

import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.ComplaintDto;
import pl.edu.wat.backend.dtos.CustomerDto;
import pl.edu.wat.backend.dtos.RoomDto;

import java.util.List;

@Service
public interface ComplaintService {
    List<ComplaintDto> getComplaints();

    List<ComplaintDto> getComplaintsForCustomer(Integer customerId);

    boolean addComplaint(ComplaintDto complaintDto);

    ComplaintDto getComplaintById(Integer id);

    void updateComplaint(ComplaintDto complaintDto);

    void deleteComplaintById(Integer id);
}
