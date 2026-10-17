package pe.edu.upeu.PharmaBackend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import
org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import pe.edu.upeu.PharmaBackend.security.JwtAuthenticationFilter;
import pe.edu.upeu.PharmaBackend.security.JwtService;
import pe.edu.upeu.PharmaBackend.security.RestAccessDeniedHandler;
import pe.edu.upeu.PharmaBackend.security.RestAuthenticationEntryPoint;
import static pe.edu.upeu.PharmaBackend.enums.Permiso.CATALOGO_ESCRIBIR;
import static pe.edu.upeu.PharmaBackend.enums.Permiso.CATALOGO_LEER;
import static pe.edu.upeu.PharmaBackend.enums.Permiso.CLIENTES_ESCRIBIR;
import static pe.edu.upeu.PharmaBackend.enums.Permiso.CLIENTES_LEER;
import static pe.edu.upeu.PharmaBackend.enums.Permiso.USUARIOS_ADMINISTRAR;
import static pe.edu.upeu.PharmaBackend.enums.Permiso.VENTAS_LEER;
import static pe.edu.upeu.PharmaBackend.enums.Permiso.VENTAS_REGISTRAR;
/*
 * Cadena de seguridad de la API.
 *
 *  - Sin estado (STATELESS): no hay sesion en el servidor; cada peticion
 *    trae su JWT en el encabezado Authorization.
 *  - CSRF desactivado: no se usan cookies de sesion, el token viaja en
 *    un encabezado que el navegador no envia solo.
 *  - Reglas por URL y metodo HTTP: el orden importa, gana la primera
 *    regla que coincide.
 *  - @EnableMethodSecurity habilita @PreAuthorize en los controladores
 *    (se usa en ReporteController).
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final String[] RUTAS_PUBLICAS = {
            "/api/v1/auth/login",
            "/api/health",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/error"
    };
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtService jwtService,
            UserDetailsService userDetailsService,
            CorsConfigurationSource corsConfigurationSource,
            RestAuthenticationEntryPoint authenticationEntryPoint,
            RestAccessDeniedHandler accessDeniedHandler) throws Exception {
        JwtAuthenticationFilter jwtFilter =
                new JwtAuthenticationFilter(jwtService, userDetailsService);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(sesion ->
                        sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Publicas
                        .requestMatchers(RUTAS_PUBLICAS).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Catalogo: todos leen; solo ADMIN y ALMACENERO escriben
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/categorias/**", "/api/v1/productos/**")
                        .hasAuthority(CATALOGO_LEER.name())
                        .requestMatchers(
                                "/api/v1/categorias/**", "/api/v1/productos/**")
                        .hasAuthority(CATALOGO_ESCRIBIR.name())
                        // Clientes
                        .requestMatchers(HttpMethod.GET, "/api/v1/clientes/**")
                        .hasAuthority(CLIENTES_LEER.name())
                        .requestMatchers("/api/v1/clientes/**")
.hasAuthority(CLIENTES_ESCRIBIR.name())
                        // Ventas
                        .requestMatchers(HttpMethod.POST, "/api/v1/ventas")
                        .hasAuthority(VENTAS_REGISTRAR.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/ventas/**")
                        .hasAuthority(VENTAS_LEER.name())
                        .requestMatchers("/api/v1/ventas/**")
                        .hasAuthority(VENTAS_REGISTRAR.name())
                        // Administracion de la seguridad
                        .requestMatchers("/api/v1/usuarios/**",
                                "/api/v1/empleados/**", "/api/v1/roles/**")
                        .hasAuthority(USUARIOS_ADMINISTRAR.name())
                        // Todo lo demas (auth/me, reportes...) exige un usuario
                        // autenticado; los reportes ademas usan @PreAuthorize.
                        .anyRequest().authenticated())
                .exceptionHandling(errores -> errores
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    /*
     * BCrypt: hash lento y con "sal" aleatoria. La misma contrasena
     * produce un hash distinto cada vez y no se puede revertir.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*
     * Spring arma el AuthenticationManager con el UserDetailsService
     * (UsuarioDetailsService) y el PasswordEncoder declarados.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
