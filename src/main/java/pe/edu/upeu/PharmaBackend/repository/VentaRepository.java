package pe.edu.upeu.PharmaBackend.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.PharmaBackend.entity.Venta;
import pe.edu.upeu.PharmaBackend.enums.EstadoVenta;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("""
            select distinct v
            from Venta v
            left join fetch v.cliente c
            left join fetch v.detalles d
            left join fetch d.producto p
            where (:clienteId is null or c.id = :clienteId)
              and (:estado is null or v.estado = :estado)
              and (:desde is null or v.fechaRegistro >= :desde)
              and (:hasta is null or v.fechaRegistro <= :hasta)
            """)
    List<Venta> buscar(
            @Param("clienteId") Long clienteId,
            @Param("estado") EstadoVenta estado,
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            Sort sort);

    @Query("""
            select new pe.edu.upeu.PharmaBackend.dto.reporte.VentaPorCategoriaDTO(cat.id, cat.nombre, sum(d.cantidad), sum(d.subtotal))
            from DetalleVenta d join d.venta v join d.producto p join p.categoria cat
            where v.estado = pe.edu.upeu.PharmaBackend.enums.EstadoVenta.REGISTRADA
              and (:desde is null or v.fechaRegistro >= :desde)
              and (:hasta is null or v.fechaRegistro <= :hasta)
            group by cat.id, cat.nombre
            order by sum(d.subtotal) desc
            """)
    List<pe.edu.upeu.PharmaBackend.dto.reporte.VentaPorCategoriaDTO> reporteVentasPorCategoria(
            @Param("desde") LocalDateTime desde, 
            @Param("hasta") LocalDateTime hasta);

    @Query("""
            select new pe.edu.upeu.PharmaBackend.dto.reporte.ProductoMasVendidoDTO(p.id, p.nombre, cat.nombre, sum(d.cantidad), sum(d.subtotal))
            from DetalleVenta d join d.venta v join d.producto p join p.categoria cat
            where v.estado = pe.edu.upeu.PharmaBackend.enums.EstadoVenta.REGISTRADA
              and (:desde is null or v.fechaRegistro >= :desde)
              and (:hasta is null or v.fechaRegistro <= :hasta)
            group by p.id, p.nombre, cat.nombre
            order by sum(d.cantidad) desc
            """)
    List<pe.edu.upeu.PharmaBackend.dto.reporte.ProductoMasVendidoDTO> reporteProductosMasVendidos(
            @Param("desde") LocalDateTime desde, 
            @Param("hasta") LocalDateTime hasta);
}
