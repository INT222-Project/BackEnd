package int222.project.backend.repositories;

import int222.project.backend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,String> {
    Optional<Customer> findCustomerByEmail(String email);
}
