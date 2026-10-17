package pe.edu.upeu.PharmaBackend.mapper;

import pe.edu.upeu.PharmaBackend.dto.ProductoRequestDTO;
import pe.edu.upeu.PharmaBackend.dto.ProductoResponseDTO;
import pe.edu.upeu.PharmaBackend.entity.Categoria;
import pe.edu.upeu.PharmaBackend.entity.Producto;

public class ProductoMapper {

    public static ProductoResponseDTO ConvertToResponse(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getEstado(),
                producto.getCategoria() != null ? producto.getCategoria().getId() : null,
                producto.getCategoria() != null ? producto.getCategoria().getNombre() : null,
                producto.getFechaCreacion(),
                producto.getFechaModificacion()
        );
    }

    public static Producto ConvertFromRequest(Producto producto, ProductoRequestDTO request, Categoria categoria) {
        producto.setNombre(request.getNombre() != null ? request.getNombre().trim() : null);
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);
        producto.setEstado(request.getEstado());
        return producto;
    }

    public static ProductoResponseDTO convertToResponse(Producto producto) {
        return ConvertToResponse(producto);
    }

    public static Producto convertFromRequest(Producto producto, ProductoRequestDTO request, Categoria categoria) {
        return ConvertFromRequest(producto, request, categoria);
    }
}
