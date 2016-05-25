-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-05-2016 a las 00:51:31
-- Versión del servidor: 10.1.8-MariaDB
-- Versión de PHP: 5.5.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ultracolor`
--
CREATE DATABASE IF NOT EXISTS `ultracolor` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ultracolor`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `idCategoria` int(11) UNSIGNED NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idCategoria`, `nombre`, `descripcion`) VALUES
(1, 'Tintas', 'todo tipo de tintas para impresora');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `idCliente` int(11) UNSIGNED NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apelllido` varchar(100) DEFAULT NULL,
  `DNI` char(8) DEFAULT NULL,
  `RUC` char(11) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `celular` varchar(20) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `sexo` varchar(10) DEFAULT NULL,
  `edad` varchar(3) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `lugarNacimiento` varchar(100) DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `religion` varchar(50) DEFAULT NULL,
  `tipo` varchar(10) DEFAULT NULL,
  `categoria` varchar(10) DEFAULT NULL,
  `fechaCreacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '+ read, - update'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idCliente`, `nombre`, `apelllido`, `DNI`, `RUC`, `direccion`, `celular`, `telefono`, `sexo`, `edad`, `correo`, `lugarNacimiento`, `fechaNacimiento`, `religion`, `tipo`, `categoria`, `fechaCreacion`) VALUES
(1, 'Luis Angel', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '2016-05-15 05:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `idCompra` int(11) UNSIGNED NOT NULL,
  `idUsuario` int(11) UNSIGNED NOT NULL,
  `total` decimal(8,2) NOT NULL,
  `fechaHora` datetime DEFAULT NULL,
  `comprobante` varchar(20) NOT NULL DEFAULT 'BOLETA',
  `estado` varchar(20) NOT NULL DEFAULT 'CANCELADO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credito`
--

CREATE TABLE `credito` (
  `idCuota` int(11) UNSIGNED NOT NULL,
  `IdVenta` int(11) UNSIGNED NOT NULL,
  `totalcuotas` int(2) NOT NULL,
  `cuotaspagado` int(2) NOT NULL,
  `fechaHora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'fecha automático tanto en inserciones pero NO en actualizaciones',
  `plazo` varchar(100) NOT NULL,
  `inicial` decimal(8,2) NOT NULL,
  `importe` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `credito`
--

INSERT INTO `credito` (`idCuota`, `IdVenta`, `totalcuotas`, `cuotaspagado`, `fechaHora`, `plazo`, `inicial`, `importe`) VALUES
(1, 2, 2, 0, '2016-05-16 00:07:44', 'QUINCENAL', '5.00', '2.50'),
(2, 5, 2, 0, '2016-05-23 21:52:25', 'MENSUAL', '0.00', '5.00'),
(3, 7, 3, 2, '2016-05-23 22:06:56', 'MENSUAL', '30.00', '26.67');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallepedido`
--

CREATE TABLE `detallepedido` (
  `idDetallepedido` int(11) UNSIGNED NOT NULL,
  `idPedidocompra` int(11) UNSIGNED NOT NULL,
  `idProducto` int(11) UNSIGNED NOT NULL,
  `cantidad` int(4) UNSIGNED NOT NULL,
  `precio` decimal(8,2) UNSIGNED NOT NULL,
  `importe` decimal(8,2) UNSIGNED NOT NULL,
  `recibido` tinyint(1) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '+ read, - update'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `idPago` int(11) UNSIGNED NOT NULL,
  `idUsuario` int(11) UNSIGNED NOT NULL,
  `idPersonal` int(11) UNSIGNED NOT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  `monto` decimal(8,2) NOT NULL,
  `fechaHora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'fecha automático tanto en inserciones pero NO en actualizaciones',
  `tipo` varchar(10) NOT NULL DEFAULT 'ADELANTO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidocompra`
--

CREATE TABLE `pedidocompra` (
  `idPedidocompra` int(11) UNSIGNED NOT NULL,
  `idCompra` int(11) UNSIGNED NOT NULL,
  `idProveedor` int(11) UNSIGNED NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '+ read, - update'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal`
--

CREATE TABLE `personal` (
  `idPersonal` int(11) UNSIGNED NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `cargo` varchar(40) NOT NULL,
  `DNI` char(8) NOT NULL,
  `especialidad` varchar(100) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `lugarNacimiento` varchar(200) DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `celular` varchar(20) DEFAULT NULL,
  `sexo` varchar(10) DEFAULT NULL,
  `edad` varchar(3) DEFAULT NULL,
  `fechaCreacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '+ read, - update'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `personal`
--

INSERT INTO `personal` (`idPersonal`, `nombre`, `apellido`, `cargo`, `DNI`, `especialidad`, `correo`, `direccion`, `lugarNacimiento`, `fechaNacimiento`, `celular`, `sexo`, `edad`, `fechaCreacion`) VALUES
(1, 'Raúl', 'Romaní Flores', 'Desarrollador', '47830392', 'Desarrollador', NULL, 'Jr. Ucayali 456', NULL, NULL, '', NULL, NULL, '2016-05-14 12:46:35');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `idProducto` int(11) UNSIGNED NOT NULL,
  `idCategoria` int(11) UNSIGNED NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `precioVenta` decimal(8,2) NOT NULL,
  `unidadCompra` varchar(30) DEFAULT NULL,
  `unidadVenta` varchar(30) DEFAULT NULL,
  `marca` varchar(30) DEFAULT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  `fechaVencimiento` date DEFAULT NULL,
  `stock` int(4) NOT NULL,
  `tipo` char(8) NOT NULL DEFAULT 'PRODUCTO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`idProducto`, `idCategoria`, `nombre`, `precioVenta`, `unidadCompra`, `unidadVenta`, `marca`, `descripcion`, `fechaVencimiento`, `stock`, `tipo`) VALUES
(1, 1, 'Tinta color negro - canon', '10.00', '', '', '', '', '2017-06-06', 0, 'PRODUCTO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productoventa`
--

CREATE TABLE `productoventa` (
  `idProductoVenta` int(11) UNSIGNED NOT NULL,
  `idVenta` int(11) UNSIGNED NOT NULL,
  `idProducto` int(11) UNSIGNED NOT NULL,
  `cantidad` int(2) NOT NULL,
  `importe` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `productoventa`
--

INSERT INTO `productoventa` (`idProductoVenta`, `idVenta`, `idProducto`, `cantidad`, `importe`) VALUES
(1, 1, 1, 2, '20.00'),
(2, 2, 1, 1, '10.00'),
(3, 3, 1, 1, '10.00'),
(4, 4, 1, 3, '30.00'),
(5, 5, 1, 1, '10.00'),
(6, 6, 1, 11, '110.00'),
(7, 7, 1, 11, '110.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `idProveedor` int(11) UNSIGNED NOT NULL,
  `razonSocial` varchar(60) NOT NULL,
  `RUC` char(11) NOT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `celular` varchar(20) DEFAULT NULL,
  `correo` varchar(30) DEFAULT NULL,
  `paginaWeb` varchar(30) DEFAULT NULL,
  `fechaCreacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '+ read, - update',
  `Nota` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) UNSIGNED NOT NULL,
  `idPersonal` int(11) UNSIGNED NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `clave` varchar(50) NOT NULL,
  `rol` varchar(50) NOT NULL,
  `estado` varchar(50) NOT NULL,
  `nota` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `idPersonal`, `usuario`, `clave`, `rol`, `estado`, `nota`) VALUES
(1, 1, 'admin', 'admin', 'ADMINISTRADOR', 'ACTIVO', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `idVenta` int(11) UNSIGNED NOT NULL,
  `idUsuario` int(11) UNSIGNED NOT NULL,
  `idCliente` int(11) UNSIGNED NOT NULL,
  `subtotal` decimal(8,2) NOT NULL,
  `descuento` decimal(8,2) NOT NULL,
  `total` decimal(8,2) NOT NULL,
  `fechaHora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'En caso de crédito se actualiza la fecha al cancelar las cuotas',
  `formapago` varchar(20) NOT NULL DEFAULT 'CONTADO',
  `comprobante` varchar(20) NOT NULL DEFAULT 'BOLETA',
  `estado` varchar(20) NOT NULL DEFAULT 'CANCELADO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`idVenta`, `idUsuario`, `idCliente`, `subtotal`, `descuento`, `total`, `fechaHora`, `formapago`, `comprobante`, `estado`) VALUES
(1, 1, 1, '20.00', '0.00', '20.00', '2016-05-15 20:21:31', 'CONTADO', 'BOLETA', 'CANCELADO'),
(2, 1, 1, '10.00', '0.00', '10.00', '2016-05-16 00:07:44', 'CUOTAS', '', 'CREDITO'),
(3, 1, 1, '10.00', '0.00', '10.00', '2016-05-23 21:32:57', 'CONTADO', 'BOLETA', 'CANCELADO'),
(4, 1, 1, '30.00', '0.00', '30.00', '2016-05-23 21:40:43', 'CONTADO', 'FACTURA', 'CANCELADO'),
(5, 1, 1, '10.00', '0.00', '10.00', '2016-05-23 21:52:25', 'CUOTAS', '', 'CREDITO'),
(6, 1, 1, '110.00', '0.00', '110.00', '2016-05-23 22:05:41', 'CONTADO', 'BOLETA', 'CANCELADO'),
(7, 1, 1, '110.00', '0.00', '110.00', '2016-05-23 22:06:56', 'CUOTAS', '', 'CREDITO');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idCategoria`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idCliente`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`idCompra`),
  ADD KEY `idUsuario` (`idUsuario`);

--
-- Indices de la tabla `credito`
--
ALTER TABLE `credito`
  ADD PRIMARY KEY (`idCuota`),
  ADD KEY `IdVenta` (`IdVenta`);

--
-- Indices de la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  ADD PRIMARY KEY (`idDetallepedido`),
  ADD KEY `idPedidocompra` (`idPedidocompra`),
  ADD KEY `idProducto` (`idProducto`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`idPago`),
  ADD KEY `idPersonal` (`idPersonal`),
  ADD KEY `idUsuario` (`idUsuario`);

--
-- Indices de la tabla `pedidocompra`
--
ALTER TABLE `pedidocompra`
  ADD PRIMARY KEY (`idPedidocompra`),
  ADD KEY `idCompra` (`idCompra`),
  ADD KEY `idProveedor` (`idProveedor`);

--
-- Indices de la tabla `personal`
--
ALTER TABLE `personal`
  ADD PRIMARY KEY (`idPersonal`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idProducto`),
  ADD KEY `idCategoria` (`idCategoria`);

--
-- Indices de la tabla `productoventa`
--
ALTER TABLE `productoventa`
  ADD PRIMARY KEY (`idProductoVenta`),
  ADD KEY `idVenta` (`idVenta`),
  ADD KEY `idProducto` (`idProducto`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`idProveedor`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD KEY `idPersonal` (`idPersonal`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`idVenta`),
  ADD KEY `idUsuario` (`idUsuario`),
  ADD KEY `idCliente` (`idCliente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idCategoria` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idCliente` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `idCompra` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `credito`
--
ALTER TABLE `credito`
  MODIFY `idCuota` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  MODIFY `idDetallepedido` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `idPago` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `pedidocompra`
--
ALTER TABLE `pedidocompra`
  MODIFY `idPedidocompra` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `personal`
--
ALTER TABLE `personal`
  MODIFY `idPersonal` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `idProducto` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `productoventa`
--
ALTER TABLE `productoventa`
  MODIFY `idProductoVenta` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `idProveedor` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `idVenta` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `compra_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`);

--
-- Filtros para la tabla `credito`
--
ALTER TABLE `credito`
  ADD CONSTRAINT `credito_ibfk_1` FOREIGN KEY (`IdVenta`) REFERENCES `venta` (`idVenta`);

--
-- Filtros para la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  ADD CONSTRAINT `detallepedido_ibfk_1` FOREIGN KEY (`idPedidocompra`) REFERENCES `pedidocompra` (`idPedidocompra`),
  ADD CONSTRAINT `detallepedido_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `pago_ibfk_1` FOREIGN KEY (`idPersonal`) REFERENCES `personal` (`idPersonal`),
  ADD CONSTRAINT `pago_ibfk_2` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`);

--
-- Filtros para la tabla `pedidocompra`
--
ALTER TABLE `pedidocompra`
  ADD CONSTRAINT `pedidocompra_ibfk_1` FOREIGN KEY (`idCompra`) REFERENCES `compra` (`idCompra`),
  ADD CONSTRAINT `pedidocompra_ibfk_2` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`idProveedor`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idCategoria`);

--
-- Filtros para la tabla `productoventa`
--
ALTER TABLE `productoventa`
  ADD CONSTRAINT `productoventa_ibfk_1` FOREIGN KEY (`idVenta`) REFERENCES `venta` (`idVenta`),
  ADD CONSTRAINT `productoventa_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`idPersonal`) REFERENCES `personal` (`idPersonal`);

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  ADD CONSTRAINT `venta_ibfk_2` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
