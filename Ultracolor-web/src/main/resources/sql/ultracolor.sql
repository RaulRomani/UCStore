
-- Versión del servidor: 10.1.8-MariaDB
-- Versión de PHP: 5.5.30
/** NOTAS
  Campo null by default
**/

--
-- Base de datos: `ULTRACOLOR`
--
DROP DATABASE IF EXISTS ultracolor;
CREATE DATABASE IF NOT EXISTS `ultracolor` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ultracolor`;



-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `idCliente` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL, -- nombre o razon social
  `apelllido` varchar(100),
  `DNI` char(8), -- se registra con null, cuando es persona juridica
  `RUC` char(11),
  `direccion` varchar(100),
  `celular` varchar(20),
  `telefono` varchar(20),
  `sexo` varchar(10),
  `edad` varchar(3),

  `correo` varchar(50),
  `lugarNacimiento` varchar(100),
  `fechaNacimiento` date,
  `categoria` varchar(10), -- general, vip, impresion //Para sacar precio de gigantografia, solo puede ver/modificar el administrador
  `fechaCreacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '+ read, - update', 
  
  PRIMARY KEY (idCliente)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `personal`
--

CREATE TABLE `personal` (
  `idPersonal` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `cargo` varchar(40) NOT NULL,
  `DNI` char(8) NOT NULL,
  `especialidad` varchar(100),
  `correo` varchar(50),
  `direccion` varchar(100),
  `lugarNacimiento` varchar(200),
  `fechaNacimiento` date,
  `celular` varchar(20),
  `sexo` varchar(10),
  `edad` varchar(3),
  `fechaCreacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '+ read, - update', 

  PRIMARY KEY (idPersonal)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idPersonal` int(11) unsigned NOT NULL,

  `username` varchar(100) NOT NULL,
  `password` char(128) NOT NULL,
  `authority` varchar(50) NOT NULL DEFAULT 'ROLE_SALER', -- administrador, vendedor, cajero, cliente(web)
  `enabled` tinyint(1) DEFAULT '0', -- activo|baneado //en caso de web
  `nota` varchar(200),
  PRIMARY KEY (idUsuario),
  FOREIGN KEY (idPersonal) REFERENCES personal(idPersonal)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;





-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `idPago` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) unsigned NOT NULL,
  `idPersonal` int(11) unsigned NOT NULL,

  `descripcion` varchar(200),
  `monto` decimal(8,2) NOT NULL,
  `fechaHora` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'fecha automático tanto en inserciones pero NO en actualizaciones',

  `tipo` varchar(10) DEFAULT 'ADELANTO' NOT NULL, -- tipo: adelanto, sueldo   //Los adelandos van como egresos diario y los sueldos van como egresos mensuales

  PRIMARY KEY (idPago),
  FOREIGN KEY (idPersonal) REFERENCES personal(idPersonal),
  FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `idVenta` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) unsigned NOT NULL,
  `idCliente` int(11) unsigned NOT NULL,

  `subtotal` decimal(8,2) NOT NULL,  -- depende de si hay descuento
  `descuento` decimal(8,2) NOT NULL,  -- si no hay descuento : 0.00

  `total` decimal(8,2) NOT NULL,
  `fechaHora` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'En caso de crédito se actualiza la fecha al cancelar las cuotas',
  `formapago` varchar(20) DEFAULT 'CONTADO' NOT NULL,
  `comprobante` varchar(20) DEFAULT 'BOLETA' NOT NULL,
  `estado` varchar(20) DEFAULT 'CANCELADO' NOT NULL, -- cancelado, credito, espera: no cancelado, anulado(only rol administrador, por Mantenimiento)

  PRIMARY KEY (idVenta),
  FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario),
  FOREIGN KEY (idCliente) REFERENCES cliente(idCliente),
  CONSTRAINT chk_venta_formapago
	CHECK (formapago IN ('CONTADO', 'CRÉDITO')),  -- DEPOSITO, TARJETA (WEB)
  CONSTRAINT chk_venta_comprobante
	CHECK (comprobante IN ('BOLETA', 'FACTURA')) -- Recibos que se imprimen: GUIA DE REMICION, NOTA DE CREDITO
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `idCompra` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) unsigned NOT NULL,
  
  `total` decimal(8,2) NOT NULL,
  `fechaHora` datetime,
  -- `formapago` varchar(20) DEFAULT 'CONTADO' NOT NULL,
  `comprobante` varchar(20) DEFAULT 'BOLETA' NOT NULL,
  `estado` varchar(20) DEFAULT 'CANCELADO' NOT NULL, -- cancelado, espera: no cancelado, anulado(only rol administrador, por Mantenimiento)

  PRIMARY KEY (idCompra),
  FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario),
  CONSTRAINT chk_venta_formapago
  CHECK (formapago IN ('CONTADO', 'CRÉDITO')),  -- DEPOSITO, TARJETA (WEB)
  CONSTRAINT chk_venta_comprobante
  CHECK (comprobante IN ('BOLETA', 'FACTURA')) -- Recibos que se imprimen: GUIA DE REMICION, NOTA DE CREDITO
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `idProveedor` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `razonSocial` varchar(60) NOT NULL,
  `RUC` char(11) NOT NULL,

  `direccion` varchar(100),
  `telefono` varchar(20),
  `celular` varchar(20),
  `correo` varchar(30),
  `paginaWeb` varchar(30),
  `fechaCreacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '+ read, - update', 
  `Nota` varchar(200),
  
  PRIMARY KEY (idProveedor)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `pedidocompra`
--

CREATE TABLE `pedidocompra` (
  `idPedidocompra` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idCompra` int(11) unsigned NOT NULL,
  `idProveedor` int(11) unsigned NOT NULL,

  `fecha` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '+ read, - update', 
  
  PRIMARY KEY (idPedidocompra),
  FOREIGN KEY (idCompra) REFERENCES compra(idCompra),
  FOREIGN KEY (idProveedor) REFERENCES proveedor(idProveedor)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/*==============================================================*/
/* Table: categoria                                             */
/*==============================================================*/
create table categoria
(
   idCategoria int(11) unsigned NOT NULL AUTO_INCREMENT,
   nombre varchar(25) not null,
   descripcion varchar(150),
   primary key (idCategoria)
)ENGINE=INNODB;


-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `idProducto` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idCategoria` int(11) unsigned NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `precioVenta` decimal(8,2) NOT NULL, -- incluido IGV
  `unidadCompra` varchar(30),
  `unidadVenta` varchar(30),
  `marca` varchar(30),
  `descripcion` varchar(200),
  `fechaVencimiento` date,
  `stock` int(4) not null,
  `tipo` char(8) default "PRODUCTO" not null, --  producto o servicio

  PRIMARY KEY (idProducto),
  FOREIGN KEY (idCategoria) REFERENCES categoria(idCategoria)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `detallepedido`
--

CREATE TABLE `detallepedido` (
  `idDetallepedido` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idPedidocompra` int(11) unsigned NOT NULL,
  `idProducto` int(11) unsigned NOT NULL,

  `cantidad` int(4) unsigned NOT NULL,
  `precio` decimal(8,2) unsigned NOT NULL,
  `importe` decimal(8,2) unsigned NOT NULL,

  `recibido` boolean NOT NULL, -- true, false
  `fecha` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '+ read, - update', 
  
  PRIMARY KEY (idDetallepedido),
  FOREIGN KEY (idPedidocompra) REFERENCES pedidocompra(idPedidocompra),
  FOREIGN KEY (idProducto) REFERENCES producto(idProducto)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `productoventa`
--

CREATE TABLE `productoventa` (
  `idProductoVenta` int(11) unsigned NOT NULL AUTO_INCREMENT,  
  `idVenta` int(11) unsigned NOT NULL,
  `idProducto` int(11) unsigned NOT NULL,

  `cantidad` int(2) NOT NULL,
  `importe` decimal(8,2) NOT NULL,

  PRIMARY KEY (idProductoVenta),
  FOREIGN KEY (idVenta) REFERENCES venta(idVenta),
  FOREIGN KEY (idProducto) REFERENCES producto(idProducto)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `crédito`
--

CREATE TABLE `credito` (
  
  `idCuota` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `IdVenta` int(11) unsigned NOT NULL, 
  `totalcuotas` int(2) NOT NULL, -- Total de cuotas a pagar
  `cuotaspagado` int(2) NOT NULL, -- Cuotas que se ha pagado
  `fechaHora` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'fecha automático tanto en inserciones pero NO en actualizaciones',
  `plazo` varchar(100) NOT NULL,
  `inicial` decimal(8,2) NOT NULL,
  `importe` decimal(8,2) NOT NULL,

  PRIMARY KEY (idCuota),
  CONSTRAINT chk_cuota_plazo
	CHECK (plazo IN ('QUINCENAL', 'MENSUAL')),
  FOREIGN KEY (IdVenta) REFERENCES venta(IdVenta)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Volcado de datos para la tabla `personal`
--

INSERT INTO `personal` (`idPersonal`, `nombre`, `apellido`, `cargo`, `DNI`, `especialidad`, `direccion`, `lugarNacimiento`, `fechaNacimiento`, `celular`, `sexo`, `edad`, `fechaCreacion`) VALUES
(1, 'Raúl', 'Romaní Flores', 'Desarrollador', '47830392', 'Desarrollador', 'Jr. Ucayali 456', NULL, NULL, '', NULL, NULL, '2016-05-14 07:46:35');


--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO usuario VALUES(1,1,'romanidev@ultracolor.com','3ced251b218f26379a00f05c3a09747578d1c06c712a3ea5634f85e3ae6aa468b317fd1b1e2a36fff4df04f61f61abca1216843a484d72e10e238b746d261a0d','ROLE_ADMIN',1,null); -- password1
