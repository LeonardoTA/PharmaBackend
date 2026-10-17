package pe.edu.upeu.PharmaBackend.security;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pe.edu.upeu.PharmaBackend.entity.Rol;
import pe.edu.upeu.PharmaBackend.enums.Permiso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
/*
 * Permisos que otorga cada rol.
 *
 * Los roles estan en la tabla roles; los permisos, aqui. Si un usuario
 * tiene varios roles (relacion muchos a muchos), recibe la UNION de sus
 * permisos. Un rol que no figura en el mapa no otorga ningun permiso.
 */
public final class PermisosPorRol {
    private static final Map<String, Set<Permiso>> PERMISOS = Map.of(
            "ADMIN", Collections.unmodifiableSet(EnumSet.allOf(Permiso.class)),
            "VENDEDOR", Collections.unmodifiableSet(EnumSet.of(
                    Permiso.CATALOGO_LEER,
                    Permiso.CLIENTES_LEER,
                    Permiso.CLIENTES_ESCRIBIR,
                    Permiso.VENTAS_LEER,
                    Permiso.VENTAS_REGISTRAR)),
            "ALMACENERO", Collections.unmodifiableSet(EnumSet.of(
                    Permiso.CATALOGO_LEER,
                    Permiso.CATALOGO_ESCRIBIR))
    );
    private PermisosPorRol() {
    }
    public static Set<Permiso> de(String nombreRol) {
        return PERMISOS.getOrDefault(nombreRol, Set.of());
    }
    /* Union de los permisos de todos los roles, ordenada alfabeticamente. */
    public static List<String> permisos(Collection<Rol> roles) {
        Set<String> nombres = new TreeSet<>();
        roles.forEach(rol -> de(rol.getNombre())
                .forEach(permiso -> nombres.add(permiso.name())));
        return new ArrayList<>(nombres);
    }
    /*
     * Authorities para Spring Security: un "ROLE_<rol>" por cada rol (para
     * hasRole) mas un authority por cada permiso (para hasAuthority).
     */
    public static List<GrantedAuthority> authorities(Collection<Rol> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.stream()
                .map(Rol::getNombre)
                .sorted()
                .forEach(nombre -> authorities.add(new SimpleGrantedAuthority("ROLE_" + nombre)));
        permisos(roles).forEach(permiso ->
                authorities.add(new SimpleGrantedAuthority(permiso)));
        return authorities;
    }
}
