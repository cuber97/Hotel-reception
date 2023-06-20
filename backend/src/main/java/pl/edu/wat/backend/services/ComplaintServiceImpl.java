package pl.edu.wat.backend.services;

import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository complaintRepository, ModelMapper modelMapper) {
        this.complaintRepository = complaintRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ComplaintDto> getComplaints() {
        List<ComplaintDto> complaintDtos = new ArrayList<>();
        complaintRepository.findAll()
                .forEach(complaint -> {
                    ComplaintDto complaintDto = this.modelMapper.map(complaint, ComplaintDto.class);
                    complaintDto.setCustomer(this.modelMapper.map(complaint.getCustomerEntity(), CustomerDto.class));
                    complaintDtos.add(complaintDto);
                });
        return complaintDtos;
    }

    @Override
    public List<ComplaintDto> getComplaintsForCustomer(Integer customerId) {
        List<ComplaintDto> complaintDtos = new ArrayList<>();
        complaintRepository.findAll()
                .forEach(complaint -> {
                    if (complaint.getCustomerEntity().getCustomerId().equals(customerId)) {
                        ComplaintDto complaintDto = this.modelMapper.map(complaint, ComplaintDto.class);
                        complaintDto.setCustomer(this.modelMapper.map(complaint.getCustomerEntity(), CustomerDto.class));
                        complaintDtos.add(complaintDto);
                    }
                });

        return complaintDtos;
    }

    @Override
    public boolean addComplaint(ComplaintDto complaintDto) {
        ComplaintEntity complaintEntity = this.modelMapper.map(complaintDto, ComplaintEntity.class);
        complaintEntity.setCustomerEntity(this.modelMapper.map(complaintDto.getCustomer(), CustomerEntity.class));
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
        ComplaintDto complaintDto = this.modelMapper.map(complaintEntity, ComplaintDto.class);
        complaintDto.setCustomer(this.modelMapper.map(complaintEntity.getCustomerEntity(), CustomerDto.class));
        return complaintDto;
    }

    @Override
    public void updateComplaint(ComplaintDto complaintDto) {
        Optional<ComplaintEntity> optionalComplaintEntity = complaintRepository.findById(complaintDto.getComplaintId());
        if (optionalComplaintEntity.isPresent()) {
            ComplaintEntity complaintEntity = this.modelMapper.map(complaintDto, ComplaintEntity.class);
            complaintEntity.setComplaintId(optionalComplaintEntity.get().getComplaintId());
            complaintEntity.setCustomerEntity(this.modelMapper.map(complaintDto.getCustomer(), CustomerEntity.class));
            complaintRepository.save(complaintEntity);
        }
    }

    @Override
    public void deleteComplaintById(Integer id) {
        complaintRepository.deleteById(id);
    }
}
