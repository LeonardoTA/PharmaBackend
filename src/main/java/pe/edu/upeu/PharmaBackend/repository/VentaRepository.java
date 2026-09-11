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
}
