package int222.project.backend.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "package")
public class Package {
    @Id
    private String packageId;
    private String name;
    private String description;
    private double packageCharge;

    public Package() {

    }

    public Package(String packageId, String name, String description, double packageCharge) {
        this.packageId = packageId;
        this.name = name;
        this.description = description;
        this.packageCharge = packageCharge;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPackageCharge() {
        return packageCharge;
    }

    public void setPackageCharge(double packageCharge) {
        this.packageCharge = packageCharge;
    }
}
