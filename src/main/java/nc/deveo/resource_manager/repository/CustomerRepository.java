package nc.deveo.resource_manager.repository;

import nc.deveo.resource_manager.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

}
