package pl.edu.wat.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.ComplaintDto;
import pl.edu.wat.backend.dtos.CustomerDto;
import pl.edu.wat.backend.entities.ComplaintEntity;
import pl.edu.wat.backend.entities.CustomerEntity;
import pl.edu.wat.backend.repositories.ComplaintRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintRepository complaintRepository;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Override
    public List<ComplaintDto> getComplaints() {
        List<ComplaintDto> complaintDtos = new ArrayList<>();
        complaintRepository.findAll()
                .forEach(complaint -> complaintDtos.add(new ComplaintDto(complaint.getComplaintId(),
                        complaint.getCreationDate(),
                        complaint.getLastUpdateDate(),
                        complaint.getHasBeenRead(),
                        complaint.getContent(),
                        new CustomerDto(complaint.getCustomerEntity().getCustomerId(),
                                complaint.getCustomerEntity().getFirstName(),
                                complaint.getCustomerEntity().getLastName(),
                                complaint.getCustomerEntity().getEmail(),
                                complaint.getCustomerEntity().getPesel(),
                                complaint.getCustomerEntity().getCreditCardNumber(),
                                complaint.getCustomerEntity().getPhoneNumber()))));
        return complaintDtos;
    }

    @Override
    public List<ComplaintDto> getComplaintsForCustomer(Integer customerId) {
        List<ComplaintDto> complaintDtos = new ArrayList<>();
        complaintRepository.findAll()
                .forEach(complaint -> {
                    if (complaint.getCustomerEntity().getCustomerId().equals(customerId)) {
                        complaintDtos.add(new ComplaintDto(complaint.getComplaintId(),
                                complaint.getCreationDate(),
                                complaint.getLastUpdateDate(),
                                complaint.getHasBeenRead(),
                                complaint.getContent(),
                                new CustomerDto(complaint.getCustomerEntity().getCustomerId(),
                                        complaint.getCustomerEntity().getFirstName(),
                                        complaint.getCustomerEntity().getLastName(),
                                        complaint.getCustomerEntity().getEmail(),
                                        complaint.getCustomerEntity().getPesel(),
                                        complaint.getCustomerEntity().getCreditCardNumber(),
                                        complaint.getCustomerEntity().getPhoneNumber())));
                    }
                });

        return complaintDtos;
    }

    @Override
    public boolean addComplaint(ComplaintDto complaintDto) {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setComplaintId(complaintDto.getComplaintId());
        complaintEntity.setCreationDate(complaintDto.getCreationDate());
        complaintEntity.setLastUpdateDate(complaintDto.getLastUpdateDate());
        complaintEntity.setHasBeenRead(complaintDto.getHasBeenRead());
        complaintEntity.setContent(complaintDto.getContent());
        complaintEntity.setCustomerEntity(new CustomerEntity(
                complaintDto.getCustomer().getCustomerId(),
                complaintDto.getCustomer().getFirstName(),
                complaintDto.getCustomer().getLastName(),
                complaintDto.getCustomer().getEmail(),
                complaintDto.getCustomer().getPesel(),
                complaintDto.getCustomer().getCreditCardNumber(),
                complaintDto.getCustomer().getPhoneNumber()));
        complaintRepository.save(complaintEntity);
        return true;
    }

    @Override
    public ComplaintDto getComplaintById(Integer id) {
        Optional<ComplaintEntity> optionalComplaintEntity = complaintRepository.findById(id);
        if (!optionalComplaintEntity.isPresent()) {
            return null;
        }
        ComplaintEntity complaintEntity = optionalComplaintEntity.get();
        return new ComplaintDto(complaintEntity.getComplaintId(),
                complaintEntity.getCreationDate(),
                complaintEntity.getLastUpdateDate(),
                complaintEntity.getHasBeenRead(),
                complaintEntity.getContent(),
                new CustomerDto(complaintEntity.getCustomerEntity().getCustomerId(),
                        complaintEntity.getCustomerEntity().getFirstName(),
                        complaintEntity.getCustomerEntity().getLastName(),
                        complaintEntity.getCustomerEntity().getEmail(),
                        complaintEntity.getCustomerEntity().getPesel(),
                        complaintEntity.getCustomerEntity().getCreditCardNumber(),
                        complaintEntity.getCustomerEntity().getPhoneNumber()));
    }

    @Override
    public void updateComplaint(ComplaintDto complaintDto) {
        Optional<ComplaintEntity> optionalComplaintEntity = complaintRepository.findById(complaintDto.getComplaintId());
        if (optionalComplaintEntity.isPresent()) {
            ComplaintEntity complaintEntity = new ComplaintEntity();
            complaintEntity.setComplaintId(complaintDto.getComplaintId());
            complaintEntity.setCreationDate(complaintDto.getCreationDate());
            complaintEntity.setLastUpdateDate(complaintDto.getLastUpdateDate());
            complaintEntity.setHasBeenRead(complaintDto.getHasBeenRead());
            complaintEntity.setContent(complaintDto.getContent());
            complaintEntity.setCustomerEntity(new CustomerEntity(
                    complaintDto.getCustomer().getCustomerId(),
                    complaintDto.getCustomer().getFirstName(),
                    complaintDto.getCustomer().getLastName(),
                    complaintDto.getCustomer().getEmail(),
                    complaintDto.getCustomer().getPesel(),
                    complaintDto.getCustomer().getCreditCardNumber(),
                    complaintDto.getCustomer().getPhoneNumber()));
            complaintRepository.save(complaintEntity);
        }
    }

    @Override
    public void deleteComplaintById(Integer id) {
        complaintRepository.deleteById(id);
    }
}
