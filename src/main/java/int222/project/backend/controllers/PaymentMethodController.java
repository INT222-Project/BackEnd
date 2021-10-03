package int222.project.backend.controllers;

import int222.project.backend.models.PaymentMethod;
import int222.project.backend.repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/paymentMethods")
public class PaymentMethodController {
    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    // Payment Method
    @GetMapping("/{methodId}")
    public PaymentMethod getPaymentMethod(@PathVariable int methodId){ return paymentMethodRepository.findById(methodId).orElse(null); }

    @GetMapping("")
    public List<PaymentMethod> getAllPaymentMethods(){ return paymentMethodRepository.findAll(); }

}
