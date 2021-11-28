package int222.project.backend.repositories;

import int222.project.backend.models.PackageDetail;
import int222.project.backend.models.ReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageDetailRepository extends JpaRepository<PackageDetail, String> {

    @Query("select pd from ReservationDetail rd inner join PackageDetail pd on  rd.reservDetailId = pd.reservDetailId where rd.reservDetailId = ?1")
    List<PackageDetail> getAllPackageDetailsByReservationDetail(String reservationDetailId);
}
