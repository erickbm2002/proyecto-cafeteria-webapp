
package cafeteria.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ItemCarrito implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idProducto;
    private String nombre;
    private BigDecimal precio;
    private BigDecimal precioConDescuento;
    private Integer cantidad;
    private String nombreImagen;
    private String nombreCategoria;
    private boolean tieneDescuento;

    private Producto producto;

    public ItemCarrito() {
    }

    public ItemCarrito(Producto producto, Integer cantidad) {
        this.producto = producto; 
        this.idProducto = producto.getIdProducto();
        this.nombre = producto.getNombre();
        this.precio = producto.getPrecio();
        this.cantidad = cantidad;
        this.nombreImagen = producto.getNombreImagen();
        this.nombreCategoria = producto.getCategoria() != null ? producto.getCategoria().getNombre() : "";

        BigDecimal precioConDesc = producto.getPrecioConDescuento();
        if (precioConDesc != null && precioConDesc.compareTo(producto.getPrecio()) < 0) {
            this.precioConDescuento = precioConDesc;
            this.tieneDescuento = true;
        } else {
            this.precioConDescuento = producto.getPrecio();
            this.tieneDescuento = false;
        }
    }

    // Método para calcular el subtotal del item
    public BigDecimal getSubtotal() {
        BigDecimal precioFinal = tieneDescuento ? precioConDescuento : precio;
        return precioFinal.multiply(new BigDecimal(cantidad));
    }

    // Método para obtener el ahorro por descuento
    public BigDecimal getAhorro() {
        if (!tieneDescuento) {
            return BigDecimal.ZERO;
        }
        BigDecimal diferencia = precio.subtract(precioConDescuento);
        return diferencia.multiply(new BigDecimal(cantidad));
    }

    // Método para obtener el porcentaje de descuento
    public Integer getPorcentajeDescuento() {
        if (!tieneDescuento) {
            return 0;
        }
        BigDecimal diferencia = precio.subtract(precioConDescuento);
        BigDecimal porcentaje = diferencia.divide(precio, 2, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(100));
        return porcentaje.intValue();
    }
}