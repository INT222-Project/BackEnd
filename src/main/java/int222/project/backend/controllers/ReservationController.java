package int222.project.backend.controllers;

import com.sun.xml.bind.v2.runtime.reflect.Lister;
import int222.project.backend.exceptions.Error;
import int222.project.backend.models.*;
import int222.project.backend.models.Package;
import int222.project.backend.repositories.PackageDetailRepository;
import int222.project.backend.repositories.ReservationDetailRepository;
import int222.project.backend.repositories.ReservationRepository;
import int222.project.backend.repositories.RoomRepository;
import int222.project.backend.models.ReservationAddingObject;
import int222.project.backend.models.ReservationRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    RoomRepository roomRepository;

    // Reservation
    @GetMapping("/{reservNo}")
    public Reservation getReservation(@PathVariable String reservNo) {
        return reservationRepository.findById(reservNo).orElse(null);
    }

    @GetMapping("")
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @GetMapping("/unsuccessReservation")
    public List<Reservation> getUnsuccesReservation() {
        return reservationRepository.getUnsuccessReservation();
    }

    @GetMapping("/byCustomerId/{customerId}")
    public List<Reservation> getReservationByCustomerId(@PathVariable String customerId) {
        return reservationRepository.getReservationByCustomerId(customerId);
    }

    @GetMapping("/byReservationDetailId/{reservDetailId}")
    public Reservation getReservationByReservationDetailId(@PathVariable String reservDetailId) {
        return reservationRepository.getReservationByReservationDetailId(reservDetailId);
    }

    @GetMapping("/unpaidReservation")
    public List<Reservation> getUnpaidReservation() {
        return reservationRepository.getUnpaidReservation();
    }

    @GetMapping("/successReservation")
    public List<Reservation> getSuccessReservation() {
        return reservationRepository.getSuccessReservation();
    }

    @PutMapping(value = "/editCustomerPackage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editCustomerPackage(@RequestPart("editPackage") List<Package> packageList, @RequestPart("reservNo") String reservNo, @RequestPart("reservationDetailId") String reservDetailId) {
        /* get reservation from reservNo */
        Reservation reservation = this.reservationRepository.findById(reservNo).orElse(null);
        ReservationDetail reservationDetail = this.reservationDetailRepository.findById(reservDetailId).orElse(null);
        if (reservation != null && reservationDetail != null) {
            double total = reservationDetail.getTotal(); // total price in reservation detail
            System.out.println("total reservation detail price first : " + total);
            List<PackageDetail> packageDetailList = this.packageDetailRepository.getAllPackageDetailsByReservationDetail(reservDetailId);
            // create temp package detail list to get package detail want to delete
            List<PackageDetail> tempPackageDetailList = new ArrayList<>();
            // check old package in package detail which package is not in new Package delete that package detail
            if (packageList.size() != 0) {
                for (PackageDetail packageDetail : packageDetailList) {
                    int count = 0;
                    for (Package packagee : packageList) {
                        if (packagee.getPackageId().equals(packageDetail.getPackageId().getPackageId())) {
                            count++;
                        }
                    }
                    if (count == 0) {
                        System.out.println("package detail need to delete : " + packageDetail.getPackageDetailId());
                        tempPackageDetailList.add(packageDetail);
                    }
                }
                if (tempPackageDetailList.size() != 0) {
                    for (PackageDetail packageDetail : tempPackageDetailList) {
                        if (packageDetailList.contains(packageDetail)) {
                            total -= packageDetail.getPackageCharge();
                            packageDetailList.remove(packageDetailList.get(packageDetailList.indexOf(packageDetail)));
                            this.packageDetailRepository.delete(packageDetail);
                        }
                    }
                }
                for (PackageDetail temp : tempPackageDetailList) {
                    System.out.println("package detail id that in temp package detail list : " + temp.getPackageDetailId());
                }
                // add new package that doesn't have in package detail
                tempPackageDetailList.clear(); // clear tempPackageDetailList for collecting package detail that need to add
                for (Package aPackage : packageList) {
                    // check package in package detail list that has the same one
                    int count = 0;
                    for (PackageDetail packageDetail : packageDetailList) {
                        System.out.println("check package id : " + aPackage.getPackageId());
                        System.out.println("package detail id : " + packageDetail.getPackageDetailId() + " that have package id : " + packageDetail.getPackageId().getPackageId());
                        if (packageDetail.getPackageId().getPackageId().equals(aPackage.getPackageId())) {
                            count++;
                        }
                    }
                    if (count == 0) {
                        PackageDetail temp = new PackageDetail(this.getPackageDetailId(), reservationDetail, aPackage, aPackage.getPackageCharge());
                        System.out.println("package detail id need to add : " + temp.getPackageDetailId());
                        tempPackageDetailList.add(temp);
                        this.packageDetailRepository.save(temp);
                    }
                }
                // loop the temp package detail list to add package detail that need to add so add to package detail list
                if (tempPackageDetailList.size() != 0) {
                    for (PackageDetail packageDetail : tempPackageDetailList) {
                        packageDetailList.add(packageDetail);
                        total += packageDetail.getPackageCharge();
                    }
                }
                // set new package detail in reservation detail
                reservationDetail.setTotal(total); // room charge + package price = total in reservation detail
                reservationDetail.setPackageDetailList(packageDetailList);
                System.out.println("total reservation detail price last : " + total);
                this.reservationDetailRepository.save(reservationDetail);
                double newTotal = 0;
                List<ReservationDetail> reservationDetailList = this.reservationDetailRepository.getAllReservationDetailsByReservNo(reservation.getReservNo());
                for (ReservationDetail tempReservationDetail : reservationDetailList) {
                    double tempTotal = tempReservationDetail.getTotal();
                    // calculate amount of days
                    long numOfDate = TimeUnit.DAYS.convert(tempReservationDetail.getCheckOutDate().getTime() - tempReservationDetail.getCheckInDate().getTime(), TimeUnit.MILLISECONDS);
                    System.out.println("amount of days :" + numOfDate);
                    double roomCharge = tempReservationDetail.getRoom().getRoomCharge();
                    double onlyPackagePrice = tempTotal - roomCharge; // it would not be less than 0 (surely)
                    newTotal += (numOfDate * roomCharge) + onlyPackagePrice;
                }
                reservation.setSubTotal(newTotal);
                return ResponseEntity.ok(this.reservationRepository.save(reservation));
            }
            // when customer don't need packages anymore
            else {
                for (PackageDetail packageDetail : packageDetailList) {
                    total -= packageDetail.getPackageCharge();
                }
                this.packageDetailRepository.deleteAll(packageDetailList);
                reservationDetail.setTotal(total);
                reservationDetail.setPackageDetailList(null);
                this.reservationDetailRepository.save(reservationDetail);
                double newTotal = 0;
                List<ReservationDetail> reservationDetailList = this.reservationDetailRepository.getAllReservationDetailsByReservNo(reservation.getReservNo());
                for (ReservationDetail tempReservationDetail : reservationDetailList) {
                    double tempTotal = tempReservationDetail.getTotal();
                    // calculate amount of days
                    long numOfDate = TimeUnit.DAYS.convert(tempReservationDetail.getCheckOutDate().getTime() - tempReservationDetail.getCheckInDate().getTime(), TimeUnit.MILLISECONDS);
                    System.out.println("amount of days :" + numOfDate);
                    double roomCharge = tempReservationDetail.getRoom().getRoomCharge();
                    double onlyPackagePrice = tempTotal - roomCharge; // it would not be less than 0 (surely)
                    newTotal += (numOfDate * roomCharge) + onlyPackagePrice;
                }
                reservation.setSubTotal(newTotal);
                return ResponseEntity.ok(this.reservationRepository.save(reservation));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error("Sorry Reservation no #" + reservNo + " and Reservation Detail Id #" + reservDetailId + " could not edit packages.", 400));
    }

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addReservation(@RequestPart("newReservation") ReservationAddingObject reservationAddingObject) {
        List<ReservationRequirement> reservationRequirementList = reservationAddingObject.getReservationRequirements();
        double price = 0;
        for (int i = 0; i < reservationRequirementList.size(); i++) {
            price += reservationRequirementList.get(i).getSubtotal();
        }
        PaymentMethod paymentMethod = reservationAddingObject.getPaymentMethod();
        // generate reservation no
        String reservationNo = this.getNextReservationNo();
        for (ReservationRequirement reservationRequirement : reservationRequirementList) {
            // to string reservation requirement object
            System.out.println("Reservation requirement object : " + reservationRequirement.toString());
            Reservation tempReservation = new Reservation(reservationNo, reservationRequirement.getCustomer(), reservationRequirement.getPaymentDate(), reservationRequirement.getReservationDate(), paymentMethod, price, "unpaid", null, null);
            System.out.println(tempReservation.toString());
            this.reservationRepository.save(tempReservation);
            // add reservation detail from reservation
            String reservationDetailId = getReservationDetailId();
            // calculate package price before adding to reservationdetail
            List<Package> packages = reservationRequirement.getPackages();
            double pricePackage = 0;
            for (Package packagee : packages) {
                pricePackage += packagee.getPackageCharge();
            }
            ReservationDetail tempReservationDetail = new ReservationDetail(reservationDetailId, tempReservation, reservationRequirement.getRoom(), reservationRequirement.getCheckInDate(), reservationRequirement.getCheckOutDate(), reservationRequirement.getNumOfRest(), reservationRequirement.getRoom().getRoomCharge() + pricePackage, "undone", null);
            this.reservationDetailRepository.save(tempReservationDetail);
            // add package detail from reservation detail
            for (int i = 0; i < packages.size(); i++) {
                String packageDetailId = getPackageDetailId();
                PackageDetail tempPackageDetail = new PackageDetail(packageDetailId, tempReservationDetail, packages.get(i), packages.get(i).getPackageCharge());
                this.packageDetailRepository.save(tempPackageDetail);
            }
            // add list to reservation
            Reservation reservation = this.reservationRepository.findById(reservationNo).orElse(null);
            List<ReservationDetail> reservationDetailList = this.reservationDetailRepository.getAllReservationDetailsByReservNo(reservation.getReservNo());
            for (ReservationDetail temp : reservationDetailList) {
                List<PackageDetail> packageDetailList = this.packageDetailRepository.getAllPackageDetailsByReservationDetail(temp.getReservDetailId());
                temp.setPackageDetailList(packageDetailList);
                this.reservationDetailRepository.saveAndFlush(temp);
            }
            reservation.setReservationDetailList(reservationDetailList);
            this.reservationRepository.saveAndFlush(reservation);
        }
    }

    @PutMapping(path = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void editReservation(@RequestPart("editReservation") Reservation reservation) {
        this.reservationRepository.saveAndFlush(reservation);
    }

    private String getNextReservationNo() {
        String latestReservation = null;
        List<Reservation> getAllReservation = reservationRepository.findAll();
        for (int i = 0; i < getAllReservation.size(); i++) {
            if (i + 1 == getAllReservation.size() - 1) break;
            String id = getAllReservation.get(i).getReservNo();
            String nextId = getAllReservation.get(i + 1).getReservNo();
            if (!(Integer.toString(Integer.parseInt(id) + 1)).equals(nextId)) {
                latestReservation = id;
                break;
            }
        }
        System.out.println("latest Reservation no : " + latestReservation);
        if (latestReservation == null)
            latestReservation = getAllReservation.get(getAllReservation.size() - 1).getReservNo();
        int id = Integer.parseInt(latestReservation) + 1;
        return Integer.toString(id);
    }

    private String getReservationDetailId() {
        List<ReservationDetail> reservationDetailList = this.reservationDetailRepository.findAll();
        String latestId = null;
        for (int i = 0; i < reservationDetailList.size(); i++) {
            if (i + 1 == reservationDetailList.size() - 1) break;
            String id = reservationDetailList.get(i).getReservDetailId().substring(2);
            String nextId = reservationDetailList.get(i + 1).getReservDetailId().substring(2);
            if (Integer.parseInt(id) + 1 != Integer.parseInt(nextId)) {
                latestId = id;
            }
        }
        if (latestId == null)
            latestId = reservationDetailList.get(reservationDetailList.size() - 1).getReservDetailId().substring(2);
        int tempId = Integer.parseInt(latestId) + 1;
        if (tempId < 10) latestId = "rd000" + tempId;
        else if (tempId < 100) latestId = "rd00" + tempId;
        else if (tempId < 1000) latestId = "rd0" + tempId;
        else latestId = "rd" + tempId;
        return latestId;
    }

    private String getPackageDetailId() {
        List<PackageDetail> packageDetailList = this.packageDetailRepository.findAll();
        String latestId = null;
        for (int i = 0; i < packageDetailList.size(); i++) {
            if (i + 1 == packageDetailList.size() - 1) break;
            String id = packageDetailList.get(i).getPackageDetailId().substring(2);
            String nextId = packageDetailList.get(i + 1).getPackageDetailId().substring(2);
            if (Integer.parseInt(id) + 1 != Integer.parseInt(nextId)) {
                latestId = id;
            }
        }
        if (latestId == null)
            latestId = packageDetailList.get(packageDetailList.size() - 1).getPackageDetailId().substring(2);
        int tempId = Integer.parseInt(latestId) + 1;
        if (tempId < 10) latestId = "pd000" + tempId;
        else if (tempId < 100) latestId = "pd00" + tempId;
        else if (tempId < 1000) latestId = "pd0" + tempId;
        else latestId = "pd" + tempId;
        return latestId;
    }
}
