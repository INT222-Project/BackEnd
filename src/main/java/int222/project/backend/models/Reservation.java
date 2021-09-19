package int222.project.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="reservation")
public class Reservation {
    @Id
    private String reservNo;
    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customerId;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTC")
    private Date paymentDate;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTC")
    private Date reservationDate;
    @ManyToOne
    @JoinColumn(name = "paymentmethodid")
    private PaymentMethod paymentMethodId;
    private double subTotal;
    @ManyToOne
    @JoinColumn(name = "repid")
    private Receptionist repId;
    @JsonManagedReference
    @OneToMany(mappedBy = "reservNo",cascade = CascadeType.ALL, targetEntity = ReservationDetail.class)
    @MapsId("reservNo")
    private List<ReservationDetail> reservationDetailList;

    public Reservation() {
    }

    public Reservation(String reservNo, Customer customerId, Date paymentDate, Date reservationDate, PaymentMethod paymentMethodId, double subTotal, Receptionist repId, List<ReservationDetail> reservationDetailList) {
        this.reservNo = reservNo;
        this.customerId = customerId;
        this.paymentDate = paymentDate;
        this.reservationDate = reservationDate;
        this.paymentMethodId = paymentMethodId;
        this.subTotal = subTotal;
        this.repId = repId;
        this.reservationDetailList = reservationDetailList;
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

    public List<ReservationDetail> getReservationDetailList() {
        return reservationDetailList;
    }

    public void setReservationDetailList(List<ReservationDetail> reservationDetailList) {
        this.reservationDetailList = reservationDetailList;
    }
}
