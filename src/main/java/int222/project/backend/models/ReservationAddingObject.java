package int222.project.backend.models;

import java.util.List;

public class ReservationAddingObject {
    private List<ReservationRequirement> reservationRequirements;
    private PaymentMethod paymentMethod;

    public ReservationAddingObject(List<ReservationRequirement> reservationRequirements, PaymentMethod paymentMethod) {
        this.reservationRequirements = reservationRequirements;
        this.paymentMethod = paymentMethod;
    }

    public List<ReservationRequirement> getReservationRequirements() {
        return reservationRequirements;
    }

    public void setReservationRequirements(List<ReservationRequirement> reservationRequirements) {
        this.reservationRequirements = reservationRequirements;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
