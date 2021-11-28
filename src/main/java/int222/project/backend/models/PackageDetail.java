package int222.project.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "packagedetail")
public class PackageDetail {
    @Id
    private String packageDetailId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "reservdetailid")
    private ReservationDetail reservDetailId;
    @ManyToOne
    @JoinColumn(name = "packageid")
    private Package packageId;
    private double packageCharge;

    public PackageDetail() {
    }

    public PackageDetail(String packageDetailId, ReservationDetail reservDetailId, Package packageId, double packageCharge) {
        this.packageDetailId = packageDetailId;
        this.reservDetailId = reservDetailId;
        this.packageId = packageId;
        this.packageCharge = packageCharge;
    }

    public String getPackageDetailId() {
        return packageDetailId;
    }

    public void setPackageDetailId(String packageDetailId) {
        this.packageDetailId = packageDetailId;
    }

    public ReservationDetail getReservDetailId() {
        return reservDetailId;
    }

    public void setReservDetailId(ReservationDetail reservDetailId) {
        this.reservDetailId = reservDetailId;
    }

    public Package getPackageId() {
        return packageId;
    }

    public void setPackageId(Package packageId) {
        this.packageId = packageId;
    }

    public double getPackageCharge() {
        return packageCharge;
    }

    public void setPackageCharge(double packageCharge) {
        this.packageCharge = packageCharge;
    }
}
