package pe.edu.upeu.PharmaBackend.util;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pe.edu.upeu.PharmaBackend.exception.ReglaNegocioException;
public final class PaginacionUtil {
    private PaginacionUtil() {}
    public static Pageable construir(int pagina, int tamanio, String campo,
            String direccion, Set<String> campos, String defecto) {
        if (pagina < 0) throw new ReglaNegocioException("La página debe ser mayor o igual a cero.");
        if (tamanio < 1 || tamanio > 100)
            throw new ReglaNegocioException("El tamaño de página debe estar entre 1 y 100.");
        campo = campo == null || campo.isBlank() ? defecto : campo.trim();
        if (!campos.contains(campo)) throw new ReglaNegocioException("El campo de ordenamiento no está permitido.");
        if (!"asc".equalsIgnoreCase(direccion) && !"desc".equalsIgnoreCase(direccion))
            throw new ReglaNegocioException("La dirección debe ser asc o desc.");
        Sort sort = Sort.by(Sort.Direction.fromString(direccion), campo);
        if (!campo.equals("id")) sort = sort.and(Sort.by("id"));
        return PageRequest.of(pagina, tamanio, sort);
    }
}
