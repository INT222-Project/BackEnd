package int222.project.backend.controllers;

import int222.project.backend.models.PaymentMethod;
import int222.project.backend.repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
