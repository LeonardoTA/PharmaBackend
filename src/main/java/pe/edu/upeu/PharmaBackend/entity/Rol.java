package pe.edu.upeu.PharmaBackend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*
 * Rol del sistema (ADMIN, VENDEDOR, ALMACENERO).
 * Los permisos de cada rol estan en PermisosPorRol.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String nombre;
    @Column(length = 150)
    private String descripcion;
}
