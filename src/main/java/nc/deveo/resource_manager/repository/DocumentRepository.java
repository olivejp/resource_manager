package nc.deveo.resource_manager.repository;

import nc.deveo.resource_manager.domain.Document;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {

}
