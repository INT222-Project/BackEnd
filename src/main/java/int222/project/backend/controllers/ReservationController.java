package int222.project.backend.controllers;

import int222.project.backend.models.Reservation;
import int222.project.backend.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://172.99.99.1:8081"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    ReservationRepository reservationRepository;

    // Reservation
    @GetMapping("/{reservNo}")
    public Reservation getReservation (@PathVariable String reservNo){ return reservationRepository.findById(reservNo).orElse(null); }

    @GetMapping("")
    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }
}
