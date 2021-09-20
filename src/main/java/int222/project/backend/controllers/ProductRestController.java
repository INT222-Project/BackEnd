package int222.project.backend.controllers;

import int222.project.backend.models.*;
import int222.project.backend.models.Package;
import int222.project.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081"}, allowedHeaders = "*")
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
    @Autowired
    RoomTypeRepository roomTypeRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ReceptionistRepository receptionistRepository;

    // Package
    @GetMapping("/api/package/{packageId}")
    public Package getPackage(@PathVariable String packageId){ return packageRepository.findById(packageId).orElse(null); }

    @GetMapping("/api/packages")
    public List<Package> getAllPackages(){ return packageRepository.findAll(); }

    // Payment Method
    @GetMapping("/api/paymentMethod/{methodId}")
    public PaymentMethod getPaymentMethod(@PathVariable int methodId){ return paymentMethodRepository.findById(methodId).orElse(null); }

    @GetMapping("/api/paymentMethods")
    public List<PaymentMethod> getAllPaymentMethods(){ return paymentMethodRepository.findAll(); }

    // Customer
    @GetMapping("/api/customer/{customerId}")
    public Customer getCustomer (@PathVariable String customerId){ return customerRepository.findById(customerId).orElse(null); }

    @GetMapping("/api/customers")
    public List<Customer> getAllCustomers(){ return customerRepository.findAll(); }

    // Receptionist
    @GetMapping("/api/receptionist/{repId}")
    public Receptionist getReceptionist (@PathVariable String repId){ return receptionistRepository.findById(repId).orElse(null); }

    @GetMapping("/api/receptionists")
    public List<Receptionist> getAllReceptionists(){ return receptionistRepository.findAll(); }

    // Reservation
    @GetMapping("/api/reservation/{reservNo}")
    public Reservation getReservation (@PathVariable String reservNo){ return reservationRepository.findById(reservNo).orElse(null); }

    @GetMapping("/api/reservations")
    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    // Reservation Detail
    @GetMapping("/api/reservationDetail/{reservDetailId}")
    public ReservationDetail getReservationDetail (@PathVariable String reservDetailId){ return reservationDetailRepository.findById(reservDetailId).orElse(null); }

    @GetMapping("/api/reservationDetails")
    public List<ReservationDetail> getAllReservationDetails(){
        return reservationDetailRepository.findAll();
    }

    // Package Detail
    @GetMapping("/api/packageDetail/{packageDetailId}")
    public PackageDetail getPackageDetail (@PathVariable String packageDetailId){ return packageDetailRepository.findById(packageDetailId).orElse(null); }

    @GetMapping("/api/packageDetails")
    public List<PackageDetail> getAllPackageDetails(){ return packageDetailRepository.findAll(); }

    // Room
    @GetMapping("/api/room/{roomId}")
    public Room getRoom (@PathVariable int roomId){ return roomRepository.findById(roomId).orElse(null); }

    @GetMapping("/api/rooms")
    public List<Room> getAllRooms(){ return roomRepository.findAll(); }

    // Room Type
    @GetMapping("/api/roomType/{roomTypeId}")
    public RoomType getRoomType (@PathVariable int roomTypeId){ return roomTypeRepository.findById(roomTypeId).orElse(null); }

    @GetMapping("/api/roomTypes")
    public List<RoomType> getAllRoomTypes(){ return roomTypeRepository.findAll(); }
}
