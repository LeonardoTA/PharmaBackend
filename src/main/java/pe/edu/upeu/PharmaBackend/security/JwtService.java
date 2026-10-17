package pe.edu.upeu.PharmaBackend.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
/*
 * Genera y valida los JWT de la aplicacion.
 *
 * El token se firma con HMAC-SHA (HS256/HS384/HS512 segun el largo de
 * la clave) usando un secreto que solo conoce el servidor. Cualquier
 * cambio en el token invalida la firma.
 *
 * El payload NO esta cifrado: cualquiera puede leerlo decodificando
 * Base64. Por eso nunca se guardan contrasenas ni datos sensibles en el.
 */
@Service
public class JwtService {
    private final SecretKey clave;
    private final long expiracionMinutos;
    public JwtService(
            @Value("${app.jwt.secret}") String secretoBase64,
            @Value("${app.jwt.expiracion-minutos:60}") long expiracionMinutos) {
        // La clave debe tener al menos 256 bits (32 bytes); si es mas
        // corta, Keys.hmacShaKeyFor lanza WeakKeyException al arrancar.
        this.clave = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretoBase64));
        this.expiracionMinutos = expiracionMinutos;
    }
    /*
     * Token con: sub = username, authorities = rol y permisos,
     * iat = emitido en, exp = expira en.
     */
    public String generarToken(UserDetails usuario) {
        Instant ahora = Instant.now();
        Instant expira = ahora.plus(expiracionMinutos, ChronoUnit.MINUTES);
        List<String> authorities = usuario.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("authorities", authorities)
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(expira))
                .signWith(clave)
                .compact();
    }
/*
     * Valida firma y expiracion y devuelve el username (claim "sub").
     * Lanza ExpiredJwtException si expiro y JwtException si la firma
     * o el formato no son validos.
     */
    public String extraerUsername(String token) {
        return leerClaims(token).getSubject();
    }
    public Claims leerClaims(String token) {
        return Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public long getExpiracionSegundos() {
        return expiracionMinutos * 60;
    }
}
