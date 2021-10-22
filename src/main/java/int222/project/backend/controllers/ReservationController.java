package int222.project.backend.controllers;

import int222.project.backend.models.Package;
import int222.project.backend.models.PackageDetail;
import int222.project.backend.models.Reservation;
import int222.project.backend.models.ReservationDetail;
import int222.project.backend.repositories.PackageDetailRepository;
import int222.project.backend.repositories.ReservationDetailRepository;
import int222.project.backend.repositories.ReservationRepository;
import int222.project.backend.services.ReservationRequirement;
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
    @Autowired
    ReservationDetailRepository reservationDetailRepository;
    @Autowired
    PackageDetailRepository packageDetailRepository;

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
    public void addReservation(@RequestPart("newReservation") ReservationRequirement reservationRequirement){
        int nextId = this.getNextReservationNo();
        Reservation tempReservation = new Reservation(Integer.toString(nextId),reservationRequirement.getCustomer(),reservationRequirement.getPaymentDate(),reservationRequirement.getReservationDate(),reservationRequirement.getPaymentMethod(),reservationRequirement.getSubtotal(),null,null);
        System.out.println(tempReservation.toString());
        this.reservationRepository.save(tempReservation);
        // add reservation detail from reservation
        ReservationDetail tempReservationDetail = new ReservationDetail(null,tempReservation,null,reservationRequirement.getCheckInDate(),reservationRequirement.getCheckOutDate(),reservationRequirement.getNumOfRest(),reservationRequirement.getRoomCharge());
        this.addReservationDetail(tempReservationDetail);
        // add package detail from reservation detail
        List<Package> packages = reservationRequirement.getPackages();
        for(int i = 0 ; i < packages.size() ; i++){
            PackageDetail tempPackageDetail = new PackageDetail(null,tempReservationDetail,packages.get(i),packages.get(i).getPackageCharge());
            this.addPackageDetail(tempPackageDetail);
        }
    }

    private int getNextReservationNo(){
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
        return id;
    }

    private void addReservationDetail(ReservationDetail reservationDetail){
        List<ReservationDetail> reservationDetailList = this.reservationDetailRepository.findAll();
        String latestId = null;
        for(int i = 0 ; i < reservationDetailList.size() ; i++){
            if(i+1 == reservationDetailList.size()-1) break;
            String id = reservationDetailList.get(i).getReservDetailId().substring(2);
            String nextId = reservationDetailList.get(i+1).getReservDetailId().substring(2);
            if(Integer.parseInt(id)+1 != Integer.parseInt(nextId)){
                latestId = id;
            }
        }
        if(latestId == null) latestId = reservationDetailList.get(reservationDetailList.size()-1).getReservDetailId().substring(2);
        int tempId = Integer.parseInt(latestId) + 1;
        if(tempId < 10) latestId = "rd000" + tempId;
        else if (tempId < 100) latestId = "rd00" + tempId;
        else if (tempId < 1000) latestId = "rd0" + tempId;
        else latestId = "rd" + tempId;
        reservationDetail.setReservDetailId(latestId);
        this.reservationDetailRepository.save(reservationDetail);
    }

    private void addPackageDetail(PackageDetail packageDetail){
        List<PackageDetail> packageDetailList = this.packageDetailRepository.findAll();
        String latestId = null;
        for(int i = 0 ; i < packageDetailList.size() ; i++){
            if(i+1 == packageDetailList.size()-1) break;
            String id = packageDetailList.get(i).getPackageDetailId().substring(2);
            String nextId = packageDetailList.get(i+1).getPackageDetailId().substring(2);
            if(Integer.parseInt(id)+1 != Integer.parseInt(nextId)){
                latestId = id;
            }
        }
        if(latestId == null) latestId = packageDetailList.get(packageDetailList.size()-1).getPackageDetailId().substring(2);
        int tempId = Integer.parseInt(latestId) + 1;
        if(tempId < 10) latestId = "pd000" + tempId;
        else if (tempId < 100) latestId = "pd00" + tempId;
        else if (tempId < 1000) latestId = "pd0" + tempId;
        else latestId = "pd" + tempId;
        packageDetail.setPackageDetailId(latestId);
        this.packageDetailRepository.save(packageDetail);
    }
}
