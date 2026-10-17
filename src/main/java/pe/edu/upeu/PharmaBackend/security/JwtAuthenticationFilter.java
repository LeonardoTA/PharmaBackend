package pe.edu.upeu.PharmaBackend.security;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/** Valida el JWT y consulta estado y permisos actuales en cada petición. */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String ATRIBUTO_ERROR = "jwt.error";
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService; this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            try {
                String username = jwtService.extraerUsername(header.substring(7).trim());
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails user = userDetailsService.loadUserByUsername(username);
                    if (user.isEnabled()) {
                        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else request.setAttribute(ATRIBUTO_ERROR, "El usuario está deshabilitado");
                }
            } catch (ExpiredJwtException ex) {
                request.setAttribute(ATRIBUTO_ERROR, "El token ha expirado. Inicie sesión nuevamente");
            } catch (UsernameNotFoundException ex) {
                request.setAttribute(ATRIBUTO_ERROR, "El usuario del token no existe");
            } catch (JwtException | IllegalArgumentException ex) {
                log.warn("Token inválido en {}", request.getRequestURI());
                request.setAttribute(ATRIBUTO_ERROR, "El token no es válido");
            }
        }
        chain.doFilter(request, response);
    }
}
