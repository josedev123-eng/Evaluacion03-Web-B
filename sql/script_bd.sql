-- =====================================================================
-- Evaluacion 03 - Gestion de pagos de servicios (Microservicios)
-- Cada microservicio tiene su PROPIA base de datos, no se comparten tablas.
-- En el perfil dev las tablas se crean solas (ddl-auto: update),
-- este script sirve para el perfil prod o para crear todo a mano.
-- =====================================================================

-- ---------------------------------------------------------------------
-- 1) cliente-service  (puerto 8081)
-- ---------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS bd_cliente;
USE bd_cliente;

CREATE TABLE IF NOT EXISTS cliente (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    dni         VARCHAR(8)   NOT NULL,
    nombres     VARCHAR(80)  NOT NULL,
    apellidos   VARCHAR(80)  NOT NULL,
    correo      VARCHAR(100) NOT NULL,
    telefono    VARCHAR(9),
    direccion   VARCHAR(150),
    PRIMARY KEY (id),
    UNIQUE KEY uk_cliente_dni (dni)
);

INSERT INTO cliente (dni, nombres, apellidos, correo, telefono, direccion) VALUES
('72145896', 'Carlos',  'Ramirez Torres', 'carlos.ramirez@correo.com', '987654321', 'Av. Cascanueces 2221, Santa Anita'),
('70985412', 'Lucia',   'Quispe Mendoza', 'lucia.quispe@correo.com',   '912345678', 'Jr. Los Pinos 145, Ate'),
('74521369', 'Miguel',  'Flores Huaman',  'miguel.flores@correo.com',  '956789123', 'Calle Las Rosas 320, La Molina');

-- ---------------------------------------------------------------------
-- 2) servicio-service  (puerto 8082)
-- ---------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS bd_servicio;
USE bd_servicio;

CREATE TABLE IF NOT EXISTS servicio (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    nombre          VARCHAR(60)  NOT NULL,
    descripcion     VARCHAR(200),
    empresa         VARCHAR(80)  NOT NULL,
    tarifa_mensual  DOUBLE       NOT NULL,
    activo          BOOLEAN      NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_servicio_nombre (nombre)
);

INSERT INTO servicio (nombre, descripcion, empresa, tarifa_mensual, activo) VALUES
('Luz',      'Suministro de energia electrica residencial', 'Luz del Sur', 120.50, TRUE),
('Agua',     'Servicio de agua potable y alcantarillado',   'Sedapal',      65.80, TRUE),
('Internet', 'Internet fibra optica 200 Mbps',              'Movistar',     99.90, TRUE);

-- ---------------------------------------------------------------------
-- 3) recibo-service  (puerto 8083)
-- idCliente e idServicio son solo IDs: NO hay FOREIGN KEY porque los
-- datos estan en otras bases (bd_cliente y bd_servicio).
-- ---------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS bd_recibo;
USE bd_recibo;

CREATE TABLE IF NOT EXISTS recibo (
    id                 BIGINT      NOT NULL AUTO_INCREMENT,
    id_cliente         BIGINT      NOT NULL,
    id_servicio        BIGINT      NOT NULL,
    periodo            VARCHAR(7)  NOT NULL,
    monto              DOUBLE      NOT NULL,
    fecha_emision      DATE        NOT NULL,
    fecha_vencimiento  DATE        NOT NULL,
    estado_pago        VARCHAR(10) NOT NULL DEFAULT 'PENDIENTE',
    fecha_pago         DATE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_recibo_cliente_servicio_periodo (id_cliente, id_servicio, periodo),
    CONSTRAINT chk_recibo_monto  CHECK (monto > 0),
    CONSTRAINT chk_recibo_estado CHECK (estado_pago IN ('PENDIENTE', 'PAGADO'))
);

INSERT INTO recibo (id_cliente, id_servicio, periodo, monto, fecha_emision, fecha_vencimiento, estado_pago, fecha_pago) VALUES
(1, 1, '2026-10', 120.50, '2026-10-01', '2026-10-20', 'PAGADO',    '2026-10-15'),
(1, 2, '2026-10',  65.80, '2026-10-01', '2026-10-20', 'PENDIENTE', NULL),
(2, 3, '2026-10',  99.90, '2026-10-01', '2026-10-25', 'PENDIENTE', NULL);

-- consultas de verificacion
SELECT * FROM bd_cliente.cliente;
SELECT * FROM bd_servicio.servicio;
SELECT * FROM bd_recibo.recibo;
