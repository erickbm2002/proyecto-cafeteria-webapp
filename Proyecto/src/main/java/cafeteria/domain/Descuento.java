package cafeteria.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "descuento")
public class Descuento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_descuento")
    private Integer idDescuento;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    @NotNull(message = "El producto es requerido")
    private Producto producto;

    @Column(precision = 5, scale = 2)
    @DecimalMin(value = "0.0", message = "El porcentaje debe ser mayor o igual a 0")
    @DecimalMax(value = "100.0", message = "El porcentaje no puede exceder 100")
    private BigDecimal porcentaje;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(nullable = false)
    private Boolean activo = true;

    // Método auxiliar para verificar si el descuento está vigente
    public boolean isVigente() {
        LocalDate hoy = LocalDate.now();
        return activo && 
               (fechaInicio == null || !hoy.isBefore(fechaInicio)) && 
               (fechaFin == null || !hoy.isAfter(fechaFin));
    }

    // Método auxiliar para calcular el precio con descuento
    public BigDecimal calcularPrecioConDescuento(BigDecimal precioOriginal) {
        if (precioOriginal == null || porcentaje == null || !isVigente()) {
            return precioOriginal;
        }
        BigDecimal descuentoDecimal = porcentaje.divide(new BigDecimal(100));
        BigDecimal montoDescuento = precioOriginal.multiply(descuentoDecimal);
        return precioOriginal.subtract(montoDescuento);
    }
}