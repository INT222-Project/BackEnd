package int222.project.backend.controllers;

import int222.project.backend.models.Receptionist;
import int222.project.backend.repositories.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://172.99.99.1:8081"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/receptionists")
public class ReceptionistController {
    @Autowired
    ReceptionistRepository receptionistRepository;

    // Receptionist
    @GetMapping("/{repId}")
    public Receptionist getReceptionist (@PathVariable String repId){ return receptionistRepository.findById(repId).orElse(null); }

    @GetMapping("")
    public List<Receptionist> getAllReceptionists(){ return receptionistRepository.findAll(); }

}
