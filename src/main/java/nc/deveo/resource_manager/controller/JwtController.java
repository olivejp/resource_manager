package nc.deveo.resource_manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nc.deveo.resource_manager.service.security.JwtService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService service;

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> jwt(@RequestBody String firebaseToken) {
        log.debug("jwt called with {}", firebaseToken);
        final String token = service.generateJwt(firebaseToken);
        return ResponseEntity.ok(token);
    }
}
