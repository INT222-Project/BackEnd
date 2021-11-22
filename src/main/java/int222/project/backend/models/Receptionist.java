package int222.project.backend.models;

import javax.persistence.*;

@Entity
@Table(name="receptionist")
public class Receptionist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String repId;
    private String email;
    private String password;
    private String fName;
    private String lName;
    private String telNo;
    private String address;

    public Receptionist() {
    }

    public Receptionist(String repId, String email, String password, String fName, String lName, String telNo, String address) {
        this.repId = repId;
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.telNo = telNo;
        this.address = address;
    }

    public String getRepId() {
        return repId;
    }

    public void setRepId(String repId) {
        this.repId = repId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Receptionist{" +
                "repId='" + repId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
