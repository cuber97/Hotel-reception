package pl.edu.wat.backend.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.CustomerDto;
import pl.edu.wat.backend.entities.CustomerEntity;
import pl.edu.wat.backend.repositories.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
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

        CustomerEntity customerEntity = this.modelMapper.map(customerDto, CustomerEntity.class);

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
        return this.modelMapper.map(customerEntity, CustomerDto.class);
    }

    @Override
    public void deleteCustomer(Integer customerIdn) {
        customerRepository.deleteById(customerIdn);
    }

    @Override
    public void updateCustomer(CustomerDto customerDto) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerDto.getCustomerId());
        if (optionalCustomerEntity.isPresent()) {
            CustomerEntity customerEntity = this.modelMapper.map(customerDto, CustomerEntity.class);
            customerEntity.setCustomerId(optionalCustomerEntity.get().getCustomerId());
            customerRepository.save(customerEntity);
        }
    }
}
