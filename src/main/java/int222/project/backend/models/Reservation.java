package int222.project.backend.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="reservation")
public class Reservation {
    @Id
    private String reservNo;
    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customerId;
    private Date paymentDate;
    private Date reservationDate;
    @ManyToOne
    @JoinColumn(name = "paymentmethodid")
    private PaymentMethod paymentMethodId;
    private double subTotal;
    @ManyToOne
    @JoinColumn(name = "repid")
    private Receptionist repId;

    public Reservation() {
    }

    public Reservation(String reservNo, Customer customerId, Date paymentDate, Date reservationDate, PaymentMethod paymentMethodId, double subTotal, Receptionist repId) {
        this.reservNo = reservNo;
        this.customerId = customerId;
        this.paymentDate = paymentDate;
        this.reservationDate = reservationDate;
        this.paymentMethodId = paymentMethodId;
        this.subTotal = subTotal;
        this.repId = repId;
    }

    public String getReservNo() {
        return reservNo;
    }

    public void setReservNo(String reservNo) {
        this.reservNo = reservNo;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
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

    public PaymentMethod getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(PaymentMethod paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Receptionist getRepId() {
        return repId;
    }

    public void setRepId(Receptionist repId) {
        this.repId = repId;
    }
}
