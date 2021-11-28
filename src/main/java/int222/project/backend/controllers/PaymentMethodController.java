package int222.project.backend.controllers;

import int222.project.backend.models.PaymentMethod;
import int222.project.backend.models.RoomType;
import int222.project.backend.repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public PaymentMethod getPaymentMethod(@PathVariable int methodId) {
        return paymentMethodRepository.findById(methodId).orElse(null);
    }

    @GetMapping("")
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addNewPaymentMethod(@RequestPart("addRoomType") PaymentMethod paymentMethod) {
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        int latestId = -1;
        for (int i = 0; i < paymentMethodList.size(); i++) {
            if (i + 1 == paymentMethodList.size() - 1) break;
            int id = paymentMethodList.get(i).getPaymentMethodId();
            int nextId = paymentMethodList.get(i + 1).getPaymentMethodId();
            if (!((id + 1) == nextId)) {
                latestId = id;
                break;
            }
        }
        if (latestId == -1) latestId = paymentMethodList.get(paymentMethodList.size() - 1).getPaymentMethodId();
        int id = latestId + 1;
        paymentMethod.setPaymentMethodId(id);
        this.paymentMethodRepository.save(paymentMethod);
    }

    @PutMapping(path = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void editPaymentMethod(@RequestPart("editRoomType") PaymentMethod paymentMethod) {
        this.paymentMethodRepository.saveAndFlush(paymentMethod);
    }

    @DeleteMapping(path = "/delete/{roomTypeId}")
    public void deletePaymentMethod(@PathVariable int paymentMethodId) {
        PaymentMethod paymentMethod = this.paymentMethodRepository.findById(paymentMethodId).orElse(null);
        if (paymentMethod != null) this.paymentMethodRepository.delete(paymentMethod);
        else return;
    }

}
