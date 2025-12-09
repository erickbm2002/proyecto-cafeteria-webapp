package cafeteria.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Data;

@EntityListeners(AuditingEntityListener.class)

@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(unique = true, nullable = false, length = 50)
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal precio;

    @Column(name = "imagen_url", length = 1024)
    private String nombreImagen;

    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    @CreatedDate
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    @LastModifiedDate
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "producto")
    private List<DetalleOrden> detallesOrden;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Descuento> descuentos;

    @OneToOne(mappedBy = "producto", cascade = CascadeType.ALL)
    private Inventario inventario;

    // Método auxiliar para obtener el descuento vigente
    public Descuento getDescuentoVigente() {
        if (descuentos == null || descuentos.isEmpty()) {
            return null;
        }
        return descuentos.stream()
            .filter(Descuento::isVigente)
            .findFirst()
            .orElse(null);
    }

    // Método auxiliar para obtener el precio con descuento
    public BigDecimal getPrecioConDescuento() {
        Descuento descuento = getDescuentoVigente();
        if (descuento != null) {
            return descuento.calcularPrecioConDescuento(precio);
        }
        return precio;
    }

    // Método auxiliar para verificar disponibilidad en inventario
    public boolean isDisponible(int cantidadRequerida) {
        return inventario != null && inventario.isDisponible(cantidadRequerida);
    }
}