package pe.edu.upeu.PharmaBackend.security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
/*
* Escribe los errores 401 y 403 con el mismo formato que
 * ErrorResponseDTO del GlobalExceptionHandler.
* Hace falta porque estos errores ocurren en los filtros de seguridad,
 * ANTES de llegar a los controladores, y alli @RestControllerAdvice no
 * interviene.
 */
final class RespuestaErrorSeguridad {
    private RespuestaErrorSeguridad() {
    }
    static void escribir(
            HttpServletRequest request,
            HttpServletResponse response,
            int status,
            String error,
            String mensaje) throws IOException {
        String json = "{"
                + "\"timestamp\":\"" + LocalDateTime.now() + "\","
                + "\"status\":" + status + ","
                + "\"error\":\"" + escapar(error) + "\","
                + "\"message\":\"" + escapar(mensaje) + "\","
                + "\"path\":\"" + escapar(request.getRequestURI()) + "\","
                + "\"validationErrors\":null"
                + "}";
        response.setStatus(status);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
    private static String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
