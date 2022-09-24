package nc.deveo.resource_manager.config.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSecurity {
    private String id;
    private String email;
    private String name;
    private String photoUrl;
    private Long expiration;
    private Boolean emailVerified;
}
