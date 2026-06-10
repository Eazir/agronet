-- ============================================================
-- AgroNet Database Initialization Script
-- Database: MySQL 8
-- Usage: Mounted as docker-entrypoint-initdb.d in MySQL container
-- ============================================================

CREATE DATABASE IF NOT EXISTS AgroNet CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE AgroNet;

-- -----------------------------------------------------------
-- 1. tipo_documento
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS tipo_documento (
    codigo_doc       BIGINT        NOT NULL AUTO_INCREMENT,
    nombre_documento VARCHAR(100)  NOT NULL,
    tipo_doc         VARCHAR(100)  NOT NULL,
    PRIMARY KEY (codigo_doc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------
-- 2. categoria_productos
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS categoria_productos (
    codigo_categoria  BIGINT        NOT NULL AUTO_INCREMENT,
    nombre_categoria  VARCHAR(100)  NOT NULL,
    url_imagen        VARCHAR(1000) DEFAULT NULL,
    PRIMARY KEY (codigo_categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------
-- 3. usuario_consumidor
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario_consumidor (
    doc_consumidor  BIGINT        NOT NULL,
    codigo_doc      BIGINT        NOT NULL,
    tipo_usuario    VARCHAR(100)  NOT NULL,
    nombre_usuario  VARCHAR(100)  NOT NULL,
    email           VARCHAR(100)  NOT NULL,
    contraseña      VARCHAR(100)  NOT NULL,
    PRIMARY KEY (doc_consumidor),
    UNIQUE KEY uk_consumidor_email (email),
    CONSTRAINT fk_consumidor_tipo_doc FOREIGN KEY (codigo_doc)
        REFERENCES tipo_documento (codigo_doc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------
-- 4. usuario_productor
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario_productor (
    doc_productor   BIGINT        NOT NULL,
    codigo_doc      BIGINT        NOT NULL,
    tipo_usuario    VARCHAR(100)  NOT NULL,
    nombre_usuario  VARCHAR(100)  NOT NULL,
    email           VARCHAR(100)  NOT NULL,
    contraseña      VARCHAR(100)  NOT NULL,
    PRIMARY KEY (doc_productor),
    UNIQUE KEY uk_productor_email (email),
    CONSTRAINT fk_productor_tipo_doc FOREIGN KEY (codigo_doc)
        REFERENCES tipo_documento (codigo_doc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------
-- 5. productos
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS productos (
    codigo_producto     BIGINT        NOT NULL AUTO_INCREMENT,
    codigo_categoria    BIGINT        NOT NULL,
    doc_productor       BIGINT        NOT NULL,
    nombre_producto     VARCHAR(100)  NOT NULL,
    origen_producto     VARCHAR(200)  DEFAULT NULL,
    descripcion         VARCHAR(500)  DEFAULT NULL,
    estado              TINYINT(1)    NOT NULL,
    url_imagen          VARCHAR(1000) DEFAULT NULL,
    fecha_creacion      DATETIME      DEFAULT NULL,
    fecha_actualizacion DATETIME      DEFAULT NULL,
    PRIMARY KEY (codigo_producto),
    CONSTRAINT fk_producto_categoria FOREIGN KEY (codigo_categoria)
        REFERENCES categoria_productos (codigo_categoria),
    CONSTRAINT fk_producto_productor FOREIGN KEY (doc_productor)
        REFERENCES usuario_productor (doc_productor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------
-- 6. stock_producto
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS stock_producto (
    id_stock             BIGINT          NOT NULL AUTO_INCREMENT,
    codigo_producto      BIGINT          DEFAULT NULL,
    precio_kilo          DECIMAL(10,2)   DEFAULT NULL,
    cantidad_stock       DECIMAL(10,2)   DEFAULT NULL,
    vende_kg             TINYINT(1)      DEFAULT NULL,
    vende_lb             TINYINT(1)      DEFAULT NULL,
    vende_tonelada       TINYINT(1)      DEFAULT NULL,
    fecha_creacion       DATETIME        DEFAULT NULL,
    fecha_actualizacion  DATETIME        DEFAULT NULL,
    PRIMARY KEY (id_stock),
    CONSTRAINT fk_stock_producto FOREIGN KEY (codigo_producto)
        REFERENCES productos (codigo_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------
-- 7. carrito
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS carrito (
    codigo_carrito  BIGINT          NOT NULL AUTO_INCREMENT,
    doc_consumidor  BIGINT          NOT NULL,
    total_carrito   DECIMAL(10,2)   NOT NULL,
    fecha_creacion  DATETIME        DEFAULT NULL,
    PRIMARY KEY (codigo_carrito),
    CONSTRAINT fk_carrito_consumidor FOREIGN KEY (doc_consumidor)
        REFERENCES usuario_consumidor (doc_consumidor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------
-- 8. productos_carrito
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS productos_carrito (
    codigo_producto_carrito BIGINT        NOT NULL AUTO_INCREMENT,
    codigo_carrito          BIGINT        NOT NULL,
    codigo_producto         BIGINT        NOT NULL,
    cantidad                INT           DEFAULT NULL,
    precio_unitario         DECIMAL(10,2) NOT NULL,
    unidad_venta            VARCHAR(255)  NOT NULL,
    subtotal                DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (codigo_producto_carrito),
    CONSTRAINT fk_prodcarrito_carrito FOREIGN KEY (codigo_carrito)
        REFERENCES carrito (codigo_carrito),
    CONSTRAINT fk_prodcarrito_producto FOREIGN KEY (codigo_producto)
        REFERENCES productos (codigo_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------
-- 9. Pedido
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS Pedido (
    codigo_pedido     BIGINT        NOT NULL AUTO_INCREMENT,
    doc_consumidor    BIGINT        NOT NULL,
    direccion_entrega VARCHAR(300) NOT NULL,
    estado            VARCHAR(50)   DEFAULT 'pendiente',
    fecha_pedido      DATETIME      DEFAULT NULL,
    PRIMARY KEY (codigo_pedido),
    CONSTRAINT fk_pedido_consumidor FOREIGN KEY (doc_consumidor)
        REFERENCES usuario_consumidor (doc_consumidor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------
-- 10. productos_pedido
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS productos_pedido (
    codigo_producto_pedido BIGINT        NOT NULL AUTO_INCREMENT,
    codigo_pedido          BIGINT        NOT NULL,
    codigo_producto        BIGINT        NOT NULL,
    cantidad               INT           DEFAULT NULL,
    precio_unitario        DECIMAL(10,2) NOT NULL,
    unidad_venta           VARCHAR(255)  NOT NULL,
    subtotal               DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (codigo_producto_pedido),
    CONSTRAINT fk_prodpedido_pedido FOREIGN KEY (codigo_pedido)
        REFERENCES Pedido (codigo_pedido),
    CONSTRAINT fk_prodpedido_producto FOREIGN KEY (codigo_producto)
        REFERENCES productos (codigo_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------
-- 11. proyectos
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS proyectos (
    codigo_proyecto      BIGINT          NOT NULL AUTO_INCREMENT,
    doc_productor        BIGINT          NOT NULL,
    nombre_proyecto      VARCHAR(100)    NOT NULL,
    descripcion          TEXT            DEFAULT NULL,
    meta                 DECIMAL(10,2)   NOT NULL,
    recaudado            DECIMAL(10,2)   DEFAULT NULL,
    donaciones_recibidas INT             DEFAULT NULL,
    url_imagen           VARCHAR(1000)   DEFAULT NULL,
    fecha_creacion       DATETIME        DEFAULT NULL,
    fecha_actualizacion  DATETIME        DEFAULT NULL,
    PRIMARY KEY (codigo_proyecto),
    CONSTRAINT fk_proyecto_productor FOREIGN KEY (doc_productor)
        REFERENCES usuario_productor (doc_productor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------
-- 12. donaciones
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS donaciones (
    codigo_donacion  BIGINT          NOT NULL AUTO_INCREMENT,
    codigo_proyecto  BIGINT          NOT NULL,
    doc_consumidor   BIGINT          NOT NULL,
    fecha_donacion   DATETIME        DEFAULT NULL,
    monto            DECIMAL(10,2)   NOT NULL,
    PRIMARY KEY (codigo_donacion),
    CONSTRAINT fk_donacion_proyecto FOREIGN KEY (codigo_proyecto)
        REFERENCES proyectos (codigo_proyecto),
    CONSTRAINT fk_donacion_consumidor FOREIGN KEY (doc_consumidor)
        REFERENCES usuario_consumidor (doc_consumidor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- SEED DATA
-- ============================================================

-- Tipos de Documento
INSERT INTO tipo_documento (codigo_doc, nombre_documento, tipo_doc) VALUES
(1, 'Cédula de Ciudadanía', 'CC'),
(2, 'Registro Único Tributario', 'RUT'),
(3, 'Cédula de Extranjería', 'CE'),
(4, 'Pasaporte', 'PAS'),
(5, 'Tarjeta de Identidad', 'TI')
ON DUPLICATE KEY UPDATE nombre_documento = VALUES(nombre_documento);

-- Categorías de Productos Agrícolas
INSERT INTO categoria_productos (codigo_categoria, nombre_categoria, url_imagen) VALUES
(1, 'Frutas Frescas', NULL),
(2, 'Verduras y Hortalizas', NULL),
(3, 'Tubérculos y Raíces', NULL),
(4, 'Cereales y Granos', NULL),
(5, 'Lácteos y Derivados', NULL),
(6, 'Carnes y Embutidos', NULL),
(7, 'Huevos y Avícolas', NULL),
(8, 'Miel y Derivados', NULL),
(9, 'Café y Chocolate', NULL),
(10, 'Hierbas y Medicinales', NULL),
(11, 'Artesanías del Campo', NULL),
(12, 'Abonos y Fertilizantes Naturales', NULL)
ON DUPLICATE KEY UPDATE nombre_categoria = VALUES(nombre_categoria);
