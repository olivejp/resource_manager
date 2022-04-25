package nc.deveo.resource_manager.repository;

import nc.deveo.resource_manager.domain.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

}
