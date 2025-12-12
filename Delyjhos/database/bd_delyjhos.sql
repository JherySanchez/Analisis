CREATE DATABASE bd_delyjhos;
USE bd_delyjhos;


-- 1. CREACIÓN DE TABLAS


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
  contrasena VARCHAR(255) NOT NULL,
  estado VARCHAR(20) NOT NULL DEFAULT 'Activo',
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
  unidad_medida VARCHAR(20) NOT NULL,
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
  estado VARCHAR(20) NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (id_producto)
);

-- Tabla intermedia: Define que insumos lleva cada producto
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
  estado_pedido VARCHAR(20) NOT NULL DEFAULT 'Pendiente',
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

-- Historial de movimientos
CREATE TABLE IF NOT EXISTS MOVIMIENTO_INVENTARIO (
  id_movimiento INT NOT NULL AUTO_INCREMENT,
  fecha_movimiento DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  tipo_movimiento VARCHAR(50) NOT NULL, 
  cantidad DECIMAL(10,2) NOT NULL,
  id_usuario INT NOT NULL,
  id_insumo INT NULL,
  id_producto INT NULL,
  PRIMARY KEY (id_movimiento),
  FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario),
  FOREIGN KEY (id_insumo) REFERENCES INSUMO (id_insumo),
  FOREIGN KEY (id_producto) REFERENCES PRODUCTO (id_producto)
);


-- INSERCION DE DATOS BASE

-- ROL y USUARIO
INSERT INTO ROL (id_rol, nombre_rol) VALUES (1, 'Administrador');
INSERT INTO USUARIO (nombres, apellidos, usuario, contrasena, estado, id_rol) 
VALUES ('Admin', 'Principal', 'admin', '123', 'Activo', 1);

-- PROVEEDORES
INSERT INTO PROVEEDOR (nombre, ruc, telefono, direccion, correo, estado) VALUES 
('Comercial Harinera del Norte S.A.C.', '20100123456', '01-456-7890', 'Av. Industrial 1050, Lima', 'ventas@harinera.com', 'Activo'),
('Avícola Santa Clara', '20200987654', '987-654-321', 'Carr. Central Km 15, Ate', 'pedidos@avicolasanta.com', 'Activo'),
('Lácteos del Valle', '20300112233', '01-222-3344', 'Jr. Los Establos 400, Lurín', 'contacto@lacteosvalle.pe', 'Activo'),
('Chocolates y Cacao Perú', '20400556677', '999-888-777', 'Calle Las Camelias 120, San Isidro', 'info@cacaoperu.com', 'Activo'),
('Esencias y Sabores S.R.L.', '20500998877', '01-333-2211', 'Av. Argentina 500, Callao', 'ventas@esencias.com', 'Activo'),
('Frutas Selectas El Huerto', '20600443322', '955-112-233', 'Mercado Mayorista Puesto 45', 'pedidos@frutaselhuerto.com', 'Activo'),
('Empaques Ecológicos Lima', '20700123987', '01-444-5566', 'Av. El Sol 800, Barranco', 'cajas@empaqueseco.com', 'Activo'),
('Importaciones DecorCake', '20800765432', '912-345-678', 'Jr. Andahuaylas 200, Cercado', 'tienda@decorcake.com', 'Activo'),
('Distribuidora de Gas "Llama Azul"', '20900111222', '01-555-1234', 'Av. Universitaria 3000, SMP', 'pedidos@llamaazul.com', 'Activo'),
('Comercializadora Todo Plástico', '20101234567', '998-765-432', 'Av. Próceres 100, SJL', 'ventas@todoplastico.com', 'Activo'),
('Makro Supermayorista', '20505050501', '01-600-0000', 'Av. Panamericana Norte, Independencia', 'atencion@makro.com.pe', 'Activo'),
('Mercado Central de Abastos', '10404040402', '944-555-666', 'Jr. Huallaga 300, Lima', 'contacto@mercadocentral.pe', 'Activo');

-- INSUMOS 
INSERT INTO INSUMO (nombre, descripcion, unidad_medida, stock_actual, stock_minimo, fecha_caducidad, estado) VALUES 
('Levadura Fresca', 'Levadura en bloque para pan', 'Unidades', 2.00, 5.00, '2025-09-15', 'Activo'), 
('Queso Crema', 'Queso tipo Philadelphia', 'Kg', 5.00, 2.00, '2025-09-28', 'Activo'),
('Yogurt Natural', 'Yogurt sin dulce para postres', 'Litros', 4.00, 2.00, '2025-10-05', 'Activo'),
('Masa Hojaldre', 'Masa lista congelada', 'Kg', 10.00, 3.00, '2025-10-20', 'Activo'),
('Crema de Leche', 'Crema de batir UHT', 'Litros', 12.00, 5.00, '2025-12-15', 'Activo'),
('Mantequilla sin Sal', 'Barra de mantequilla repostera', 'Kg', 8.00, 3.00, '2025-12-20', 'Activo'),
('Huevos Pardos', 'Jaba de huevos medianos', 'Unidades', 120.00, 30.00, '2025-12-25', 'Activo'),
('Harina Pastelera', 'Harina sin preparar especial', 'Kg', 50.00, 10.00, '2026-06-30', 'Activo'),
('Azúcar Blanca', 'Azúcar refinada', 'Kg', 45.00, 10.00, '2026-08-15', 'Activo'),
('Azúcar Impalpable', 'Azúcar finita (Glass)', 'Kg', 10.00, 2.00, '2026-05-20', 'Activo'),
('Leche Evaporada', 'Lata de leche concentrada', 'Unidades', 48.00, 12.00, '2026-03-10', 'Activo'),
('Chocolate Bitter', 'Cobertura 55% Cacao', 'Kg', 15.00, 5.00, '2026-09-01', 'Activo'),
('Chispas de Chocolate', 'Gotas horneables', 'Kg', 8.00, 2.00, '2026-11-15', 'Activo'),
('Esencia de Vainilla', 'Extracto oscuro', 'Litros', 5.00, 1.00, '2027-01-01', 'Activo'),
('Polvo de Hornear', 'Levadura química', 'Kg', 3.00, 1.00, '2026-12-12', 'Activo'),
('Manjarblanco', 'Dulce de leche repostero', 'Kg', 20.00, 5.00, '2026-04-30', 'Activo'),
('Fudge', 'Fudge de chocolate listo', 'Kg', 10.00, 4.00, '2026-05-15', 'Activo'),
('Mermelada de Fresa', 'Mermelada pulpa de fruta', 'Kg', 6.00, 2.00, '2026-07-20', 'Activo'),
('Aceite Vegetal', 'Aceite neutro para kekes', 'Litros', 20.00, 5.00, '2026-10-10', 'Activo'),
('Coco Rallado', 'Coco deshidratado fino', 'Kg', 5.00, 1.00, '2026-08-05', 'Activo'),
('Cacao en Polvo', 'Cacao 100% para repostería', 'Kg', 5.00, 1.00, '2025-12-01', 'Activo'),
('Limón', 'Limón fresco para jugos y ralladura', 'Kg', 10.00, 2.00, '2025-05-15', 'Activo'),
('Leche Condensada', 'Lata de leche dulce', 'Unidades', 24.00, 5.00, '2026-06-30', 'Activo'),
('Galletas de Vainilla', 'Paquetes para base de postres', 'Unidades', 30.00, 5.00, '2026-03-20', 'Activo'),
('Colapez', 'Gelatina sin sabor en polvo', 'Kg', 2.00, 0.50, '2027-01-01', 'Activo'),
('Maicena', 'Fecula de maiz para alfajores', 'Kg', 10.00, 2.00, '2026-10-10', 'Activo'),
('Carne Molida', 'Carne especial para empanadas', 'Kg', 5.00, 1.00, '2025-05-10', 'Activo'),
('Cebolla Roja', 'Cebolla para aderezo', 'Kg', 8.00, 2.00, '2025-05-20', 'Activo');

-- PRODUCTOS
INSERT INTO PRODUCTO (nombre, descripcion, precio, stock_actual, estado) VALUES 
('Torta de Chocolate', 'Bizcocho húmedo con fudge', 45.00, 0, 'Activo'),
('Pie de Limón', 'Base de galleta con merengue', 35.00, 0, 'Activo'),
('Cheesecake de Fresa', 'Queso crema y jalea de fresa', 50.00, 0, 'Activo'),
('Alfajores (Caja x 12)', 'Rellenos de manjarblanco', 12.00, 0, 'Activo'),
('Empanada de Carne', 'Masa casera horneada', 5.00, 0, 'Activo');

-- RECETAS
--  Torta de Chocolate
INSERT INTO RECETA (id_producto, id_insumo, cantidad_necesaria) VALUES 
(1, 8, 0.50),  -- Harina
(1, 7, 4.00),  -- Huevos
(1, 9, 0.30),  -- Azucar
(1, 21, 0.10), -- Cacao
(1, 6, 0.20),  -- Mantequilla
(1, 11, 1.00), -- Leche Evap
(1, 15, 0.02), -- Polvo Hornear
(1, 17, 0.25); -- Fudge

-- 2. Pie de Limón
INSERT INTO RECETA (id_producto, id_insumo, cantidad_necesaria) VALUES 
(2, 8, 0.30), 
(2, 6, 0.15), 
(2, 23, 2.00), -- Leche Cond
(2, 22, 0.50), -- Limon
(2, 7, 3.00), 
(2, 9, 0.20); 

-- Cheesecake de Fresa
INSERT INTO RECETA (id_producto, id_insumo, cantidad_necesaria) VALUES 
(3, 24, 2.00), -- Galletas
(3, 6, 0.10), 
(3, 2, 0.50),  -- Queso Crema
(3, 11, 1.00), 
(3, 25, 0.02), 
(3, 18, 0.30), -- Mermelada
(3, 9, 0.15); 

-- Alfajores
INSERT INTO RECETA (id_producto, id_insumo, cantidad_necesaria) VALUES 
(4, 26, 0.20), -- Maicena
(4, 8, 0.20), 
(4, 6, 0.20), 
(4, 16, 0.30), -- Manjarblanco
(4, 10, 0.05); -- Azucar Impalpable

-- Empanada de Carne
INSERT INTO RECETA (id_producto, id_insumo, cantidad_necesaria) VALUES 
(5, 8, 0.10), 
(5, 6, 0.05), 
(5, 27, 0.08), -- Carne
(5, 28, 0.05), -- Cebolla
(5, 7, 0.25), 
(5, 19, 0.02); -- Aceite


-- SELECTS

SELECT * FROM ROL;
SELECT * FROM USUARIO;
SELECT * FROM PROVEEDOR;
SELECT * FROM INSUMO;
SELECT * FROM PRODUCTO;
SELECT * FROM RECETA;