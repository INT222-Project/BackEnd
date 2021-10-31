package int222.project.backend.repositories;

import int222.project.backend.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    @Query("select rs from Reservation rs where rs.repId is null")
    List<Reservation> getUnsuccessReservation();

    @Query("select rs from Reservation rs where rs.customerId.customerId = ?1")
    List<Reservation> getReservationByCustomerId(String customerId);
}
