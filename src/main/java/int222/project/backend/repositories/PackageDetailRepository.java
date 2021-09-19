package int222.project.backend.repositories;

import int222.project.backend.models.PackageDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageDetailRepository extends JpaRepository<PackageDetail,String> {
}
