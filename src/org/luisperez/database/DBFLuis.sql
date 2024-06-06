drop database if exists dbfluis;
 
create database if not exists dbfluis;
 
use  dbfluis;

create table Clientes(
	clienteId int not null auto_increment,
    nombre varchar(30) not null,
    apellido varchar(30) not null,
    telefono varchar(15),
    direccion varchar(150) not null,
    nit varchar(15) default 'CF', 
    
    primary key PK_clienteId(clienteId)
); 

create table Cargos(
	cargoId int not null auto_increment,
    nombreCargo varchar(30) not null,
    descripcionCargo varchar(100) not null,
    
    primary key PK_cargoId(cargoId)
);

create table Compras (
	compraId int not null auto_increment,
    fechaCompra date not null,
    totalCompra decimal (10,2),
    
    primary key PK_compraId(compraId)
);

create table CategoriaProductos(
	categoriaProductoId int  not null auto_increment,
    nombreCategoria varchar(30) not null,
    descripcionCategoria varchar(100) not null,
    
    primary key PK_categoriaProductoId(categoriaProductoId)
);

create table Distribuidores(
	distribuidorId int not null auto_increment,
    nombreDistribuidor varchar (30)  not null,
    direccionDistribuidor varchar (200) not null,
    nitDistribuidor varchar (15) not null,
    telefonoDistribuidor varchar(15) not null,
    web varchar (50),
    
    primary key PK_distribuidorId(distribuidorId)
);

create table Productos(
	productoId int not null auto_increment,
    nombreProducto varchar(50) not null,
    descripcionProducto varchar(100) ,
    cantidadStock int not null,
    precioVentaUnitario decimal (10,2) not null,
    precioVentaMayor decimal(10,2) not null,
    precioCompra decimal(10,2) not null,
-- BloB 
	distribuidorId int not null,
	categoriaProductoId int not null,
    
    primary key PK_productoId(productoId),
	constraint FK_Productos_Distribuidores foreign key Productos(distribuidorId)
		references Distribuidores(distribuidorId),
	constraint FK_Productos_CategoriaProductos foreign key Productos(categoriaProductoId)
		references CategoriaProductos(categoriaProductoId)
);

create table DetallesCompras(
	detalleCompraId int not null auto_increment,
    cantidadCompra int not null,
    productoId int not null,
    compraId int not null,
    
    primary key PK_detalleCompraId(detalleCompraId),
    constraint FK_DetallesCompras_Productos foreign key DetallesCompras(productoId)
		references Productos(productoId),
    constraint FK_DetallesCompras_Compras foreign key DetallesCompras(compraId)
		references Compras(compraId)
    
);

create table Promociones(
	promocionId int not null auto_increment,
    precioPromocion decimal(10,2)not null,
    descripcionPromocion varchar(1000),
	fechaInicio date not null,
    fechaFinalizacion date not null,
    productoId int not null,
    
    primary key PK_promocionId(promocionId),
    constraint FK_Promociones_Productos foreign key Promociones(productoId)
		references Productos(productoId)
);

create table Empleados(
	empleadoId int not null auto_increment,
    nombreEmpleado varchar(30) not null,
    apellidoEmpleado varchar (30) not null,
    sueldo decimal (10,2) not null,
    horaEntrada time not null,
    horaSalida time not null,
    cargoId int not null,
    encargadoId int,
    
    primary key PK_Empleados(empleadoId),
    constraint FK_Empleados_Cargos foreign key Empleados(cargoId)
		references Cargos(cargoId),
	constraint FK_encargadoId foreign key Empleados(encargadoId)
		references Empleados(empleadoId)

);

create table Facturas(
	facturaId int not null auto_increment,
    fecha time not null,
    hora time not null,
    clienteId int not null,
	empleadoId int not null,
    total decimal (10,2),
    
    primary key PK_Facturas(facturaId),
    constraint FK_Facturas_Clientes foreign key Facturas(clienteId)
		references Clientes (clienteId),
	constraint PK_Facturas_Empleados foreign key Facturas(empleadoId)
		references Empleados(empleadoId)
);

create table DetallesFacturas(
	detalleFacturaId int not null auto_increment,
    facturaId int not null,
    productoId int not null,
    
    primary key PK_detalleFacturaId(detalleFacturaId),
    constraint FK_DetallesFacturas_Facturas foreign key DetallesFacturas(facturaId)
		references Facturas(facturaId),
	constraint FK_DetallesFacturas_Productos foreign key DetallesFacturas(productoId)
		references Productos(productoId)
);

create table TicketsSoportes(
	ticketSoporteId int not null auto_increment,
    descripcionTicket varchar(250) not null,
    estatus varchar (30) not null,
    clienteId int not null,
    facturaId int null,
    
	primary key PK_TicketsSoportes(ticketSoporteId),
    constraint FK_TicketsSoportes_Clientes foreign key TicketsSoportes(clienteId)
		references Clientes(clienteId),
	constraint FK_TicketsSoportes_Facturas foreign key TicketsSoportes(facturaId)
		references Facturas(facturaId)
	
);

create table NivelesAcceso(
	nivelAccesoId int not null auto_increment,
    nivelAcceso varchar(40) not null,
    primary key PK_nivelAccesoId (nivelAccesoId)
);

create table Usuarios(
	usuarioId int not null auto_increment,
    usuario varchar(30) not null,
	contrasenia varchar(100) not null,
    nivelAccesoId int not null,
    empleadoId int not null,
    primary key PK_usuarioId(usuarioId),
    constraint FK_Usuarios_NivelesAcceso foreign key Usuarios(nivelAccesoId)
		references NivelesAcceso(nivelAccesoId),
	constraint FK_Usuarios_Empleados foreign key Usuarios(empleadoId)
		references Empleados(empleadoId)
);

-- Clientes
insert into Clientes(nombre, apellido, telefono, nit, direccion) values
    ('Ana', 'García', '9876-5432', '09876543-1', 'Avenida Principal'),
    ('Juan', 'Pérez', '5678-1234', '12345678-9', 'Calle Secundaria'),
    ('María', 'López', '3456-7890', '56789012-3', 'Boulevard Central'),
    ('Pedro', 'Martínez', '8765-4321', '32109876-5', 'Avenida Norte'),
    ('Carla', 'Hernández', '6543-2109', '67890123-7', 'Calle Este');

-- Cargos
insert into Cargos(nombreCargo, descripcionCargo) values
    ('Cajero', 'Responsable de las transacciones de pago.'),
    ('Recepcionista', 'Encargado de recibir a los clientes.'),
    ('Almacenero', 'Administra el inventario del almacén.'),
    ('Supervisor', 'Supervisa el rendimiento del personal.'),
    ('Contador', 'Encargado de las finanzas de la empresa.');

-- Compras
insert into Compras(fechaCompra, totalCompra) values
    ('2024-05-20', '150.00'),
    ('2024-05-21', '200.00'),
    ('2024-05-22', '175.00'),
    ('2024-05-23', '180.00'),
    ('2024-05-24', '190.00');

-- Empleados
insert into Empleados(nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId, encargadoId) values
    ('Sofía', 'González', '250.00', '09:00:00', '17:00:00', 2, 3),
    ('Andrés', 'Ramírez', '300.00', '10:00:00', '18:00:00', 1, 4),
    ('Laura', 'Díaz', '270.00', '08:00:00', '16:00:00', 3, 2),
    ('Diego', 'Sánchez', '280.00', '11:00:00', '19:00:00', 4, 5),
    ('Natalia', 'Fernández', '260.00', '07:00:00', '15:00:00', 5, 1);

-- Facturas
insert into Facturas(fecha, hora, clienteId, empleadoId, total) values
    ('2024-05-20', '10:00:00', 2, 4, '55.00'),
    ('2024-05-21', '11:00:00', 3, 2, '70.00'),
    ('2024-05-22', '12:00:00', 4, 5, '65.00'),
    ('2024-05-23', '13:00:00', 5, 1, '80.00'),
    ('2024-05-24', '14:00:00', 1, 3, '90.00');

-- TicketSoporte
insert into TicketSoporte(descripcionTicket, estatus, clienteId, facturaId) values
    ('Problema con la impresora', 'En proceso', 3, 2),
    ('Fallo en el sistema', 'Recién creado', 4, 5),
    ('Consulta sobre el producto', 'En proceso', 2, 3),
    ('Solicitud de reembolso', 'Recién creado', 5, 1),
    ('Error en la factura', 'Recién creado', 1, 4);

-- Distribuidores
insert into Distribuidores(nombreDistribuidor, telefonoDistribuidor, nitDistribuidor, direccionDistribuidor, web) values
    ('Distribuidora Acme', '1111-2222', '09876543', 'Avenida Principal', 'www.acme.com'),
    ('Mayorista B&B', '3333-4444', '12345678', 'Calle Secundaria', 'www.bby.com'),
    ('Importadora XYZ', '5555-6666', '56789012', 'Boulevard Central', 'www.xyzimports.com'),
    ('Distribuciones R&S', '7777-8888', '32109876', 'Avenida Norte', 'www.rsdist.com'),
    ('Comercializadora Global', '9999-0000', '67890123', 'Calle Este', 'www.global.com');

-- CategoriaProductos
insert into CategoriaProductos(nombreCategoria, descripcionCategoria) values
    ('Electrodomésticos', 'Productos electrónicos para el hogar.'),
    ('Ropa', 'Variedad de prendas de vestir.'),
    ('Juguetes', 'Juguetes para todas las edades.'),
    ('Herramientas', 'Instrumentos para el trabajo.'),
    ('Muebles', 'Mobiliario para el hogar.');

-- Productos
insert into Productos(nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, distribuidorId, categoriaProductoId) values
    ('Televisor LED', 'Televisor de alta definición.', 20, 500.00, 450.00, 300.00, 1, 1),
    ('Camisa de Vestir', 'Camisa elegante para caballero.', 30, 35.00, 30.00, 15.00, 2, 2),
    ('Rompecabezas 1000 piezas', 'Juego de rompecabezas para adultos.', 40, 25.00, 20.00, 10.00, 3, 3),
    ('Taladro inalámbrico', 'Herramienta eléctrica para perforar.', 25, 80.00, 70.00, 50.00, 4, 4),
    ('Sofá de Cuero', 'Sofá de cuero genuino.', 15, 700.00, 650.00, 500.00, 5, 5);

-- Promociones
insert into Promociones(precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId) values
    (400.00, 'Oferta especial de televisores LED.', '2024-05-20', '2024-05-30', 1),
    (20.00, 'Descuento en camisas de vestir.', '2024-05-21', '2024-06-05', 2),
    (15.00, 'Precio rebajado en rompecabezas.', '2024-05-22', '2024-06-10', 3),
    (60.00, 'Oferta limitada en taladros inalámbricos.', '2024-05-23', '2024-06-15', 4),
    (600.00, 'Descuento en sofás de cuero.', '2024-05-24', '2024-06-20', 5);

-- DetalleFactura
insert into DetalleFactura(facturaId, productoId) values
    (2, 1),
    (3, 2),
    (4, 3),
    (5, 4),
    (1, 5);

-- DetalleCompra
insert into DetalleCompra(cantidadCompra, productoId, compraId) values
    (10, 5, 1),
    (20, 4, 2),
    (15, 3, 3),
    (25, 2, 4),
    (30, 1, 5);

    
insert into NivelesAcceso(nivelAcceso) values
    ('admin'),
    ('usuario');

insert into Usuarios (usuario, contrasenia, nivelAccesoId, empleadoId) values 
('Luis', '1234', 2, 1);
    
select * from Usuarios;
    -- Función para calcular si un producto tiene una promoción activa
delimiter $$
create function calcularPromocionActiva(prodId int) returns int deterministic
begin
    declare promActiva int default 0;
    declare i int default 1;
    declare fecF date;

    resultLoop: loop
        select fechaFinalizacion into fecF from Promociones
			where promocionId = i and productoId = prodId;

        if fecF is not null then
            if fecF > date(now()) then
                set promActiva = 1; 
            end if;
        end if;

        set i = i + 1; 

        if i > (select count(*) from Promociones where productoId = prodId) then
            leave resultLoop; 
        end if;
    end loop resultLoop;

    return promActiva;
end $$
delimiter ;

-- Función para calcular el total de una factura
delimiter $$
create function calcularTotalFactura(facId int) returns decimal(10,2) deterministic
begin
    declare total decimal(10,2) default 0.0;
    declare i int default 1;
    declare precio decimal(10,2);

    totalLoop: loop
        if calcularPromocionActiva(facId) = 0 then
            if facId = (select facturaId from DetalleFactura DF where detalleFacturaId = i) then
                set precio = (select P.precioVentaUnitario from Productos P where productoId = (select productoId from DetalleFactura where detalleFacturaId = i));
                set total = total + precio + (precio*0.12);
            end if;
        else 
            if facId = (select facturaId from DetalleFactura DF where detalleFacturaId = i) then
                set precio = (select PR.precioPromocion from Promociones PR where productoId = (select productoId from DetalleFactura where detalleFacturaId = i));
                set total = total + precio + (precio*0.12);
            end if;
        end if;

        if i = (select count(*) from DetalleFactura) then
            leave totalLoop;
        end if;

        set i = i + 1;
    end loop totalLoop;

    call asignarTotalFactura(facId, total);

    return total;
end $$
delimiter ;

-- Función para disminuir el stock de un producto después de una venta
delimiter $$
create function disminuirStockVenta(prodId int) returns int deterministic
begin
    declare stockActual int default 0;
    
    select cantidadStock into stockActual from Productos where productoId = prodId limit 1;
    
    if stockActual > 0 then
        update Productos set cantidadStock = stockActual - 1 where productoId = prodId;
    end if;
    
    return stockActual - 1;
end $$
delimiter ;

-- Trigger para calcular el total de una factura después de insertar un detalle de factura
delimiter $$
create trigger calcularTotalFacturaAfterInsert
after insert on DetalleFactura
for each row
begin
    declare totalFactura decimal(10,2);
    declare nuevoStock int;
    
    set totalFactura = calcularTotalFactura(new.facturaId);
    set nuevoStock = disminuirStockVenta(new.productoId); 
end$$
delimiter ;

-- Función para calcular el total de una compra
delimiter $$
create function calcularTotalCompra(comId int) returns decimal(10,2) deterministic
begin
    declare totalCompra decimal(10,2) default 0.0;
    declare i int default 1;
    declare precio decimal(10,2);
    declare cantidadComprada int default 0;
    declare curCantidadCompra, curProductoId, curCompraId int;
    
    declare cursorDetalleCompra cursor for
		select DC.cantidadCompra, DC.productoId, DC.compraId from DetalleCompra DC;
    
    open cursorDetalleCompra;
    
    totalLoop: loop
        fetch cursorDetalleCompra into curCantidadCompra, curProductoId, curCompraId;
    
        if comId = curCompraId then
            set precio = (select P.precioCompra from Productos P where P.productoId = curProductoId);
            set cantidadComprada = curCantidadCompra;
            set totalCompra = totalCompra + (precio * cantidadComprada + (cantidadComprada * precio * 0.12));
        end if;
    
        if i = (select count(*) from DetalleCompra) then
            leave totalLoop;
        end if;
    
        set i = i + 1;
    end loop totalLoop;
    
    call asignarTotalCompra(comId, totalCompra);
    
    return totalCompra;
end $$
delimiter ;

-- Función para aumentar el stock de un producto después de una compra
delimiter $$
create function aumentarStockCompra(prodId int) returns int deterministic
begin
    declare stockActual int default 0;
    declare cantidadComprada int default 0;
	
    select cantidadStock into stockActual from Productos where productoId = prodId limit 1;
    select cantidadCompra into cantidadComprada from DetalleCompra where productoId = prodId limit 1;
    
    set stockActual = stockActual + cantidadComprada;
    
    update Productos set cantidadStock = stockActual where productoId = prodId;
    
    return stockActual;
end $$
delimiter ;

-- Trigger para calcular el total de una compra después de insertar un detalle de compra
delimiter $$
create trigger calcularTotalCompraAfterInsert
after insert on DetalleCompra
for each row
begin
    declare totalCompra decimal(10,2);
    declare nuevoStock int;
    
    set totalCompra = calcularTotalCompra(new.compraId);
    set nuevoStock = aumentarStockCompra(new.productoId); 
end$$
delimiter ;

Delimiter $$
create procedure sp_EditarCompra(In comId int, In fec date, In tot decimal(10,2))
Begin
    Update Compras
        Set
            fechaCompra = fec,
            totalCompra = tot
                Where compraId = comId;
    -- Actualizar el stock de productos comprados en esta compra
    declare proId int;
    declare cant int;
    declare currentStock int;
    declare newStock int;
    declare cur1 cursor for select productoId, cantidadCompra from DetalleCompra where compraId = comId;
    open cur1;
    read_loop: loop
        fetch cur1 into proId, cant;
        if done then
            leave read_loop;
        end if;
        select cantidadStock into currentStock from Productos where productoId = proId;
        set newStock = currentStock - cant;
        update Productos
            set cantidadStock = newStock
            where productoId = proId;
    end loop;
    close cur1;
End$$
Delimiter ;

Delimiter $$
create procedure sp_EliminarCompra(In comId int)
Begin
    -- Actualizar el stock de productos asociados con esta compra
    declare proId int;
    declare cant int;
    declare currentStock int;
    declare newStock int;
    declare cur1 cursor for select productoId, cantidadCompra from DetalleCompra where compraId = comId;
    open cur1;
    read_loop: loop
        fetch cur1 into proId, cant;
        if done then
            leave read_loop;
        end if;
        select cantidadStock into currentStock from Productos where productoId = proId;
        set newStock = currentStock + cant;
        update Productos
            set cantidadStock = newStock
            where productoId = proId;
    end loop;
    close cur1;

    -- Eliminar la compra
    Delete from Compras
        Where compraId = comId;
End$$
Delimiter ;

Delimiter $$
create procedure sp_agregarDetalleCompra(In can int, In proId int, In comId int)
begin
    insert into DetalleCompra(cantidadCompra, productoId, compraId) values 
        (can, proId, comId);

    -- Actualizar el stock del producto
    declare currentStock int;
    declare newStock int;
    select cantidadStock into currentStock from Productos where productoId = proId;
    set newStock = currentStock + can;
    update Productos
        set cantidadStock = newStock
        where productoId = proId;
end$$
Delimiter ;

Delimiter $$
create procedure sp_modificarStockCompra(In proId int, In cantA int)
begin
    declare currentStock int;
    declare newStock int;
    select cantidadStock into currentStock from Productos where productoId = proId;
    set newStock = currentStock + cantA;
    update Productos
        set cantidadStock = newStock
        where productoId = proId;
end $$
Delimiter ;