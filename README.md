# proyecto-cafeteria-webapp
Cafetería Web App - Proyecto Final Universidad Fidelitas  Una aplicación web moderna para una cafetería, que incluye información general, un catálogo de productos y un carrito de compras integrado. Desarrollado como proyecto final para la universidad.  Tecnologías: HTML, CSS, JavaScript, JAVA,SPRINBOOT, MySQL.

# Base de datos
## Proposito
La base de datos fue diseñada para respaldar el funcionamiento de una cafetería digital, permitiendo gestionar productos, usuarios, órdenes, promociones, inventario, facturación y transacciones. Está optimizada para integrarse con una aplicación web y garantizar integridad, trazabilidad y escalabilidad.

## Estructura general
| Tabla           | Descripción                                      |
|-----------------|--------------------------------------------------|
| `categoria`     | Clasificación de productos                       |
| `producto`      | Información detallada de cada producto           |
| `usuario`       | Clientes y administradores                       |
| `orden`         | Registro de pedidos realizados                   |
| `detalle_orden` | Productos incluidos en cada orden                |
| `promocion`     | Campañas promocionales                           |
| `descuento`     | Descuentos aplicables por producto               |
| `inventario`    | Control de stock por producto                    |
| `factura`       | Documento de cobro asociado a una orden          |
| `transaccion`   | Registro de pagos realizados                     |


## Usuarios y permisos
CREATE USER 'cafeteria_admin'@'%' IDENTIFIED BY 'AdminCaf3.';

CREATE USER 'cafeteria_reportes'@'%' IDENTIFIED BY 'ReportCaf3.';

GRANT SELECT, INSERT, UPDATE, DELETE ON cafeteria.* TO 'cafeteria_admin'@'%';

GRANT SELECT ON cafeteria.* TO 'cafeteria_reportes'@'%';

## Relaciones y claves foráneas
Cada producto pertenece a una categoría

Cada orden pertenece a un usuario

Cada detalle de orden vincula un producto con una orden

Las facturas se asocian a órdenes, y las transacciones a facturas

## Script de creación de tablas utilizadas
```sql
-- Tabla de categorías
CREATE TABLE categoria (
  id_categoria INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE,
  activo BOOLEAN DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB;

-- Tabla de productos
CREATE TABLE producto (
  id_producto INT AUTO_INCREMENT PRIMARY KEY,
  id_categoria INT NOT NULL,
  nombre VARCHAR(50) NOT NULL UNIQUE,
  descripcion TEXT,
  precio DECIMAL(10,2) CHECK (precio >= 0),
  imagen_url VARCHAR(1024),
  activo BOOLEAN DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
) ENGINE = InnoDB;

-- Tabla de usuarios
CREATE TABLE usuario (
  id_usuario INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  correo VARCHAR(100) UNIQUE,
  telefono VARCHAR(25),
  direccion TEXT,
  rol ENUM('Cliente', 'Administrador') DEFAULT 'Cliente',
  activo BOOLEAN DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB;

-- Tabla de órdenes
CREATE TABLE orden (
  id_orden INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  fecha_orden DATE NOT NULL,
  estado ENUM('Pendiente', 'Preparando', 'Entregada', 'Cancelada') DEFAULT 'Pendiente',
  total DECIMAL(10,2) CHECK (total >= 0),
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE = InnoDB;

-- Tabla detalle de orden (relación orden-producto)
CREATE TABLE detalle_orden (
  id_detalle INT AUTO_INCREMENT PRIMARY KEY,
  id_orden INT NOT NULL,
  id_producto INT NOT NULL,
  cantidad INT UNSIGNED CHECK (cantidad > 0),
  precio_unitario DECIMAL(10,2) CHECK (precio_unitario >= 0),
  FOREIGN KEY (id_orden) REFERENCES orden(id_orden),
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
  UNIQUE (id_orden, id_producto)
) ENGINE = InnoDB;

-- Tabla de promociones
CREATE TABLE promocion (
  id_promocion INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(100) NOT NULL,
  descripcion TEXT,
  fecha_inicio DATE,
  fecha_fin DATE,
  activo BOOLEAN DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB;

-- Tabla de descuentos por producto
CREATE TABLE descuento (
  id_descuento INT AUTO_INCREMENT PRIMARY KEY,
  id_producto INT NOT NULL,
  porcentaje DECIMAL(5,2) CHECK (porcentaje >= 0 AND porcentaje <= 100),
  fecha_inicio DATE,
  fecha_fin DATE,
  activo BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
) ENGINE = InnoDB;

-- Tabla de inventario
CREATE TABLE inventario (
  id_inventario INT AUTO_INCREMENT PRIMARY KEY,
  id_producto INT NOT NULL,
  cantidad_actual INT UNSIGNED DEFAULT 0,
  fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
) ENGINE = InnoDB;

-- Tabla de facturas
CREATE TABLE factura (
  id_factura INT AUTO_INCREMENT PRIMARY KEY,
  id_orden INT NOT NULL,
  fecha_emision DATE NOT NULL,
  monto_total DECIMAL(10,2) CHECK (monto_total > 0),
  estado ENUM('Pagada', 'Pendiente', 'Anulada') DEFAULT 'Pendiente',
  FOREIGN KEY (id_orden) REFERENCES orden(id_orden)
) ENGINE = InnoDB;

-- Tabla de transacciones
CREATE TABLE transaccion (
  id_transaccion INT AUTO_INCREMENT PRIMARY KEY,
  id_factura INT NOT NULL,
  monto DECIMAL(10,2) CHECK (monto > 0),
  fecha_transaccion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  metodo_pago ENUM('Tarjeta', 'Efectivo', 'PayPal') NOT NULL,
  estado ENUM('Exitosa', 'Fallida', 'Pendiente') DEFAULT 'Pendiente',
  FOREIGN KEY (id_factura) REFERENCES factura(id_factura)
) ENGINE = InnoDB;
