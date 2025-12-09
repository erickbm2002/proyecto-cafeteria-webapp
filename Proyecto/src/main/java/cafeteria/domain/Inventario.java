package cafeteria.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "inventario")
public class Inventario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Integer idInventario;

    @OneToOne
    @JoinColumn(name = "id_producto", nullable = false, unique = true)
    @NotNull(message = "El producto es requerido")
    private Producto producto;

    @Column(name = "cantidad_actual", nullable = false)
    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    private Integer cantidadActual = 0;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Método auxiliar para verificar disponibilidad
    public boolean isDisponible(int cantidadRequerida) {
        return cantidadActual >= cantidadRequerida;
    }

    // Método auxiliar para decrementar stock
    public void decrementarStock(int cantidad) {
        if (cantidad > cantidadActual) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + cantidadActual + ", Requerido: " + cantidad);
        }
        this.cantidadActual -= cantidad;
        this.fechaActualizacion = LocalDateTime.now();
    }

    // Método auxiliar para incrementar stock
    public void incrementarStock(int cantidad) {
        this.cantidadActual += cantidad;
        this.fechaActualizacion = LocalDateTime.now();
    }
}