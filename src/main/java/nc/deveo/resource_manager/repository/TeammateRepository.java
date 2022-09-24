package nc.deveo.resource_manager.repository;

import nc.deveo.resource_manager.domain.Teammate;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TeammateRepository extends PagingAndSortingRepository<Teammate, Long> {

    boolean existsByEmail(String email);

    Optional<Teammate> findByEmail(String email);
}
