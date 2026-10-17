package pe.edu.upeu.PharmaBackend.security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
/*
 * 401 Unauthorized: la peticion llego a un endpoint protegido SIN un
 * usuario autenticado (no envio token, el token expiro o no es valido).
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        Object motivo = request.getAttribute(JwtAuthenticationFilter.ATRIBUTO_ERROR);
        String mensaje = motivo != null
                ? motivo.toString()
                : "Debe iniciar sesión para acceder a este recurso";
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
RespuestaErrorSeguridad.escribir(
                request,
                response,
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                mensaje);
    }
}
