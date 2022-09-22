package nc.deveo.resource_manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nc.deveo.resource_manager.controller.dto.Appointment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/planning")
public class PlanningController {

    @GetMapping
    public List<Appointment> getAppointments() {
        return List.of(
                Appointment.builder()
                        .dateTimeBegin(LocalDateTime.now())
                        .dateTimeEnd(LocalDateTime.now().plus(3, ChronoUnit.DAYS))
                        .rendezVous("Rendez vous chez le docteur")
                        .build(),
                Appointment.builder()
                        .dateTimeBegin(LocalDateTime.now().plus(5, ChronoUnit.DAYS))
                        .dateTimeEnd(LocalDateTime.now().plus(5, ChronoUnit.DAYS).plus(3, ChronoUnit.HOURS))
                        .rendezVous("Rendez vous chez le coiffeur")
                        .build()
                );
    }
}
