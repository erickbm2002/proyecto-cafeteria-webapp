-- Creación del esquema
CREATE DATABASE cafeteria
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;
  
  -- Creación de usuarios con contraseñas seguras
CREATE USER 'cafeteria_admin'@'%' IDENTIFIED BY 'AdminCaf3.';
CREATE USER 'cafeteria_reportes'@'%' IDENTIFIED BY 'ReportCaf3.';

-- Asignación de permisos
-- Se otorgan permisos específicos en lugar de todos los permisos a todas las tablas futuras
GRANT SELECT, INSERT, UPDATE, DELETE ON cafeteria.* TO 'cafeteria_admin'@'%';
GRANT SELECT ON cafeteria.* TO 'cafeteria_reportes'@'%';
FLUSH PRIVILEGES;

USE cafeteria;

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