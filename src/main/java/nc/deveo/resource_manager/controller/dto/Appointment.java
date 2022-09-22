package nc.deveo.resource_manager.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Appointment {
    public LocalDateTime dateTimeBegin;
    public LocalDateTime dateTimeEnd;
    public String rendezVous;
}
