package int222.project.backend.controllers;

import int222.project.backend.models.Customer;
import int222.project.backend.repositories.CustomerRepository;
import int222.project.backend.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UploadService uploadService;
    // Customer
    @PostMapping("/uploadImage")
    public void uploadImage(@RequestParam("image-file") MultipartFile imageFile, String customerId){
        uploadService.saveImage(imageFile,customerId);
    }

    @GetMapping(path = "/showImage/{customerId}")
    public ResponseEntity<byte[]> showImage(@PathVariable String customerId){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(uploadService.get(customerId,Customer.class));
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer (@PathVariable String customerId){ return customerRepository.findById(customerId).orElse(null); }

    @GetMapping("")
    public List<Customer> getAllCustomers(){ return customerRepository.findAll(); }

}
