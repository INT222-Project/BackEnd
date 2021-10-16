package int222.project.backend.controllers;

import int222.project.backend.models.Reservation;
import int222.project.backend.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
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

    @GetMapping("/unsuccessReservation")
    public List<Reservation> getUnsuccesReservation(){
        return reservationRepository.getUnsuccessReservation();
    }

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addReservation(@RequestPart("newReservation") Reservation reservation){
        String latestReservation = null;
        List<Reservation> getAllReservation = reservationRepository.findAll();
        for(int i = 0 ; i < getAllReservation.size() ; i++){
            if(i+1 == getAllReservation.size() - 1) break;
            String id = getAllReservation.get(i).getReservNo();
            String nextId = getAllReservation.get(i+1).getReservNo();
            if(!(Integer.toString(Integer.parseInt(id)+1)).equals(nextId)) {
                latestReservation = id;
                break;
            }
        }
        System.out.println("latest Reservation no : " + latestReservation);
        if(latestReservation == null) latestReservation = getAllReservation.get(getAllReservation.size()-1).getReservNo();
        int id = Integer.parseInt(latestReservation)+1;
        reservation.setReservNo(Integer.toString(id));
        System.out.println(reservation.toString());
        this.reservationRepository.save(reservation);
    }
}
