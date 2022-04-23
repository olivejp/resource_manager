package nc.deveo.resource_manager.repository;

import nc.deveo.resource_manager.domain.Teamate;
import nc.deveo.resource_manager.domain.TeamateProjection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teamate",
        path = "teamate",
        collectionResourceDescription = @Description("La description de ma collection"),
        itemResourceDescription = @Description("La description de mes ITEMS"),
        excerptProjection = TeamateProjection.class
)
public interface TeamateRepository extends PagingAndSortingRepository<Teamate, Long> {

}
