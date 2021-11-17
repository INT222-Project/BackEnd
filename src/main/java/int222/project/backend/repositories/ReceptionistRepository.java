package int222.project.backend.repositories;

import int222.project.backend.models.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceptionistRepository extends JpaRepository<Receptionist,String> {
    Optional<Receptionist> findReceptionistByEmail(String s);
}
