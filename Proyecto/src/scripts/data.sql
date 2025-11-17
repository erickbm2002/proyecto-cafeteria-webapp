
-- --- Sección de Inserción de Datos ---
-- Inserción de Categoría
INSERT INTO categoria (nombre) VALUES
(1,'Bebidas'),
(2,'Comidas'),
(3,'Postres');

-- Inserción de Producto
INSERT INTO producto (id_producto,id_categoria, nombre, descripcion, precio, imagen_url) VALUES
(1,2,'Burrito de Pollo', ' Descubre nuestro burrito estrella. Pollo tierno y lleno de sabor, envuelto en una suave tortilla de harina con cremosos frijoles, arroz esponjoso, queso fundido y salsa casera.', 5, 'burrito_pollo.jpg'),
(2,1,'Café Frío', 'café de origen premium infusionado en frío durante horas para una suavidad inigualable. Servido sobre hielo picado con un toque de leche cremosa y un jarabe de vainilla casero.', 3, 'cafe_frio.jpg');

-- Inserción de Usuario
INSERT INTO usuario (nombre, correo, telefono, direccion, rol) VALUES

-- Inserción de Orden
INSERT INTO orden (id_usuario, fecha_orden, estado, total) VALUES


-- Inserción de Detalle de orden
INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario) VALUES


-- Inserción de Promoción
INSERT INTO promocion (titulo, descripcion, fecha_inicio, fecha_fin) VALUES


-- Inserción de Descuento
INSERT INTO descuento (id_producto, porcentaje, fecha_inicio, fecha_fin) VALUES


-- Inserción de Inventario
INSERT INTO inventario (id_producto, cantidad_actual) VALUES


-- Inserción de Factura
INSERT INTO factura (id_orden, fecha_emision, monto_total, estado) VALUES


-- Inserción de Transacción
INSERT INTO transaccion (id_factura, monto, metodo_pago, estado) VALUES



-- Creación de vista para consultar el historial completo de órdenes con sus facturas y transacciones
CREATE VIEW vista_flujo_orden_completo AS
SELECT
  o.id_orden,
  u.nombre AS cliente,
  o.fecha_orden,
  o.estado AS estado_orden,
  p.nombre AS producto,
  do.cantidad,
  do.precio_unitario,
  f.id_factura,
  f.fecha_emision,
  f.monto_total,
  f.estado AS estado_factura,
  t.id_transaccion,
  t.metodo_pago,
  t.estado AS estado_transaccion
FROM orden o
JOIN usuario u ON o.id_usuario = u.id_usuario
JOIN detalle_orden do ON o.id_orden = do.id_orden
JOIN producto p ON do.id_producto = p.id_producto
LEFT JOIN factura f ON o.id_orden = f.id_orden
LEFT JOIN transaccion t ON f.id_factura = t.id_factura
ORDER BY o.id_orden, f.id_factura, t.id_transaccion;

-- Consulta de Prueba
SELECT * FROM vista_flujo_orden_completo WHERE id_orden = 1;