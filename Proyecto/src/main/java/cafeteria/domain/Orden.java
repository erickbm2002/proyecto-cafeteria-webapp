package cafeteria.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@EntityListeners(AuditingEntityListener.class)

@Data
@Entity
@Table(name = "orden")
public class Orden implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Integer idOrden;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @NotNull(message = "El usuario es requerido")
    private Usuario usuario;

    @Column(name = "fecha_orden", nullable = false)
    @NotNull(message = "La fecha de orden es requerida")
    private LocalDate fechaOrden;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.Pendiente;

    @Column(precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "El total debe ser mayor o igual a 0")
    private BigDecimal total;

    @Column(name = "fecha_creacion", updatable = false)
    @CreatedDate
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    @LastModifiedDate
    private LocalDateTime fechaModificacion;
    
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<DetalleOrden> detalles;

    @OneToOne(mappedBy = "orden", cascade = CascadeType.ALL)
    private Factura factura;

    public enum Estado {
        Pendiente,
        Preparando,
        Entregada,
        Cancelada
    }
}