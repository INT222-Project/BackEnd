package int222.project.backend.controllers;

import int222.project.backend.models.Customer;
import int222.project.backend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;
    // Customer
    @GetMapping("/{customerId}")
    public Customer getCustomer (@PathVariable String customerId){ return customerRepository.findById(customerId).orElse(null); }

    @GetMapping("")
    public List<Customer> getAllCustomers(){ return customerRepository.findAll(); }

}
