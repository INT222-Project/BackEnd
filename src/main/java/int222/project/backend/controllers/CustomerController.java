package int222.project.backend.controllers;

import int222.project.backend.exceptions.ImageHandlerException;
import int222.project.backend.models.Customer;
import int222.project.backend.models.Room;
import int222.project.backend.repositories.CustomerRepository;
import int222.project.backend.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @PostMapping("/uploadImage/{customerId}")
    public void uploadImage(@RequestParam("image-file") MultipartFile imageFile, @PathVariable String customerId) throws IOException {
        uploadService.saveImage(imageFile, customerId, Customer.class);
    }

    @GetMapping(path = "/showImage/{customerId}")
    public ResponseEntity<?> showImage(@PathVariable String customerId) {
        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(uploadService.get(customerId, Customer.class));
        } catch (ImageHandlerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + " and error code : " + e.getErrorCode());
        }
    }

    @DeleteMapping(path = "/deleteImage/{customerId}")
    public ResponseEntity<?> deleteImage(@PathVariable String customerId) {
        uploadService.deleteImage(customerId, Customer.class);
        return ResponseEntity.ok().body("Successful delete image");
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable String customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    @GetMapping("")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PutMapping(path = "/edit/{customerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editCustomer(@RequestParam(value = "image-file", required = false) MultipartFile imageFile, @RequestPart("editCustomer") Customer customer, @PathVariable String customerId) {
        Customer temp = this.customerRepository.findById(customerId).orElse(null);
        if (temp != null) {
            temp = customer;
            if (imageFile != null) {
                deleteImage(customerId);
                try {
                    uploadImage(imageFile, customerId);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sorry, We could not save your image file.");
                }
            }
            return ResponseEntity.ok(this.customerRepository.saveAndFlush(temp));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Exception("Unable to find customer id : " + customerId));
        }
    }

}
