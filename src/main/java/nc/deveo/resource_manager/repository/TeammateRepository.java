package nc.deveo.resource_manager.repository;

import nc.deveo.resource_manager.domain.Teammate;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeammateRepository extends PagingAndSortingRepository<Teammate, Long> {

    boolean existsByEmail(String email);
}
