package nc.deveo.resource_manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nc.deveo.resource_manager.controller.dto.CustomerDto;
import nc.deveo.resource_manager.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public ResponseEntity<Page<CustomerDto>> getAll(final Pageable pageable) {
        log.debug("REST request to get all teamate");
        final Page<CustomerDto> teamatePage = service.findAll(pageable);
        return ResponseEntity.ok(teamatePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> get(@PathVariable final Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> create(@RequestBody @Valid CustomerDto teammateToCreate) {
        final CustomerDto teammateCreated = service.save(teammateToCreate);
        return ResponseEntity.ok().body(teammateCreated);
    }

    @PutMapping
    public ResponseEntity<CustomerDto> update(@RequestBody @Valid CustomerDto customer) {
        final CustomerDto teammateUpdated = service.update(customer);
        return ResponseEntity.ok().body(teammateUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
