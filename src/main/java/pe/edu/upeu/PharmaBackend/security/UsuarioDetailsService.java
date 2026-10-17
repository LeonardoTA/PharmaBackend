package pe.edu.upeu.PharmaBackend.security;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.PharmaBackend.entity.Usuario;
import pe.edu.upeu.PharmaBackend.repository.UsuarioRepository;
/*
 * Puente entre la tabla usuarios y Spring Security.
 *
 * Spring Security pide los datos del usuario por su username y recibe
 * un UserDetails con: el hash de la contrasena, las authorities (todos
 * sus roles y la union de sus permisos) y si la cuenta esta habilitada.
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByUsernameIgnoreCase(username.trim())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + username));
        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(PermisosPorRol.authorities(usuario.getRoles()))
                // Deshabilitado si la cuenta o el empleado estan inactivos
                .disabled(!(Boolean.TRUE.equals(usuario.getEstado())
&& Boolean.TRUE.equals(usuario.getEmpleado().getEstado())))
                .build();
    }
}
