package int222.project.backend.controllers;

import int222.project.backend.models.Customer;
import int222.project.backend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
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
