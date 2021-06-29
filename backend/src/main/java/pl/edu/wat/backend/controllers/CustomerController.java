package pl.edu.wat.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.backend.dtos.CustomerDto;
import pl.edu.wat.backend.services.CustomerService;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") Integer customerId) {
        CustomerDto customerDto = customerService.getCustomer(customerId);
        if (customerDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity addCustomer(@RequestBody CustomerDto customerDto) {
        if (customerService.addCustomer(customerDto)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/customers/{customerIdn}")
    public ResponseEntity deleteCustomer(@PathVariable("customerIdn") Integer customerIdn) {
        customerService.deleteCustomer(customerIdn);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/customers")
    public ResponseEntity updateCustomer(@RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(customerDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
