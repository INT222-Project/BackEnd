package int222.project.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "packagedetail")
public class PackageDetail {
    @Id
    private String packageDetailId;
    @ManyToOne
    @JoinColumn(name= "reservdetailid")
    @JsonBackReference
    private ReservationDetail reservDetailId;
    @ManyToOne
    @JoinColumn(name = "packageid")
    private Package packageId;
    private double packageCharge;
}
