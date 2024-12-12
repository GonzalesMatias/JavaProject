CREATE TABLE Productos (
id_producto INT PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(255) NOT NULL,
descripción TEXT,
precio DECIMAL(10, 2) NOT NULL,
categoría VARCHAR(255),
stock_minimo INT,
stock_maximo INT
);

CREATE TABLE Inventario (
id_inventario INT PRIMARY KEY AUTO_INCREMENT,
id_producto INT,
cantidad_actual INT,
fecha_actualizacion DATETIME,
FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

CREATE TABLE Proveedores (
id_proveedor INT PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(255) NOT NULL,
contacto VARCHAR(255),
telefono VARCHAR(50),
email VARCHAR(255)
);

CREATE TABLE Compras (
id_compra INT PRIMARY KEY AUTO_INCREMENT,
id_proveedor INT,
fecha_compra DATETIME,
total_compra DECIMAL(10, 2),
FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor)
);

CREATE TABLE Detalle_Compras (
id_detalle_compra INT PRIMARY KEY AUTO_INCREMENT,
id_compra INT,
id_producto INT,
cantidad INT,
precio_unitario DECIMAL(10, 2),
FOREIGN KEY (id_compra) REFERENCES Compras(id_compra),
FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

CREATE TABLE Ventas (
id_venta INT PRIMARY KEY AUTO_INCREMENT,
fecha_venta DATETIME,
total_venta DECIMAL(10, 2)
);

CREATE TABLE Detalle_Ventas (
id_detalle_venta INT PRIMARY KEY AUTO_INCREMENT,
id_venta INT,
id_producto INT,
cantidad INT,
precio_unitario DECIMAL(10, 2),
FOREIGN KEY (id_venta) REFERENCES Ventas(id_venta),
FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

CREATE TABLE Clientes (
id_cliente INT PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(255) NOT NULL,
contacto VARCHAR(255),
telefono VARCHAR(50),
email VARCHAR(255)
);

CREATE TABLE Usuarios (
id_usuario INT PRIMARY KEY AUTO_INCREMENT,
nombre_usuario VARCHAR(255) NOT NULL,
contraseña VARCHAR(255) NOT NULL,
rol VARCHAR(50)
);

CREATE TABLE Movimientos_Inventario (
id_movimiento INT PRIMARY KEY AUTO_INCREMENT,
id_producto INT,
tipo_movimiento VARCHAR(50),
cantidad INT,
fecha_movimiento DATETIME,
id_usuario INT,
FOREIGN KEY (id_producto) REFERENCES Productos(id_producto),
FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);
SELECT * FROM Usuarios;
SELECT * FROM Productos;