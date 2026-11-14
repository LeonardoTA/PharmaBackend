package pe.edu.upeu.PharmaBackend.auditoria;
import org.slf4j.MDC;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public record EventoAuditoria(String usuario, String accion,
                              String entidad, Long entidadId,
                              String detalle, String requestId) {
    /** Evento realizado por el usuario autenticado. */
    public static EventoAuditoria de(String accion, String entidad,
                                     Long entidadId, String detalle) {
        return new EventoAuditoria(usuarioActual(), accion, entidad, entidadId,
                recortar(detalle, 500), MDC.get("requestId"));
    }
    /** Evento de login: todavía no hay usuario autenticado. */
    public static EventoAuditoria login(String accion, String username) {
        return new EventoAuditoria(recortar(username, 50), accion, "Usuario",
                null, null, MDC.get("requestId"));
    }
    private static String usuarioActual() {
        Authentication a =
                SecurityContextHolder.getContext().getAuthentication();
        if (a == null || !a.isAuthenticated()
                || a instanceof AnonymousAuthenticationToken) {
            return "anonimo";
        }
        return a.getName();
    }
    private static String recortar(String texto, int maximo) {
        return texto == null || texto.length() <= maximo
                ? texto : texto.substring(0, maximo);
    }
}

