package int222.project.backend.repositories;

import int222.project.backend.models.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package,String> {
}
