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


-- ============================================================
-- INSERTAR INSUMOS
-- ============================================================

INSERT INTO INSUMO (nombre, descripcion, unidad_medida, stock_actual, stock_minimo, fecha_caducidad, estado) VALUES 
-- [BAJO STOCK & VENCIDO] (Para probar Alerta Roja total)
('Levadura Fresca', 'Levadura en bloque para pan', 'Unidades', 2.00, 5.00, '2025-09-15', 'Activo'), 

-- [VENCIDOS RECIENTES] (Septiembre - Octubre 2025)
('Queso Crema', 'Queso tipo Philadelphia', 'Kg', 5.00, 2.00, '2025-09-28', 'Activo'),
('Yogurt Natural', 'Yogurt sin dulce para postres', 'Litros', 4.00, 2.00, '2025-10-05', 'Activo'),
('Masa Hojaldre', 'Masa lista congelada', 'Kg', 10.00, 3.00, '2025-10-20', 'Activo'),

-- [ALERTAS AMARILLAS / PRÓXIMOS A VENCER] (Noviembre - Diciembre 2025)
('Crema de Leche', 'Crema de batir UHT', 'Litros', 12.00, 5.00, '2025-12-15', 'Activo'), -- Revisa fecha actual
('Mantequilla sin Sal', 'Barra de mantequilla repostera', 'Kg', 8.00, 3.00, '2025-12-20', 'Activo'),
('Huevos Pardos', 'Jaba de huevos medianos', 'Unidades', 120.00, 30.00, '2025-12-25', 'Activo'),

-- [FECHAS SEGURAS] (2026 en adelante)
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
('Coco Rallado', 'Coco deshidratado fino', 'Kg', 5.00, 1.00, '2026-08-05', 'Activo');

-- ============================================================
-- INSERTAR PROVEEDORES
-- ============================================================

INSERT INTO PROVEEDOR (nombre, ruc, telefono, direccion, correo, estado) VALUES 
-- MATERIA PRIMA PRINCIPAL
('Comercial Harinera del Norte S.A.C.', '20100123456', '01-456-7890', 'Av. Industrial 1050, Lima', 'ventas@harinera.com', 'Activo'),
('Avícola Santa Clara', '20200987654', '987-654-321', 'Carr. Central Km 15, Ate', 'pedidos@avicolasanta.com', 'Activo'),
('Lácteos del Valle', '20300112233', '01-222-3344', 'Jr. Los Establos 400, Lurín', 'contacto@lacteosvalle.pe', 'Activo'),

-- INSUMOS ESPECIFICOS
('Chocolates y Cacao Perú', '20400556677', '999-888-777', 'Calle Las Camelias 120, San Isidro', 'info@cacaoperu.com', 'Activo'),
('Esencias y Sabores S.R.L.', '20500998877', '01-333-2211', 'Av. Argentina 500, Callao', 'ventas@esencias.com', 'Activo'),
('Frutas Selectas El Huerto', '20600443322', '955-112-233', 'Mercado Mayorista Puesto 45', 'pedidos@frutaselhuerto.com', 'Activo'),

-- EMPAQUES Y DECORACIÓN
('Empaques Ecológicos Lima', '20700123987', '01-444-5566', 'Av. El Sol 800, Barranco', 'cajas@empaqueseco.com', 'Activo'),
('Importaciones DecorCake', '20800765432', '912-345-678', 'Jr. Andahuaylas 200, Cercado', 'tienda@decorcake.com', 'Activo'),

-- SERVICIOS Y SUMINISTROS
('Distribuidora de Gas "Llama Azul"', '20900111222', '01-555-1234', 'Av. Universitaria 3000, SMP', 'pedidos@llamaazul.com', 'Activo'),
('Comercializadora Todo Plástico', '20101234567', '998-765-432', 'Av. Próceres 100, SJL', 'ventas@todoplastico.com', 'Activo'),

-- MAYORISTAS GENERALES
('Makro Supermayorista', '20505050501', '01-600-0000', 'Av. Panamericana Norte, Independencia', 'atencion@makro.com.pe', 'Activo'),
('Mercado Central de Abastos', '10404040402', '944-555-666', 'Jr. Huallaga 300, Lima', 'contacto@mercadocentral.pe', 'Activo');
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




