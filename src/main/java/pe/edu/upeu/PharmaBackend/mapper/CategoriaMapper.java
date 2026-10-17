package pe.edu.upeu.PharmaBackend.mapper;

import pe.edu.upeu.PharmaBackend.dto.CategoriaRequestDTO;
import pe.edu.upeu.PharmaBackend.dto.CategoriaResponseDTO;
import pe.edu.upeu.PharmaBackend.entity.Categoria;

public class CategoriaMapper {

    public static CategoriaResponseDTO ConvertToResponse(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getEstado(),
                categoria.getFechaCreacion(),
                categoria.getFechaModificacion()
        );
    }

    public static Categoria ConvertFromRequest(Categoria categoria, CategoriaRequestDTO request) {
        categoria.setNombre(request.getNombre() != null ? request.getNombre().trim() : null);
        categoria.setDescripcion(request.getDescripcion());
        categoria.setEstado(request.getEstado());
        return categoria;
    }

    public static CategoriaResponseDTO convertToResponse(Categoria categoria) {
        return ConvertToResponse(categoria);
    }

    public static Categoria convertFromRequest(Categoria categoria, CategoriaRequestDTO request) {
        return ConvertFromRequest(categoria, request);
    }
}
