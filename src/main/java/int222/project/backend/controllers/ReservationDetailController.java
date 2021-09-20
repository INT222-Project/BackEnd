package int222.project.backend.controllers;

import int222.project.backend.models.ReservationDetail;
import int222.project.backend.repositories.ReservationDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
