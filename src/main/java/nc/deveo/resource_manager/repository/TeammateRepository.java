package nc.deveo.resource_manager.repository;

import nc.deveo.resource_manager.domain.Document;
import nc.deveo.resource_manager.domain.Teammate;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TeammateRepository extends PagingAndSortingRepository<Teammate, Long> {
    List<Teammate> findAllByListDocumentContaining(Document document);
}
