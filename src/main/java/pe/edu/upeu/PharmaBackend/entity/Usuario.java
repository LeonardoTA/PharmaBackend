package pe.edu.upeu.PharmaBackend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
/*
 * Cuenta de acceso de un empleado.
 *
 *  - empleado: relacion 1 a 1. La FK empleado_id esta en esta tabla y es
 *    UNIQUE, asi un empleado no puede tener dos usuarios.
 *  - roles: relacion muchos a muchos a traves de la tabla usuario_roles.
 *    Se carga EAGER porque en cada peticion hay que conocer los roles
 *    para calcular los permisos, y son pocos.
 *
 * La columna password guarda SIEMPRE el hash BCrypt, nunca el texto plano.
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
@Column(nullable = false)
    private Boolean estado;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empleado_id", nullable = false, unique = true)
    private Empleado empleado;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        if (estado == null) {
            estado = true;
        }
    }
    @PreUpdate
    public void preUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }
}
