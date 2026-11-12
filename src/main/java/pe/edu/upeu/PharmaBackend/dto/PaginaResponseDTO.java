package pe.edu.upeu.PharmaBackend.dto;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
public record PaginaResponseDTO<T>(List<T> contenido, int pagina, int tamanio,
        long totalElementos, int totalPaginas, boolean ultima) implements Serializable {
    public static <T> PaginaResponseDTO<T> de(Page<T> p) {
        return new PaginaResponseDTO<>(p.getContent(), p.getNumber(), p.getSize(),
                p.getTotalElements(), p.getTotalPages(), p.isLast());
    }
}
