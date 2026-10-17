package pe.edu.upeu.PharmaBackend.security;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import pe.edu.upeu.PharmaBackend.entity.Rol;
import pe.edu.upeu.PharmaBackend.enums.Permiso;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
class PermisosPorRolTest {
    private static final Rol ADMIN = new Rol(1L, "ADMIN", null);
private static final Rol VENDEDOR = new Rol(2L, "VENDEDOR", null);
    private static final Rol ALMACENERO = new Rol(3L, "ALMACENERO", null);
    private List<String> authorities(Rol... roles) {
        return PermisosPorRol.authorities(List.of(roles)).stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
    @Test
    @DisplayName("ADMIN tiene todos los permisos")
    void adminTieneTodo() {
        assertThat(PermisosPorRol.de("ADMIN")).containsExactlyInAnyOrder(Permiso.values());
        assertThat(authorities(ADMIN)).contains("ROLE_ADMIN", "REPORTES_VER", "USUARIOS_ADMINISTRAR");
    }
    @Test
    @DisplayName("VENDEDOR registra ventas, pero no ve reportes ni edita el catÃ¡logo")
    void vendedor() {
        assertThat(authorities(VENDEDOR))
                .contains("ROLE_VENDEDOR", "VENTAS_REGISTRAR", "CATALOGO_LEER")
                .doesNotContain("REPORTES_VER", "CATALOGO_ESCRIBIR", "USUARIOS_ADMINISTRAR");
    }
    @Test
    @DisplayName("Con dos roles recibe la uniÃ³n de sus permisos, sin repetidos")
    void variosRoles() {
        assertThat(authorities(VENDEDOR, ALMACENERO))
                .contains("ROLE_ALMACENERO", "ROLE_VENDEDOR",
                        "CATALOGO_ESCRIBIR", "VENTAS_REGISTRAR")
                .doesNotContain("REPORTES_VER")
                .doesNotHaveDuplicates();
    }
    @Test
    @DisplayName("Un rol sin permisos definidos solo aporta ROLE_<nombre>")
    void rolDesconocido() {
        assertThat(authorities(new Rol(9L, "AUDITOR", null)))
                .containsExactly("ROLE_AUDITOR");
    }
}
