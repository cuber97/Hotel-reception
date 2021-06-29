package pl.edu.wat.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.CustomerDto;
import pl.edu.wat.backend.entities.CustomerEntity;
import pl.edu.wat.backend.repositories.CustomerRepository;
import pl.edu.wat.backend.repositories.ReservationRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDto> getCustomers() {
        List<CustomerDto> customerDtos = new ArrayList<>();

        customerRepository.findAll()
                .forEach(customer -> customerDtos.add(new CustomerDto(customer.getCustomerId(), customer.getFirstName(),
                        customer.getLastName(), customer.getEmail(), customer.getPesel(), customer.getCreditCardNumber(),
                        customer.getPhoneNumber())));

        return customerDtos;
    }

    @Override
    public boolean addCustomer(CustomerDto customerDto) {
        if(customerRepository.findByPesel(customerDto.getPesel()) != null) {
            return false;
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName(customerDto.getFirstName());
        customerEntity.setLastName(customerDto.getLastName());
        customerEntity.setEmail(customerDto.getEmail());
        customerEntity.setPesel(customerDto.getPesel());
        customerEntity.setCreditCardNumber(customerDto.getCreditCardNumber());
        customerEntity.setPhoneNumber(customerDto.getPhoneNumber());

        customerRepository.save(customerEntity);

        return true;
    }

    @Override
    public CustomerDto getCustomer(Integer customerIdn) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerIdn);
        if(!optionalCustomerEntity.isPresent()) {
            return null;
        }
        CustomerEntity customerEntity = optionalCustomerEntity.get();
        return new CustomerDto(customerEntity.getCustomerId(), customerEntity.getFirstName(), customerEntity.getLastName(), customerEntity.getEmail(), customerEntity.getPesel(), customerEntity.getCreditCardNumber(), customerEntity.getPhoneNumber());
    }

    @Override
    public void deleteCustomer(Integer customerIdn) {
        customerRepository.deleteById(customerIdn);
    }

    @Override
    public void updateCustomer(CustomerDto customerDto) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerDto.getCustomerId());
        if (optionalCustomerEntity.isPresent()) {
            CustomerEntity customerEntity = optionalCustomerEntity.get();
            customerEntity.setFirstName(customerDto.getFirstName());
            customerEntity.setLastName(customerDto.getLastName());
            customerEntity.setEmail(customerDto.getEmail());
            customerEntity.setCreditCardNumber(customerDto.getCreditCardNumber());
            customerEntity.setPesel(customerDto.getPesel());
            customerEntity.setPhoneNumber(customerDto.getPhoneNumber());
            customerRepository.save(customerEntity);
        }
    }
}
