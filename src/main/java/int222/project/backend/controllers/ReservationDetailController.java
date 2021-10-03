package int222.project.backend.controllers;

import int222.project.backend.models.ReservationDetail;
import int222.project.backend.repositories.ReservationDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/reservationDetails")
public class ReservationDetailController {
    @Autowired
    ReservationDetailRepository reservationDetailRepository;

    // Reservation Detail
    @GetMapping("/{reservDetailId}")
    public ReservationDetail getReservationDetail (@PathVariable String reservDetailId){ return reservationDetailRepository.findById(reservDetailId).orElse(null); }

    @GetMapping("")
    public List<ReservationDetail> getAllReservationDetails(){
        return reservationDetailRepository.findAll();
    }

}
