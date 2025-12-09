package cafeteria.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_orden", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"id_orden", "id_producto"}))
public class DetalleOrden implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    @NotNull(message = "La orden es requerida")
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    @NotNull(message = "El producto es requerido")
    private Producto producto;

    @Column(nullable = false)
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", message = "El precio unitario debe ser mayor o igual a 0")
    private BigDecimal precioUnitario;

    // Método auxiliar para calcular el subtotal
    public BigDecimal getSubtotal() {
        if (precioUnitario != null && cantidad != null) {
            return precioUnitario.multiply(new BigDecimal(cantidad));
        }
        return BigDecimal.ZERO;
    }
}