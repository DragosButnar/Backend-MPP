package a2.A2.Repository;
import a2.A2.Model.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFranchiseRepository extends JpaRepository<Franchise, Long> {
    Optional<Franchise> findByName(String name);
}