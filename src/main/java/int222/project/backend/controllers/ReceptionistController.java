package int222.project.backend.controllers;

import int222.project.backend.models.Receptionist;
import int222.project.backend.repositories.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
