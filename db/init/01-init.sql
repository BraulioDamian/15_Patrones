SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- Elimina líneas redundantes
CREATE SCHEMA IF NOT EXISTS dbtienda CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE dbtienda;

-- Tabla: area
CREATE TABLE IF NOT EXISTS area (
    AreaID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    GananciaPorcentaje DOUBLE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: productos
CREATE TABLE IF NOT EXISTS productos (
    ProductoID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    Descripcion VARCHAR(255),
    AreaID INT,
    Precio DOUBLE,
    UnidadesDisponibles INT,
    NivelReorden INT,
    FechaCaducidad DATE,
    CodigoBarras VARCHAR(255),
    TamañoNeto VARCHAR(255),  -- Asegúrate de que el código fuente use UTF-8
    Marca VARCHAR(255),
    Contenido VARCHAR(255),
    FOREIGN KEY (AreaID) REFERENCES area(AreaID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: Usuario
CREATE TABLE IF NOT EXISTS usuario (
    UsuarioID INT AUTO_INCREMENT PRIMARY KEY,
    NombreUsuario VARCHAR(255) UNIQUE NOT NULL,
    Contraseña VARCHAR(255) NOT NULL,
    Rol VARCHAR(255) NOT NULL,
    ContraseñaToken VARCHAR(255),
    Email VARCHAR(255),
    NombreCompleto VARCHAR(255),
    PreguntaSeguridad TEXT,
    RespuestaSeguridad TEXT,
    UltimoLogin TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: ventas
CREATE TABLE IF NOT EXISTS ventas (
    VentaID INT AUTO_INCREMENT PRIMARY KEY,
    UsuarioID INT,
    FechaVenta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PrecioTotal DOUBLE,
    FOREIGN KEY (UsuarioID) REFERENCES Usuario(UsuarioID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: detallesventa
CREATE TABLE IF NOT EXISTS detallesventa (
    DetalleVentaID INT AUTO_INCREMENT PRIMARY KEY,
    VentaID INT,
    ProductoID INT,
    Cantidad INT,
    PrecioUnitario DOUBLE,
    PrecioTotal DOUBLE,
    FOREIGN KEY (VentaID) REFERENCES ventas(VentaID),
    FOREIGN KEY (ProductoID) REFERENCES productos(ProductoID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: configuracionroles
CREATE TABLE IF NOT EXISTS configuracionroles (
    Rol VARCHAR(255) PRIMARY KEY,
    ContrasenaHash VARCHAR(255)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Índices para mejorar el rendimiento
CREATE INDEX idx_productos_nombre ON productos (Nombre);
CREATE INDEX idx_ventas_usuario ON ventas (UsuarioID);
CREATE INDEX idx_detallesventa_venta ON detallesventa (VentaID);