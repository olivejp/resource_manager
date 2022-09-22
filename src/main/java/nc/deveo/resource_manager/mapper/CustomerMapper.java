package nc.deveo.resource_manager.mapper;

import nc.deveo.resource_manager.controller.dto.CustomerDto;
import nc.deveo.resource_manager.domain.Customer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    Customer toEntity(CustomerDto dto);

    CustomerDto toDto(Customer entity);
}
