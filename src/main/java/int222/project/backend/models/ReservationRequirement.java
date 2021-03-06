package int222.project.backend.models;

import int222.project.backend.models.Customer;
import int222.project.backend.models.Package;
import int222.project.backend.models.PaymentMethod;
import int222.project.backend.models.Room;

import java.util.Date;
import java.util.List;

public class ReservationRequirement {
    private Customer customer;
    private Date paymentDate;
    private Date reservationDate;
    private double subtotal;
    private Date checkInDate;
    private Date checkOutDate;
    private int numOfRest;
    private Room room;
    private List<Package> packages;

    public ReservationRequirement(Customer customer, Date paymentDate, Date reservationDate, double subtotal, Date checkInDate, Date checkOutDate, int numOfRest, Room room, List<Package> packages) {
        this.customer = customer;
        this.paymentDate = paymentDate;
        this.reservationDate = reservationDate;
        this.subtotal = subtotal;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numOfRest = numOfRest;
        this.room = room;
        this.packages = packages;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumOfRest() {
        return numOfRest;
    }

    public void setNumOfRest(int numOfRest) {
        this.numOfRest = numOfRest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        return "ReservationRequirement{" +
                "customer=" + customer +
                ", paymentDate=" + paymentDate +
                ", reservationDate=" + reservationDate +
                ", subtotal=" + subtotal +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfRest=" + numOfRest +
                ", room=" + room +
                ", packages=" + packages +
                '}';
    }
}
