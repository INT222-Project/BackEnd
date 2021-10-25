package int222.project.backend.repositories;

import int222.project.backend.models.Reservation;
import int222.project.backend.models.ReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationDetailRepository extends JpaRepository <ReservationDetail,String> {

    @Query("select rd from ReservationDetail rd inner join Reservation r on  rd.reservNo = r.reservNo where r.reservNo = ?1")
    List<ReservationDetail> getAllReservationDetailsByReservNo(String reservNo);
}
