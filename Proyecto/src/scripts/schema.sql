-- --- Sección de Inserción de Datos ---
-- Inserción de Categoría
INSERT INTO categoria (nombre, descripcion) VALUES
('Café', 'Bebidas calientes a base de café'),
('Té', 'Infusiones naturales'),
('Jugos', 'Bebidas frías de frutas'),
('Pastelería', 'Postres y repostería'),
('Sandwiches', 'Comidas rápidas'),
('Snacks', 'Acompañamientos ligeros'),
('Lácteos', 'Bebidas con leche'),
('Especiales', 'Productos de temporada');

-- Inserción de Producto
INSERT INTO producto (id_categoria, nombre, descripcion, precio) VALUES
(1, 'Café Americano', 'Café negro tradicional', 1200),
(1, 'Café Espresso', 'Café concentrado', 1500),
(1, 'Café Latte', 'Café con leche espumosa', 1800),
(1, 'Café Mocha', 'Café con chocolate', 2000),
(1, 'Café Capuchino', 'Café con espuma de leche', 1900),
(2, 'Té Verde', 'Infusión de hojas verdes', 1300),
(2, 'Té Negro', 'Infusión intensa', 1300),
(2, 'Té de Manzanilla', 'Infusión relajante', 1200),
(3, 'Jugo de Naranja', 'Natural y sin azúcar', 1600),
(3, 'Jugo de Piña', 'Refrescante y tropical', 1600),
(3, 'Jugo Mixto', 'Combinación de frutas', 1700),
(4, 'Croissant', 'Pan hojaldrado francés', 1800),
(4, 'Queque de Vainilla', 'Bizcocho suave', 2000),
(4, 'Brownie', 'Chocolate intenso', 2200),
(4, 'Galleta de Avena', 'Con pasas y miel', 1500),
(5, 'Sandwich de Jamón', 'Pan, jamón y queso', 2500),
(5, 'Sandwich Vegetariano', 'Con vegetales frescos', 2400),
(5, 'Panini de Pollo', 'Con salsa pesto', 2800),
(6, 'Papas Fritas', 'Corte clásico', 1200),
(6, 'Nachos con Queso', 'Con jalapeños', 2000),
(6, 'Empanada de Queso', 'Frita y crujiente', 1800),
(7, 'Leche con Chocolate', 'Bebida fría', 1600),
(7, 'Batido de Fresa', 'Con leche y fruta natural', 2000),
(8, 'Café Navideño', 'Edición especial con especias', 2500);

-- Inserción de Usuario
INSERT INTO usuario (nombre, correo, telefono, direccion, rol) VALUES
('Ana López', 'ana@gmail.com', '8888-1111', 'San José', 'Cliente'),
('Luis Ramírez', 'luis@gmail.com', '8888-2222', 'Cartago', 'Cliente'),
('María Fernández', 'maria@gmail.com', '8888-3333', 'Heredia', 'Cliente'),
('Carlos Jiménez', 'carlos@gmail.com', '8888-4444', 'Alajuela', 'Cliente'),
('Sofía Vargas', 'sofia@gmail.com', '8888-5555', 'San José', 'Cliente'),
('Pedro Mora', 'pedro@gmail.com', '8888-6666', 'Cartago', 'Cliente'),
('Laura Rojas', 'laura@gmail.com', '8888-7777', 'Heredia', 'Cliente'),
('Jorge Salas', 'jorge@gmail.com', '8888-8888', 'Alajuela', 'Cliente'),
('Lucía Castro', 'lucia@gmail.com', '8888-9999', 'San José', 'Cliente'),
('Diego Navarro', 'diego@gmail.com', '8888-0000', 'Cartago', 'Cliente'),
('Admin Cafetería', 'admin@cafeteria.com', '8000-0001', 'Oficina Central', 'Administrador'),
('Barista 1', 'barista1@cafeteria.com', '8000-0002', 'Sucursal Este', 'Administrador'),
('Barista 2', 'barista2@cafeteria.com', '8000-0003', 'Sucursal Oeste', 'Administrador'),
('Gerente', 'gerente@cafeteria.com', '8000-0004', 'Oficina Central', 'Administrador'),
('Cajero', 'cajero@cafeteria.com', '8000-0005', 'Sucursal Norte', 'Administrador');

-- Inserción de Orden
INSERT INTO orden (id_usuario, fecha_orden, estado, total) VALUES
(1, '2025-10-01', 'Entregada', 4800),
(2, '2025-10-02', 'Entregada', 3200),
(3, '2025-10-03', 'Pendiente', 2500),
(4, '2025-10-04', 'Cancelada', 0),
(5, '2025-10-05', 'Entregada', 5600),
(6, '2025-10-06', 'Entregada', 4300),
(7, '2025-10-07', 'Pendiente', 3100),
(8, '2025-10-08', 'Entregada', 7200),
(9, '2025-10-09', 'Entregada', 3900),
(10, '2025-10-10', 'Entregada', 5100),
(1, '2025-10-11', 'Entregada', 2800),
(2, '2025-10-12', 'Pendiente', 3300),
(3, '2025-10-13', 'Entregada', 4700),
(4, '2025-10-14', 'Entregada', 6000),
(5, '2025-10-15', 'Entregada', 2500),
(6, '2025-10-16', 'Pendiente', 1900),
(7, '2025-10-17', 'Entregada', 4200),
(8, '2025-10-18', 'Entregada', 3700),
(9, '2025-10-19', 'Entregada', 2900),
(10, '2025-10-20', 'Pendiente', 3100);


-- Inserción de Detalle de orden
INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 2, 1200),
(1, 12, 1, 1800),
(2, 3, 1, 1800),
(2, 14, 1, 2200),
(3, 5, 1, 1900),
(3, 6, 1, 1300),
(5, 2, 1, 1500),
(5, 4, 1, 2000),
(5, 13, 1, 2000),
(6, 7, 1, 1300),
(6, 8, 1, 1200),
(6, 9, 1, 1600),
(7, 10, 2, 1600),
(8, 11, 1, 1700),
(8, 12, 1, 1800),
(8, 13, 1, 2000),
(9, 14, 2, 2200),
(10, 15, 1, 2500),
(10, 16, 1, 2400),
(11, 17, 1, 2800),
(12, 18, 1, 1200),
(13, 19, 1, 2000),
(14, 20, 1, 1800),
(15, 21, 1, 1600),
(16, 22, 1, 2000),
(17, 23, 1, 2500),
(18, 24, 1, 1600),
(19, 1, 1, 1200),
(20, 2, 1, 1500),
(20, 3, 1, 1800);

-- Inserción de Promoción
INSERT INTO promocion (titulo, descripcion, fecha_inicio, fecha_fin) VALUES
('Promo Café 2x1', 'Compra un café y recibe otro gratis', '2025-11-01', '2025-11-15'),
('Descuento Té Verde', '20% de descuento en té verde', '2025-11-05', '2025-11-20'),
('Combo Desayuno', 'Café + Croissant a precio especial', '2025-11-10', '2025-11-30'),
('Happy Hour Jugos', 'Jugos a mitad de precio de 2pm a 4pm', '2025-11-01', '2025-11-30'),
('Especial Navidad', 'Café navideño con galleta gratis', '2025-12-01', '2025-12-25');

-- Inserción de Descuento
INSERT INTO descuento (id_producto, porcentaje, fecha_inicio, fecha_fin) VALUES
(1, 10.00, '2025-11-01', '2025-11-15'),
(6, 20.00, '2025-11-05', '2025-11-20'),
(12, 15.00, '2025-11-10', '2025-11-30'),
(9, 50.00, '2025-11-01', '2025-11-30'),
(24, 25.00, '2025-12-01', '2025-12-25'),
(14, 10.00, '2025-11-01', '2025-11-10'),
(17, 5.00, '2025-11-15', '2025-11-30'),
(20, 30.00, '2025-11-20', '2025-11-30'),
(22, 20.00, '2025-11-25', '2025-12-05'),
(23, 15.00, '2025-12-01', '2025-12-15');

-- Inserción de Inventario
INSERT INTO inventario (id_producto, cantidad_actual) VALUES
(1, 50), (2, 40), (3, 60), (4, 30), (5, 45),
(6, 70), (7, 65), (8, 80), (9, 55), (10, 50),
(11, 40), (12, 35), (13, 25), (14, 20), (15, 30),
(16, 30), (17, 25), (18, 20), (19, 15), (20, 10),
(21, 50), (22, 45), (23, 40), (24, 35);



-- Inserción de Factura
INSERT INTO factura (id_orden, fecha_emision, monto_total, estado) VALUES
(1, '2025-10-01', 4800, 'Pagada'),
(2, '2025-10-02', 3200, 'Pagada'),
(5, '2025-10-05', 5600, 'Pagada'),
(6, '2025-10-06', 4300, 'Pagada'),
(8, '2025-10-08', 7200, 'Pagada'),
(9, '2025-10-09', 3900, 'Pagada'),
(10, '2025-10-10', 5100, 'Pagada'),
(11, '2025-10-11', 2800, 'Pagada'),
(13, '2025-10-13', 4700, 'Pagada'),
(14, '2025-10-14', 6000, 'Pagada'),
(15, '2025-10-15', 2500, 'Pagada'),
(17, '2025-10-17', 4200, 'Pagada'),
(18, '2025-10-18', 3700, 'Pagada'),
(19, '2025-10-19', 2900, 'Pagada'),
(3, '2025-10-03', 2500, 'Pendiente'),
(7, '2025-10-07', 3100, 'Pendiente'),
(12, '2025-10-12', 3300, 'Pendiente'),
(16, '2025-10-16', 1900, 'Pendiente'),
(20, '2025-10-20', 3100, 'Pendiente');

-- Inserción de Transacción
INSERT INTO transaccion (id_factura, monto, metodo_pago, estado) VALUES
(22, 3200, 'Efectivo', 'Exitosa'),
(35, 2500, 'PayPal', 'Pendiente'),
(23, 5600, 'Tarjeta', 'Exitosa'),
(24, 4300, 'Efectivo', 'Exitosa'),
(36, 3100, 'Tarjeta', 'Pendiente'),
(25, 7200, 'Tarjeta', 'Exitosa'),
(26, 3900, 'PayPal', 'Exitosa'),
(27, 5100, 'Tarjeta', 'Exitosa'),
(28, 2800, 'Efectivo', 'Exitosa'),
(37, 3300, 'Tarjeta', 'Pendiente'),
(29, 4700, 'Tarjeta', 'Exitosa'),
(30, 6000, 'PayPal', 'Exitosa'),
(31, 2500, 'Tarjeta', 'Exitosa'),
(38, 1900, 'Efectivo', 'Pendiente'),
(32, 4200, 'Efectivo', 'Exitosa'),
(33, 3700, 'Tarjeta', 'Exitosa'),
(34, 2900, 'PayPal', 'Exitosa'),
(39, 3100, 'PayPal', 'Pendiente');

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