package nc.deveo.resource_manager.service;

import lombok.RequiredArgsConstructor;
import nc.deveo.resource_manager.controller.NotFoundException;
import nc.deveo.resource_manager.controller.dto.CustomerDto;
import nc.deveo.resource_manager.domain.Customer;
import nc.deveo.resource_manager.mapper.CustomerMapper;
import nc.deveo.resource_manager.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public Page<CustomerDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public CustomerDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public CustomerDto save(CustomerDto dto) {
        final Customer customerToSave = mapper.toEntity(dto);
        repository.save(customerToSave);
        return mapper.toDto(customerToSave);
    }

    public CustomerDto update(CustomerDto dto) {
        repository.findById(dto.getId()).orElseThrow(NotFoundException::new);
        return save(dto);
    }
}
