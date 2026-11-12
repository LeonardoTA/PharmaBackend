package pe.edu.upeu.PharmaBackend.config;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditor")
public class AuditoriaConfig {
    @Bean
    public AuditorAware<String> auditor() {
        return () -> Optional
                .ofNullable(SecurityContextHolder.getContext()
                        .getAuthentication())
                .filter(Authentication::isAuthenticated)
                .filter(a -> !(a instanceof AnonymousAuthenticationToken))
                .map(Authentication::getName)
                .or(() -> Optional.of("sistema"));
    }
}

