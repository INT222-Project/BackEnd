package int222.project.backend.controllers;

import int222.project.backend.models.Reservation;
import int222.project.backend.models.ReservationDetail;
import int222.project.backend.repositories.ReservationDetailRepository;
import int222.project.backend.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/reservationDetails")
public class ReservationDetailController {
    @Autowired
    ReservationDetailRepository reservationDetailRepository;

    @Autowired
    ReservationRepository reservationRepository;

    // Reservation Detail
    @GetMapping("/{reservDetailId}")
    public ReservationDetail getReservationDetail (@PathVariable String reservDetailId){ return reservationDetailRepository.findById(reservDetailId).orElse(null); }

    @GetMapping("")
    public List<ReservationDetail> getAllReservationDetails(){
        return reservationDetailRepository.findAll();
    }

    @GetMapping("/byReservationNo/{reservNo}")
    public List<ReservationDetail> getAllReservationDetailsByReservNo(@PathVariable String reservNo){
        Reservation reservation = reservationRepository.findById(reservNo).orElse(null);
        return reservationDetailRepository.getAllReservationDetailsByReservNo(reservation.getReservNo());
    }

    @GetMapping("/reservedReservationDetail")
    public List<ReservationDetail> getReservedReservationDetail(){ return reservationDetailRepository.getReservedReservationDetail();}


}
