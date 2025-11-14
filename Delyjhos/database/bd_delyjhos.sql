CREATE DATABASE bd_delyjhos;
USE bd_delyjhos;

CREATE TABLE ROL (
  id_rol INT NOT NULL AUTO_INCREMENT,
  nombre_rol VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_rol)
);

CREATE TABLE USUARIO (
  id_usuario INT NOT NULL AUTO_INCREMENT,
  nombres VARCHAR(100) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  usuario VARCHAR(50) NOT NULL UNIQUE,
  contrasena VARCHAR(255) NOT NULL, -- Guardar contraseñas hasheadas
  estado VARCHAR(20) NOT NULL DEFAULT 'Activo', -- Activo, Inactivo
  id_rol INT NOT NULL,
  PRIMARY KEY (id_usuario),
  FOREIGN KEY (id_rol) REFERENCES ROL (id_rol)
);

CREATE TABLE PROVEEDOR (
  id_proveedor INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255) NOT NULL,
  ruc VARCHAR(11) NULL,
  telefono VARCHAR(15) NULL,
  direccion VARCHAR(255) NULL,
  correo VARCHAR(100) NULL,
  estado VARCHAR(20) NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (id_proveedor)
);

CREATE TABLE INSUMO (
  id_insumo INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT NULL,
  unidad_medida VARCHAR(20) NOT NULL, -- Ej. "Kg", "Litros", "Unidades"
  stock_actual DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  stock_minimo DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  fecha_caducidad DATE NULL,
  estado VARCHAR(20) NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (id_insumo)
);

CREATE TABLE PRODUCTO (
  id_producto INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT NULL,
  precio DECIMAL(10,2) NOT NULL,
  categoria VARCHAR(100) NULL,
  stock_actual INT NOT NULL DEFAULT 0,
  estado VARCHAR(20) NOT NULL DEFAULT 'Activo', -- Activo, Inactivo
  PRIMARY KEY (id_producto)
);

-- Define los insumos para cada producto
CREATE TABLE RECETA (
  id_receta INT NOT NULL AUTO_INCREMENT,
  id_producto INT NOT NULL,
  id_insumo INT NOT NULL,
  cantidad_necesaria DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_receta),
  FOREIGN KEY (id_producto) REFERENCES PRODUCTO (id_producto),
  FOREIGN KEY (id_insumo) REFERENCES INSUMO (id_insumo)
);

CREATE TABLE PEDIDO (
  id_pedido INT NOT NULL AUTO_INCREMENT,
  fecha_pedido DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado_pedido VARCHAR(20) NOT NULL DEFAULT 'Pendiente', -- Pendiente, Recibido, Cancelado
  total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  id_proveedor INT NOT NULL,
  PRIMARY KEY (id_pedido),
  FOREIGN KEY (id_proveedor) REFERENCES PROVEEDOR (id_proveedor)
);

CREATE TABLE PEDIDO_DETALLE (
  id_pedido_detalle INT NOT NULL AUTO_INCREMENT,
  id_pedido INT NOT NULL,
  id_insumo INT NOT NULL,
  cantidad INT NOT NULL,
  precio_unitario DECIMAL(10,2) NOT NULL,
  subtotal DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_pedido_detalle),
  FOREIGN KEY (id_pedido) REFERENCES PEDIDO (id_pedido),
  FOREIGN KEY (id_insumo) REFERENCES INSUMO (id_insumo)
);

-- Registra todas las entradas y salidas de stock
CREATE TABLE IF NOT EXISTS MOVIMIENTO_INVENTARIO (
  id_movimiento INT NOT NULL AUTO_INCREMENT,
  fecha_movimiento DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  tipo_movimiento VARCHAR(50) NOT NULL, -- 'Entrada por Compra', 'Salida por Producción', 'Salida por Venta', 'Merma'
  cantidad DECIMAL(10,2) NOT NULL,
  id_usuario INT NOT NULL,
  id_insumo INT NULL, -- Es nulo si el movimiento es de un producto
  id_producto INT NULL, -- Es nulo si el movimiento es de un insumo
  PRIMARY KEY (id_movimiento),
  FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario),
  FOREIGN KEY (id_insumo) REFERENCES INSUMO (id_insumo),
  FOREIGN KEY (id_producto) REFERENCES PRODUCTO (id_producto)
);

-- Inserts
INSERT INTO ROL (id_rol, nombre_rol) 
VALUES (1, 'Administrador')
ON DUPLICATE KEY UPDATE nombre_rol='Administrador';

INSERT INTO USUARIO (nombres, apellidos, usuario, contrasena, estado, id_rol) 
VALUES ('Admin', 'Principal', 'admin', '123', 'Activo', 1);

-- Selects
SELECT * FROM ROL;
SELECT * FROM USUARIO;
SELECT * FROM PROVEEDOR;
SELECT * FROM INSUMO;
SELECT * FROM PRODUCTO;
SELECT * FROM RECETA;
SELECT * FROM PEDIDO;
SELECT * FROM PEDIDO_DETALLE;
SELECT * FROM MOVIMIENTO_INVENTARIO;




