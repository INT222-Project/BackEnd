package int222.project.backend.controllers;

import int222.project.backend.models.Reservation;
import int222.project.backend.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
