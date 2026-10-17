package pe.edu.upeu.PharmaBackend.security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
/*
 * 403 Forbidden: el usuario SI esta autenticado (token valido), pero
 * su rol no tiene el permiso que exige el endpoint.
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger log =
            LoggerFactory.getLogger(RestAccessDeniedHandler.class);
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        log.warn("Acceso denegado: {} {} usuario={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getUserPrincipal() != null
                        ? request.getUserPrincipal().getName()
                        : "anónimo");
        RespuestaErrorSeguridad.escribir(
                request,
                response,
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                "No tiene permisos para realizar esta operación");
    }
}
