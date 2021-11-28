package int222.project.backend.models;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    private String customerId;
    private String email;
    private String password;
    private String fName;
    private String lName;
    private String telNo;
    private String address;

    public Customer() {

    }

    public Customer(String customerId, String email, String password, String fName, String lName, String telNo, String address) {
        this.customerId = customerId;
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.telNo = telNo;
        this.address = address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
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
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
