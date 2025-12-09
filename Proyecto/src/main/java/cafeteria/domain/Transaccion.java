package cafeteria.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "transaccion")
public class Transaccion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @ManyToOne
    @JoinColumn(name = "id_factura", nullable = false)
    @NotNull(message = "La factura es requerida")
    private Factura factura;

    @Column(precision = 10, scale = 2)
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @Column(name = "fecha_transaccion")
    private LocalDateTime fechaTransaccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    @NotNull(message = "El método de pago es requerido")
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.Pendiente;

    public enum MetodoPago {
        Tarjeta,
        Efectivo,
        PayPal
    }

    public enum Estado {
        Exitosa,
        Fallida,
        Pendiente
    }

    // Método auxiliar para verificar si fue exitosa
    public boolean isExitosa() {
        return estado == Estado.Exitosa;
    }
}