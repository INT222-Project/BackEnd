package int222.project.backend.repositories;

import int222.project.backend.models.ReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDetailRepository extends JpaRepository <ReservationDetail,String> {
}
