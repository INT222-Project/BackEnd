package int222.project.backend.controllers;

import int222.project.backend.models.PackageDetail;
import int222.project.backend.models.Reservation;
import int222.project.backend.models.ReservationDetail;
import int222.project.backend.models.Room;
import int222.project.backend.repositories.PackageDetailRepository;
import int222.project.backend.repositories.ReservationDetailRepository;
import int222.project.backend.repositories.ReservationRepository;
import int222.project.backend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {""},allowedHeaders = "*")
@RestController
public class ProductRestController {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationDetailRepository reservationDetailRepository;
    @Autowired
    PackageDetailRepository packageDetailRepository;
    @Autowired
    RoomRepository roomRepository;

    @GetMapping("/api/reservations")
    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    @GetMapping("/api/reservationDetails")
    public List<ReservationDetail> getAllReservationDetails(){
        return reservationDetailRepository.findAll();
    }

    @GetMapping("/api/packageDetails")
    public List<PackageDetail> getAllPackageDetails(){ return packageDetailRepository.findAll(); }

    @GetMapping("/api/rooms")
    public List<Room> getAllRooms(){ return roomRepository.findAll(); }
}
