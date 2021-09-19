package int222.project.backend.repositories;

import int222.project.backend.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, String> { }
