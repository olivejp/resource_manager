package nc.deveo.resource_manager.repository;

import nc.deveo.resource_manager.domain.Competence;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "competence",
        path = "competence",
        collectionResourceDescription = @Description("La description de ma collection"),
        itemResourceDescription = @Description("La description de mes ITEMS")
)
public interface CompetenceRepository extends PagingAndSortingRepository<Competence, Long> {

}
