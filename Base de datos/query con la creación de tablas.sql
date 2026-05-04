CREATE TABLE "CuentaCliente" (
	"NombreUsuario"	TEXT NOT NULL,
	"Contrasena"	TEXT NOT NULL,
	PRIMARY KEY("NombreUsuario")
);

CREATE TABLE "Cliente" (
	"Id"	INTEGER NOT NULL,
	"NombreUsuario"	TEXT NOT NULL,
	"Nombre"	TEXT NOT NULL,
	"PrimerApellido"	TEXT NOT NULL,
	"SegundoApellido"	TEXT NOT NULL,
	"Correo"	TEXT NOT NULL,
	FOREIGN KEY("NombreUsuario") REFERENCES "CuentaCliente"("NombreUsuario"),
	PRIMARY KEY("Id" AUTOINCREMENT)
);


CREATE TABLE "Tarjeta" (
	"Id"	INTEGER NOT NULL,
	"IdCliente"	INTEGER NOT NULL,
	"Numero"	INTEGER NOT NULL,
	"NombreTitular"	TEXT NOT NULL,
	"FechaVencimiento"	TEXT NOT NULL,
	"CodigoSeguridad"	INTEGER NOT NULL,
	FOREIGN KEY("IdCliente") REFERENCES "Cliente"("Id"),
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "Telefono" (
	"Id"	INTEGER NOT NULL,
	"IdCliente"	INTEGER NOT NULL,
	"Numero"	INTEGER NOT NULL,
	FOREIGN KEY("IdCliente") REFERENCES "Cliente"("Id"),
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "Provincia" (
	"Id"	INTEGER NOT NULL,
	"Nombre"	TEXT NOT NULL,
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "Canton" (
	"Id"	INTEGER NOT NULL,
	"IdProvincia"	INTEGER NOT NULL,
	"Nombre"	TEXT NOT NULL,
	FOREIGN KEY("IdProvincia") REFERENCES "Provincia"("Id"),
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "Distrito" (
	"Id"	INTEGER NOT NULL,
	"IdCanton"	INTEGER NOT NULL,
	"Nombre"	TEXT NOT NULL,
	PRIMARY KEY("Id" AUTOINCREMENT),
	FOREIGN KEY("IdCanton") REFERENCES "Canton"("Id")
);

CREATE TABLE "Direccion" (
	"Id"	INTEGER NOT NULL,
	"Provincia" TEXT NOT NULL,
	"Canton" TEXT NOT NULL,
	"Distrito"	TEXT NOT NULL,
	"DireccionExacta"	TEXT NOT NULL,
	PRIMARY KEY("Id" AUTOINCREMENT),
	--FOREIGN KEY("IdDistrito") REFERENCES "Distrito"("Id")
);


CREATE TABLE "DireccionXCliente" (
	"Id"	INTEGER NOT NULL,
	"IdCliente"	INTEGER NOT NULL,
	"IdDireccion"	INTEGER NOT NULL,
	"Activo"	INTEGER NOT NULL,
	FOREIGN KEY("IdCliente") REFERENCES "Cliente"("Id"),
	FOREIGN KEY("IdDireccion") REFERENCES "Direccion"("Id"),
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "Categoria" (
	"Id"	INTEGER NOT NULL,
	"Nombre"	TEXT NOT NULL,
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "Restaurante" (
	"Id"	INTEGER NOT NULL,
	"IdDireccion"	INTEGER NOT NULL,
	"Nombre"	TEXT NOT NULL,
	"Foto"	BLOB NOT NULL,
	"Activo"	INTEGER NOT NULL,
	FOREIGN KEY("IdDireccion") REFERENCES "Direccion"("Id"),
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "RestauranteXCategoria" (
	"IdRestaurante"	INTEGER NOT NULL,
	"IdCategoria"	INTEGER NOT NULL,
	PRIMARY KEY("IdRestaurante","IdCategoria")
);

CREATE TABLE "CuentaAdministrador" (
	"IdLaboral"	TEXT NOT NULL,
	"Contrasena"	TEXT NOT NULL,
	PRIMARY KEY("IdLaboral")
);

CREATE TABLE "Puesto" (
	"Id"	INTEGER NOT NULL,
	"Nombre"	TEXT NOT NULL,
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "Administrador" (
	"Id"	INTEGER NOT NULL,
	"IdRestaurante"	INTEGER NOT NULL,
	"IdLaboral"	TEXT NOT NULL,
	"IdPuesto"	INTEGER NOT NULL,
	"Cedula"	INTEGER NOT NULL,
	"Nombre"	TEXT NOT NULL,
	"PrimerApellido"	TEXT NOT NULL,
	"SegundoApellido"	TEXT NOT NULL,
	PRIMARY KEY("Id" AUTOINCREMENT),
	FOREIGN KEY("IdRestaurante") REFERENCES "Restaurante"("Id"),
	FOREIGN KEY("IdPuesto") REFERENCES "Puesto"("Id"),
	FOREIGN KEY("IdLaboral") REFERENCES "CuentaAdministrador"("IdLaboral")
);

CREATE TABLE "SolicitudEliminacion" (
	"Id"	INTEGER NOT NULL,
	"IdRestaurante"	INTEGER NOT NULL,
	"IdEncargado"	INTEGER NOT NULL,
	"Activo"	INTEGER NOT NULL,
	FOREIGN KEY("IdRestaurante") REFERENCES "Restaurante"("Id"),
	FOREIGN KEY("IdEncargado") REFERENCES "Administrador"("Id"),
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "RestauranteEliminado" (
	"Id"	INTEGER NOT NULL,
	"IdSolicitud"	INTEGER NOT NULL,
	"IdGerente"	INTEGER NOT NULL,
	"FechaEliminado"	TEXT NOT NULL,
	FOREIGN KEY("IdGerente") REFERENCES "Administrador"("Id"),
	FOREIGN KEY("IdSolicitud") REFERENCES "SolicitudEliminacion"("Id"),
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "Menu" (
	"Id"	INTEGER NOT NULL,
	"IdRestaurante"	INTEGER NOT NULL,
	FOREIGN KEY("IdRestaurante") REFERENCES "Restaurante"("Id"),
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "Producto" (
	"Id"	INTEGER NOT NULL,
	"Nombre"	TEXT NOT NULL,
	"Foto"	BLOB NOT NULL,
	"Descripcion"	TEXT NOT NULL,
	"Precio"	REAL NOT NULL,
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "ProductoXMenu" (
	"IdProducto"	INTEGER NOT NULL,
	"IdMenu"	INTEGER NOT NULL,
	"CantidadStock"	INTEGER NOT NULL,
	FOREIGN KEY("IdMenu") REFERENCES "Menu"("Id"),
	FOREIGN KEY("IdProducto") REFERENCES "Producto"("Id"),
	PRIMARY KEY("IdProducto","IdMenu")
);

CREATE TABLE "EstadoPedido" (
	"Id"	INTEGER NOT NULL,
	"Nombre"	TEXT NOT NULL,
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "Pedido" (
	"Id"	INTEGER NOT NULL,
	"IdRestaurante"	INTEGER NOT NULL,
	"IdEstado"	INTEGER NOT NULL,
	"IdCliente"	INTEGER NOT NULL,
	"Fecha"	TEXT NOT NULL,
	"TotalPagado"	REAL NOT NULL,
	PRIMARY KEY("Id" AUTOINCREMENT),
	FOREIGN KEY("IdEstado") REFERENCES "EstadoPedido"("Id"),
	FOREIGN KEY("IdCliente") REFERENCES "Cliente"("Id"),
	FOREIGN KEY("IdRestaurante") REFERENCES "Restaurante"("Id")
);

CREATE TABLE "ItemsPedido" (
	"IdProducto"	INTEGER NOT NULL,
	"IdPedido"	INTEGER NOT NULL,
	"Cantidad"	INTEGER NOT NULL,
	"Activo"	INTEGER NOT NULL,
	FOREIGN KEY("IdPedido") REFERENCES "Pedido"("Id"),
	FOREIGN KEY("IdProducto") REFERENCES "Producto"("Id"),
	PRIMARY KEY("IdProducto","IdPedido")
);

CREATE TABLE "Carrito" (
	"Id"	INTEGER NOT NULL,
	"IdCliente"	INTEGER NOT NULL,
	"FechaCreacion"	TEXT NOT NULL,
	"FechaModificacion"	TEXT NOT NULL,
	FOREIGN KEY("IdCliente") REFERENCES "Cliente"("Id"),
	PRIMARY KEY("Id" AUTOINCREMENT)
);

CREATE TABLE "ItemsCarrito" (
	"IdProducto"	INTEGER NOT NULL,
	"IdCarrito"	INTEGER NOT NULL,
	"Cantidad"	INTEGER NOT NULL,
	"Activo"	INTEGER NOT NULL,
	FOREIGN KEY("IdProducto") REFERENCES "Producto"("Id"),
	FOREIGN KEY("IdCarrito") REFERENCES "Carrito"("Id"),
	PRIMARY KEY("IdProducto","IdCarrito")
);

CREATE TABLE "Entrega" (
	"Id"	INTEGER NOT NULL,
	"IdPedido"	INTEGER NOT NULL,
	"IdRepartidor"	INTEGER NOT NULL,
	PRIMARY KEY("Id" AUTOINCREMENT),
	FOREIGN KEY("IdRepartidor") REFERENCES "Administrador"("Id"),
	FOREIGN KEY("IdPedido") REFERENCES "Pedido"("Id")
);