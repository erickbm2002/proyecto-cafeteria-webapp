
-- --- Sección de Inserción de Datos ---
-- Inserción de Categoría
INSERT INTO categoria (nombre) VALUES
(1,'Bebidas'),
(2,'Comidas'),
(3,'Postres');

-- Inserción de Producto
INSERT INTO producto (id_categoria, nombre, descripcion, precio, imagen_url, activo)
VALUES
-- BEBIDAS (1)
(1, 'capuchino', 'Bebida caliente', 1500, 'capuchino.jpg', TRUE),
(1, 'chocolate_caliente', 'Bebida caliente de chocolate', 1500, 'chocolate_caliente.jpg', TRUE),
(1, 'smoothie_banano', 'Smoothie natural de banano', 1800, 'smoothie_banano.jpg', TRUE),
(1, 'smoothie_frambuesa', 'Smoothie natural de frambuesa', 1800, 'smoothie_frambuesa.jpg', TRUE),
(1, 'smoothie_frutos_rojos', 'Smoothie natural de frutos rojos', 1800, 'smoothie_frutos_rojos.jpg', TRUE),
(1, 'smoothie_mango', 'Smoothie natural de mango', 1800, 'smoothie_mango.jpg', TRUE),
(1, 'smoothie_sandia', 'Smoothie natural de sandía', 1800, 'smoothie_sandia.jpg', TRUE),

-- COMIDAS (2)
(2, 'desayuno_tostadas', 'Tostadas con acompañamientos', 2500, 'desayuno_tostadas.jpg', TRUE),
(2, 'emparedado_jamon_ensalada', 'Emparedado con jamón y ensalada', 2800, 'emparedado_jamon_ensalada.jpg', TRUE),
(2, 'omelette_hongos', 'Omelette con hongos frescos', 2700, 'omelette_hongos.jpg', TRUE),
(2, 'omelette_queso', 'Omelette relleno de queso', 2600, 'omelette_queso.jpg', TRUE),
(2, 'tortilla_queso', 'Tortilla tradicional con queso', 2000, 'tortilla_queso.jpg', TRUE),

-- POSTRES (3)
(3, 'cheesecake_frutos_rojos', 'Cheesecake con salsa de frutos rojos', 3000, 'cheesecake_frutos_rojos.jpg', TRUE),
(3, 'crepa_chocolate', 'Crepa con chocolate derretido', 2500, 'crepa_chocolate.jpg', TRUE),
(3, 'crepa_nutella_fresa', 'Crepa con Nutella y fresas', 2800, 'crepa_nutella_fresa.jpg', TRUE),

(2, 'pancakes', 'Pancakes con miel o frutas', 2500, 'pancakes.jpg', TRUE);
INSERT INTO producto (id_categoria, nombre, descripcion, precio, imagen_url, activo)
VALUES
-- BEBIDAS (1)
(1, 'Capuchino',
 'Café intenso con espuma de leche sedosa, preparado con granos seleccionados para un aroma profundo y un sabor equilibrado que envuelve el paladar.',
 1500, 'capuchino.jpg', TRUE),

(1, 'Chocolate caliente',
 'Mezcla cremosa de cacao premium derretido lentamente, con notas dulces y tostadas que reconfortan desde el primer sorbo.',
 1500, 'chocolate_caliente.jpg', TRUE),

(1, 'Smoothie banano',
 'Banano fresco mezclado hasta lograr una textura suave y natural, con un dulzor equilibrado que energiza de forma ligera y refrescante.',
 1800, 'smoothie_banano.jpg', TRUE),

(1, 'Smoothie frambuesa',
 'Frambuesas maduras procesadas en frío para mantener su acidez vibrante y un color intenso que conquista a la vista y al gusto.',
 1800, 'smoothie_frambuesa.jpg', TRUE),

(1, 'Smoothie frutos rojos',
 'Mezcla equilibrada de mora, fresa y arándano que ofrece un sabor vibrante y antioxidante en cada sorbo.',
 1800, 'smoothie_frutos_rojos.jpg', TRUE),

(1, 'Smoothie mango',
 'Mango tropical madurado al sol que aporta un dulzor natural y una textura suave para un refresco excepcionalmente aromático.',
 1800, 'smoothie_mango.jpg', TRUE),

(1, 'Smoothie sandía',
 'Sandía fresca con notas ligeras y dulces, procesada para conservar su frescura natural en una bebida que revive al instante.',
 1800, 'smoothie_sandia.jpg', TRUE),

-- COMIDAS (2)
(2, 'Desayuno tostadas',
 'Pan artesanal tostado y servido con mantequilla suave, miel natural y frutas frescas para un comienzo equilibrado y lleno de sabor.',
 2500, 'desayuno_tostadas.jpg', TRUE),

(2, 'Emparedado jamón ensalada',
 'Jamón ahumado acompañado de vegetales crujientes, servidos en pan fresco con un aderezo ligero que realza cada ingrediente.',
 2800, 'emparedado_jamon_ensalada.jpg', TRUE),

(2, 'Omelette hongos',
 'Omelette esponjoso relleno de hongos salteados y aromáticos, preparado a fuego lento para mantener su suavidad y sabor profundo.',
 2700, 'omelette_hongos.jpg', TRUE),

(2, 'Omelette queso',
 'Huevos batidos con técnica suave y rellenos de queso fundido que aporta una cremosidad irresistible en cada bocado.',
 2600, 'omelette_queso.jpg', TRUE),

(2, 'Tortilla queso',
 'Tortilla tradicional recién hecha con una capa de queso derretido que se mezcla con notas suaves y ligeramente tostadas.',
 2000, 'tortilla_queso.jpg', TRUE),

(2, 'Pancakes',
 'Suaves y esponjosos, preparados al momento y servidos con miel natural y mantequilla que se derrite sobre cada capa.',
 2500, 'pancakes.jpg', TRUE),

-- POSTRES (3)
(3, 'Cheesecake frutos rojos',
 'Cheesecake cremoso sobre una base crujiente, coronado con una salsa fresca de frutos rojos que aporta equilibrio entre dulzor y acidez.',
 3000, 'cheesecake_frutos_rojos.jpg', TRUE),

(3, 'Crepa chocolate',
 'Crepa delgada y dorada rellena con chocolate derretido de alta pureza que envuelve el paladar con un sabor suave y profundo.',
 2500, 'crepa_chocolate.jpg', TRUE),

(3, 'Crepa nutella fresa',
 'Crepa fina rellena con Nutella cremosa y fresas frescas que aportan frescura y contraste perfecto en cada bocado.',
 2800, 'crepa_nutella_fresa.jpg', TRUE);

-- Inserción de Usuario
INSERT INTO usuario (nombre, correo, telefono, direccion, rol, activo, password)
VALUES
('Erick Barrientos', 'admin@cafeteria.com', '8888-8888', 'San José', 'Administrador', TRUE, 'admin123'),
('Steven Fonseca', 'admin1@cafeteria.com', '7777-7777', 'San José', 'Administrador', TRUE, 'admin123'),
('Diego Villegas', 'diego.cliente@gmail.com
', '6666-6666', 'Cartago', 'Cliente', TRUE, 'cliente123'),
('Stephania Rojas', 'stephannia.cliente@gmail.com
', '5555-5555', 'Heredia', 'Cliente', TRUE, 'cliente123')

-- Inserción de Inventario
INSERT INTO inventario (id_producto, cantidad_actual, fecha_actualizacion)
SELECT 
    p.id_producto,
    10,  -- Cantidad inicial de 100 unidades
    CURRENT_TIMESTAMP
FROM producto p
WHERE NOT EXISTS (
    SELECT 1 
    FROM inventario i 
    WHERE i.id_producto = p.id_producto
);



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