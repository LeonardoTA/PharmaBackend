package pe.edu.upeu.PharmaBackend.mapper;

import pe.edu.upeu.PharmaBackend.dto.DetalleVentaResponseDTO;
import pe.edu.upeu.PharmaBackend.dto.VentaResponseDTO;
import pe.edu.upeu.PharmaBackend.entity.Venta;

import java.util.Collections;
import java.util.List;

public class VentaMapper {

    public static VentaResponseDTO ConvertToResponse(Venta venta) {
        List<DetalleVentaResponseDTO> detalles = venta.getDetalles() == null ? Collections.emptyList() :
                venta.getDetalles()
                        .stream()
                        .map(detalle ->
                                new DetalleVentaResponseDTO(
                                        detalle.getProducto() != null ? detalle.getProducto().getId() : null,
                                        detalle.getProducto() != null ? detalle.getProducto().getNombre() : null,
                                        detalle.getCantidad(),
                                        detalle.getPrecio(),
                                        detalle.getSubtotal()
                                )
                        ).toList();

        String clienteNombre = venta.getCliente() != null
                ? venta.getCliente().getNombres() + " " + venta.getCliente().getApellidos()
                : null;

        Long clienteId = venta.getCliente() != null ? venta.getCliente().getId() : null;

        return new VentaResponseDTO(
                venta.getId(),
                venta.getFechaRegistro(),
                clienteId,
                clienteNombre,
                venta.getEstado() != null ? venta.getEstado().name() : null,
                venta.getTotal(),
                detalles
        );
    }

    public static VentaResponseDTO convertToResponse(Venta venta) {
        return ConvertToResponse(venta);
    }
}
