package int222.project.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservationdetail")
public class ReservationDetail {
    @Id
    private String reservDetailId;
    @ManyToOne
    @JoinColumn(name= "reservno")
    @JsonBackReference
    private Reservation reservNo;
    @ManyToOne
    @JoinColumn(name = "roomid")
    private Room room;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTC")
    private Date checkInDate;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTC")
    private Date checkOutDate;
    private int numOfRest;
    private double total;
    private String status;
    @JsonManagedReference
    @OneToMany(mappedBy = "reservDetailId",cascade = CascadeType.ALL,targetEntity = PackageDetail.class)
    @MapsId("reservDetailId")
    private List<PackageDetail> packageDetailList;

    public ReservationDetail() {
    }

    public ReservationDetail(String reservDetailId, Reservation reservNo, Room room, Date checkInDate, Date checkOutDate, int numOfRest, double total, String status, List<PackageDetail> packageDetailList) {
        this.reservDetailId = reservDetailId;
        this.reservNo = reservNo;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numOfRest = numOfRest;
        this.total = total;
        this.status = status;
        this.packageDetailList = packageDetailList;
    }

    public String getReservDetailId() {
        return reservDetailId;
    }

    public void setReservDetailId(String reservDetailId) {
        this.reservDetailId = reservDetailId;
    }

    public Reservation getReservNo() {
        return reservNo;
    }

    public void setReservNo(Reservation reservNo) {
        this.reservNo = reservNo;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PackageDetail> getPackageDetailList() {
        return packageDetailList;
    }

    public void setPackageDetailList(List<PackageDetail> packageDetailList) {
        this.packageDetailList = packageDetailList;
    }

    @Override
    public String toString() {
        return "ReservationDetail{" +
                "reservDetailId='" + reservDetailId + '\'' +
                ", reservNo=" + reservNo +
                ", room=" + room +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfRest=" + numOfRest +
                ", total=" + total +
                ", status='" + status + '\'' +
                ", packageDetailList=" + packageDetailList +
                '}';
    }
}
