package nc.deveo.resource_manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class AliveController {

    @GetMapping("/alive")
    public ResponseEntity<Void> isAlive() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
