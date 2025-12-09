package cafeteria.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "factura")
public class Factura implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Integer idFactura;

    @OneToOne
    @JoinColumn(name = "id_orden", nullable = false, unique = true)
    @NotNull(message = "La orden es requerida")
    private Orden orden;

    @Column(name = "fecha_emision", nullable = false)
    @NotNull(message = "La fecha de emisión es requerida")
    private LocalDate fechaEmision;

    @Column(name = "monto_total", precision = 10, scale = 2)
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a 0")
    private BigDecimal montoTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.Pendiente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<Transaccion> transacciones;

    public enum Estado {
        Pagada,
        Pendiente,
        Anulada
    }

    // Método auxiliar para verificar si está pagada
    public boolean isPagada() {
        return estado == Estado.Pagada;
    }

    // Método auxiliar para calcular el monto pagado
    public BigDecimal getMontoTotalPagado() {
        if (transacciones == null || transacciones.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return transacciones.stream()
            .filter(t -> t.getEstado() == Transaccion.Estado.Exitosa)
            .map(Transaccion::getMonto)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Método auxiliar para verificar si el pago está completo
    public boolean isPagoCompleto() {
        return montoTotal != null && 
               getMontoTotalPagado().compareTo(montoTotal) >= 0;
    }
}