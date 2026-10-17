package pe.edu.upeu.PharmaBackend.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.upeu.PharmaBackend.entity.Rol;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class JwtServiceTest {
    // Clave de prueba en Base64 (48 bytes = 384 bits)
    private static final String SECRETO =
            "w9Z6dP/dzykNH/uiI3Z/KGjBlP0H4Ji+Tuq4fm2cTNuLD4jWafu1gBL74V2SJEvy";
    private static final String OTRO_SECRETO =
            "q8Bx3mN0vRk7TzYp1LcW5aHs9JdFg2Ue4OiPlKjMnBvCxZaSdFgHjKlQwErTyUiO";
    private final JwtService jwtService = new JwtService(SECRETO, 60);
    private UserDetails vendedor() {
        return User.withUsername("vendedor")
                .password("no-importa")
                .authorities(PermisosPorRol.authorities(
                        List.of(new Rol(2L, "VENDEDOR", null))))
.build();
    }
    @Test
    @DisplayName("El token generado contiene el username y las authorities")
    void generarYLeerToken() {
        String token = jwtService.generarToken(vendedor());
        Claims claims = jwtService.leerClaims(token);
        assertThat(token.split("\\.")).hasSize(3);
        assertThat(claims.getSubject()).isEqualTo("vendedor");
        assertThat(claims.getExpiration()).isAfter(claims.getIssuedAt());
        List<String> authorities = claims.get("authorities", List.class);
        assertThat(authorities)
                .contains("ROLE_VENDEDOR", "VENTAS_REGISTRAR")
                .doesNotContain("REPORTES_VER");
        assertThat(jwtService.extraerUsername(token)).isEqualTo("vendedor");
    }
@Test
    @DisplayName("Un token alterado no pasa la verificación de firma")
    void tokenAlterado() {
        String token = jwtService.generarToken(vendedor());
        String alterado = token.substring(0, token.length() - 2)
                + (token.endsWith("AA") ? "BB" : "AA");
        assertThatThrownBy(() -> jwtService.extraerUsername(alterado))
                .isInstanceOf(JwtException.class);
    }
@Test
    @DisplayName("Un token firmado con otra clave es rechazado")
    void tokenDeOtraClave() {
        String ajeno = new JwtService(OTRO_SECRETO, 60).generarToken(vendedor());
        assertThatThrownBy(() -> jwtService.extraerUsername(ajeno))
                .isInstanceOf(JwtException.class);
    }
    @Test
    @DisplayName("Un token expirado lanza ExpiredJwtException")
    void tokenExpirado() {
        // Expiracion negativa: el token nace vencido
        String vencido = new JwtService(SECRETO, -1).generarToken(vendedor());
        assertThatThrownBy(() -> jwtService.extraerUsername(vencido))
                .isInstanceOf(ExpiredJwtException.class);
    }
@Test
@DisplayName("La expiración se informa en segundos")
    void expiracionEnSegundos() {
        assertThat(jwtService.getExpiracionSegundos()).isEqualTo(3600);
    }
}
