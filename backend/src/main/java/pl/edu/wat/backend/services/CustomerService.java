package pl.edu.wat.backend.services;

import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.CustomerDto;
import java.util.List;

@Service
public interface CustomerService {
    List<CustomerDto> getCustomers();

    boolean addCustomer(CustomerDto customerDto);

    CustomerDto getCustomer(Integer customerId);

    void deleteCustomer(Integer customerId);

    void updateCustomer(CustomerDto customerDto);
}
