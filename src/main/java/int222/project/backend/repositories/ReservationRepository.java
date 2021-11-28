package int222.project.backend.repositories;

import int222.project.backend.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    @Query(value = "select distinct rs.* from reservation rs , (select * from reservationdetail) rd where rs.reservno = rd.reservno and (rd.status='undone' or rd.status='done') and rs.status='paid' order by rs.status desc;", nativeQuery = true)
    List<Reservation> getUnsuccessReservation();

    @Query("select rs from Reservation rs inner join ReservationDetail rd on rs.reservNo = rd.reservNo where rd.reservDetailId = ?1")
    Reservation getReservationByReservationDetailId(String reservDetailId);

    @Query("select rs from Reservation rs where rs.customerId.customerId = ?1")
    List<Reservation> getReservationByCustomerId(String customerId);

    @Query("select rs from Reservation rs where rs.status='unpaid'")
    List<Reservation> getUnpaidReservation();

    @Query("select rs from Reservation rs where rs.status='paid'")
    List<Reservation> getSuccessReservation();
}
