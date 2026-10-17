package pe.edu.upeu.PharmaBackend.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.PharmaBackend.dto.auth.LoginRequestDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.LoginResponseDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.UsuarioResponseDTO;
import pe.edu.upeu.PharmaBackend.entity.Usuario;
import pe.edu.upeu.PharmaBackend.exception.RecursoNoEncontradoException;
import pe.edu.upeu.PharmaBackend.mapper.UsuarioMapper;
import pe.edu.upeu.PharmaBackend.repository.UsuarioRepository;
import pe.edu.upeu.PharmaBackend.security.JwtService;
import pe.edu.upeu.PharmaBackend.security.PermisosPorRol;
import pe.edu.upeu.PharmaBackend.service.service.AuthService;
import java.util.List;
@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log =
            LoggerFactory.getLogger(AuthServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UsuarioRepository usuarioRepository,
UsuarioMapper usuarioMapper, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }
    /*
     * 1. AuthenticationManager busca al usuario (UsuarioDetailsService),
     *    verifica que este habilitado y compara la contrasena con el hash
     *    usando BCrypt. Si algo falla lanza una AuthenticationException,
     *    que GlobalExceptionHandler convierte en 401.
     * 2. Si todo es correcto, se emite el JWT.
     *
     * La contrasena NUNCA se escribe en el log.
     */
    @Override
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO request) {
        String username = request.getUsername().trim();
        log.info("Intento de inicio de sesión: usuario={}", username);
        Authentication autenticacion = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, request.getPassword()));
        UserDetails principal = (UserDetails) autenticacion.getPrincipal();
        Usuario usuario = buscar(principal.getUsername());
        String token = jwtService.generarToken(principal);
        List<String> roles = usuarioMapper.nombresRoles(usuario.getRoles());
        log.info("Inicio de sesión correcto: usuario={} roles={}",
                usuario.getUsername(), roles);
        return new LoginResponseDTO(
                token,
                "Bearer",
                jwtService.getExpiracionSegundos(),
                usuario.getUsername(),
                usuario.getEmpleado().getNombreCompleto(),
                roles,
                PermisosPorRol.permisos(usuario.getRoles())
        );
    }
    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO usuarioActual(String username) {
        return usuarioMapper.toUsuarioResponse(buscar(username));
    }
    @Override
    @Transactional
    public void cambiarPassword(String username, pe.edu.upeu.PharmaBackend.dto.auth.CambioPasswordRequestDTO request) {
        Usuario usuario = buscar(username);
        if (!passwordEncoder.matches(request.getPasswordActual(), usuario.getPassword()))
            throw new pe.edu.upeu.PharmaBackend.exception.ReglaNegocioException("La contraseña actual no es correcta");
        if (passwordEncoder.matches(request.getPasswordNueva(), usuario.getPassword()))
            throw new pe.edu.upeu.PharmaBackend.exception.ReglaNegocioException("La nueva contraseña debe ser distinta de la actual");
        if (request.getPasswordNueva().getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72)
            throw new pe.edu.upeu.PharmaBackend.exception.ReglaNegocioException("La contraseña no puede superar 72 bytes");
        usuario.setPassword(passwordEncoder.encode(request.getPasswordNueva()));
        usuarioRepository.save(usuario);
        log.info("Contraseña actualizada: usuario={}", usuario.getUsername());
    }
    private Usuario buscar(String username) {
        return usuarioRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado: " + username));
    }
}
