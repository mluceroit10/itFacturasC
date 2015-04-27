-- MySQL dump 9.09
--
-- Host: localhost    Database: dbitfacturasc
---------------------------------------------------------
-- Server version	4.0.15-nt

--
-- Table structure for table `jdo_table`
--

DROP TABLE IF EXISTS jdo_table;
CREATE TABLE jdo_table (
  TABLE_ID int(11) NOT NULL default '0',
  NEXT_ID int(11) NOT NULL default '0',
  JAVA_NAME varchar(128) binary NOT NULL default '',
  TABLE_NAME varchar(64) binary NOT NULL default '',
  PRIMARY KEY  (TABLE_ID),
  UNIQUE KEY JAVA_NAME (JAVA_NAME),
  UNIQUE KEY TABLE_NAME (TABLE_NAME)
) TYPE=InnoDB;

--
-- Dumping data for table `jdo_table`
--


/*!40000 ALTER TABLE jdo_table DISABLE KEYS */;
LOCK TABLES jdo_table WRITE;
INSERT INTO jdo_table VALUES (1,1,'persistencia.domain.Oid','OID'),(2,2,'persistencia.domain.Localidad','LOCALIDAD'),(3,1,'persistencia.domain.Provincia','PROVINCIA'),(4,1,'persistencia.domain.Comercio','COMERCIO'),(7,6,'persistencia.domain.ItemFactura','ITEM_FACTURA'),(8,0,'persistencia.domain.Factura','FACTURA'),(9,1,'persistencia.domain.PlanillaES','PLANILLA_ES'),(10,8,'persistencia.domain.MovimientoCaja','MOVIMIENTO_CAJA'),(11,3,'persistencia.domain.Cliente','CLIENTE'),(13,2,'persistencia.domain.Cheque','CHEQUE'),(14,3,'persistencia.domain.NotaDebito','NOTA_DEBITO'),(16,6,'persistencia.domain.FacturaCliente','FACTURA_CLIENTE');
UNLOCK TABLES;
/*!40000 ALTER TABLE jdo_table ENABLE KEYS */;

--
-- Table structure for table `oid`
--

DROP TABLE IF EXISTS oid;
CREATE TABLE oid (
  OID_ID bigint(20) NOT NULL default '0',
  OID bigint(20) default NULL,
  PRIMARY KEY  (OID_ID)
) TYPE=InnoDB;

--
-- Dumping data for table `oid`
--


/*!40000 ALTER TABLE oid DISABLE KEYS */;
LOCK TABLES oid WRITE;
INSERT INTO oid VALUES (35184372088832,160);
UNLOCK TABLES;
/*!40000 ALTER TABLE oid ENABLE KEYS */;

--
-- Table structure for table `provincia`
--

DROP TABLE IF EXISTS provincia;
CREATE TABLE provincia (
  PROVINCIA_ID bigint(20) NOT NULL default '0',
  ID bigint(20) default NULL,
  NOMBRE varchar(20) binary default NULL,
  PRIMARY KEY  (PROVINCIA_ID)
) TYPE=InnoDB;

--
-- Dumping data for table `provincia`
--


/*!40000 ALTER TABLE provincia DISABLE KEYS */;
LOCK TABLES provincia WRITE;
INSERT INTO provincia VALUES (105553116266496,131073,'Córdoba');
UNLOCK TABLES;
/*!40000 ALTER TABLE provincia ENABLE KEYS */;

--
-- Table structure for table `localidad`
--

DROP TABLE IF EXISTS localidad;
CREATE TABLE localidad (
  LOCALIDAD_ID bigint(20) NOT NULL default '0',
  COD_POSTAL int(11) NOT NULL default '0',
  ID bigint(20) default NULL,
  NOMBRE varchar(255) binary default NULL,
  PROVINCIA_ID bigint(20) default NULL,
  PRIMARY KEY  (LOCALIDAD_ID),
  KEY LOCALIDAD_N1 (PROVINCIA_ID),
  CONSTRAINT `0_2893` FOREIGN KEY (`PROVINCIA_ID`) REFERENCES `provincia` (`PROVINCIA_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `localidad`
--


/*!40000 ALTER TABLE localidad DISABLE KEYS */;
LOCK TABLES localidad WRITE;
INSERT INTO localidad VALUES (70368744177664,5800,131075,'Río Cuarto',105553116266496);
UNLOCK TABLES;
/*!40000 ALTER TABLE localidad ENABLE KEYS */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS cliente;
CREATE TABLE cliente (
  CLIENTE_ID bigint(20) NOT NULL default '0',
  CODIGO bigint(20) default NULL,
  CUIT varchar(255) binary default NULL,
  DEUDA double NOT NULL default '0',
  DIRECCION varchar(255) binary default NULL,
  ELIMINADO tinyint(1) NOT NULL default '0',
  ID bigint(20) default NULL,
  ING_BRUTOS_CL varchar(255) binary default NULL,
  IVA_CL varchar(255) binary default NULL,
  LOCALIDAD_ID bigint(20) default NULL,
  NOMBRE varchar(255) binary default NULL,
  TELEFONO varchar(255) binary default NULL,
  PRIMARY KEY  (CLIENTE_ID),
  KEY CLIENTE_N1 (LOCALIDAD_ID),
  CONSTRAINT `0_2897` FOREIGN KEY (`LOCALIDAD_ID`) REFERENCES `localidad` (`LOCALIDAD_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `cliente`
--


/*!40000 ALTER TABLE cliente DISABLE KEYS */;
LOCK TABLES cliente WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE cliente ENABLE KEYS */;

--
-- Table structure for table `comercio`
--

DROP TABLE IF EXISTS comercio;
CREATE TABLE comercio (
  COMERCIO_ID bigint(20) NOT NULL default '0',
  CATEGORIA varchar(255) binary default NULL,
  CUIT varchar(255) binary default NULL,
  DIRECCION varchar(255) binary default NULL,
  ID bigint(20) default NULL,
  INICIO_ACT date default NULL,
  LOCALIDAD_ID bigint(20) default NULL,
  NOMBRE varchar(255) binary default NULL,
  NRO_FACT_C bigint(20) default NULL,
  NRO_NOTA_DEBITO bigint(20) default NULL,
  NRO_REMITO bigint(20) default NULL,
  NRO_RECIBO bigint(20) default NULL,
  TELEFONO varchar(255) binary default NULL,
  PRIMARY KEY  (COMERCIO_ID),
  KEY COMERCIO_N1 (LOCALIDAD_ID),
  CONSTRAINT `0_2895` FOREIGN KEY (`LOCALIDAD_ID`) REFERENCES `localidad` (`LOCALIDAD_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `comercio`
--


/*!40000 ALTER TABLE comercio DISABLE KEYS */;
LOCK TABLES comercio WRITE;
INSERT INTO comercio VALUES (140737488355328,'','27-30310394-0','Saavedra 480 Planta Alta B',131077,'2011-05-23',70368744177664,'Radio Fm Libre 105.5',1,1,0,1,'(0358) 4655211 / 4642419');
UNLOCK TABLES;
/*!40000 ALTER TABLE comercio ENABLE KEYS */;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS factura;
CREATE TABLE factura (
  FACTURA_ID bigint(20) NOT NULL default '0',
  ANULADA tinyint(1) NOT NULL default '0',
  ID bigint(20) default NULL,
  IMPORTE_TOTAL double NOT NULL default '0',
  NRO_FACTURA bigint(20) default NULL,
  PERIODO varchar(255) binary default NULL,
  TIPO_FACTURA varchar(255) binary default NULL,
  CLIENTE_ID bigint(20) default NULL,
  PRIMARY KEY  (FACTURA_ID),
  KEY FACTURA_CLIENTE_N2 (CLIENTE_ID),
  CONSTRAINT `0_2905` FOREIGN KEY (`CLIENTE_ID`) REFERENCES `cliente` (`CLIENTE_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `factura`
--


/*!40000 ALTER TABLE factura DISABLE KEYS */;
LOCK TABLES factura WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE factura ENABLE KEYS */;

--
-- Table structure for table `factura_cliente`
--

DROP TABLE IF EXISTS factura_cliente;
CREATE TABLE factura_cliente (
  FACTURA_CLIENTE_ID bigint(20) NOT NULL default '0',
  COND_VENTA varchar(255) binary default NULL,
  FECHA_IMPRESION date default NULL,
  FECHA_PAGO date default NULL,
  IMPORTE_ABONADO double NOT NULL default '0',
  INGR_BRUTOS varchar(255) binary default NULL,
  IVA_F varchar(255) binary default NULL,
  REMITO_NRO varchar(255) binary default NULL,
  PRIMARY KEY  (FACTURA_CLIENTE_ID)
) TYPE=MyISAM;

--
-- Dumping data for table `factura_cliente`
--


/*!40000 ALTER TABLE factura_cliente DISABLE KEYS */;
LOCK TABLES factura_cliente WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE factura_cliente ENABLE KEYS */;

--
-- Table structure for table `nota_debito`
--

DROP TABLE IF EXISTS nota_debito;
CREATE TABLE nota_debito (
  NOTA_DEBITO_ID bigint(20) NOT NULL default '0',
  FECHA date default NULL,
  ID_FACTURA_NC bigint(20) default NULL,
  NRO_FACTURA_NC bigint(20) default NULL,
  OBSERVACIONES varchar(255) binary default NULL,
  TIPO_FACTURA_NC varchar(255) binary default NULL,
  PRIMARY KEY  (NOTA_DEBITO_ID),
  CONSTRAINT `0_2907` FOREIGN KEY (`NOTA_DEBITO_ID`) REFERENCES `factura` (`FACTURA_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `nota_debito`
--


/*!40000 ALTER TABLE nota_debito DISABLE KEYS */;
LOCK TABLES nota_debito WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE nota_debito ENABLE KEYS */;

--
-- Table structure for table `planilla_es`
--

DROP TABLE IF EXISTS planilla_es;
CREATE TABLE planilla_es (
  PLANILLA_ES_ID bigint(20) NOT NULL default '0',
  FECHA_P date default NULL,
  ID bigint(20) default NULL,
  NRO_PLANILLA int(11) NOT NULL default '0',
  PERIODO varchar(255) binary default NULL,
  SALDO double NOT NULL default '0',
  PRIMARY KEY  (PLANILLA_ES_ID)
) TYPE=InnoDB;

--
-- Dumping data for table `planilla_es`
--


/*!40000 ALTER TABLE planilla_es DISABLE KEYS */;
LOCK TABLES planilla_es WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE planilla_es ENABLE KEYS */;

--
-- Table structure for table `movimiento_caja`
--

DROP TABLE IF EXISTS movimiento_caja;
CREATE TABLE movimiento_caja (
  MOVIMIENTO_CAJA_ID bigint(20) NOT NULL default '0',
  CODIGO int(11) NOT NULL default '0',
  CON_FACTURA tinyint(1) NOT NULL default '0',
  DESCRIPCION varchar(255) binary default NULL,
  FACTURA_ID bigint(20) default NULL,
  FECHA_MC date default NULL,
  FORMA_PAGO varchar(255) binary default NULL,
  ID bigint(20) default NULL,
  IMPORTE double NOT NULL default '0',
  NRO_CHEQUE bigint(20) default NULL,
  PERIODO varchar(255) binary default NULL,
  PLANILLA_ID bigint(20) default NULL,
  TIPO_FACTURA varchar(255) binary default NULL,
  TIPO_MOVIMIENTO int(11) NOT NULL default '0',
  NRO_RECIBO bigint(20) default NULL,
  PRIMARY KEY  (MOVIMIENTO_CAJA_ID),
  KEY MOVIMIENTO_CAJA_N1 (PLANILLA_ID),
  KEY MOVIMIENTO_CAJA_N2 (FACTURA_ID),
  CONSTRAINT `0_2899` FOREIGN KEY (`PLANILLA_ID`) REFERENCES `planilla_es` (`PLANILLA_ES_ID`),
  CONSTRAINT `0_2901` FOREIGN KEY (`FACTURA_ID`) REFERENCES `factura` (`FACTURA_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `movimiento_caja`
--


/*!40000 ALTER TABLE movimiento_caja DISABLE KEYS */;
LOCK TABLES movimiento_caja WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE movimiento_caja ENABLE KEYS */;

--
-- Table structure for table `item_factura`
--

DROP TABLE IF EXISTS item_factura;
CREATE TABLE item_factura (
  ITEM_FACTURA_ID bigint(20) NOT NULL default '0',
  CANTIDAD double NOT NULL default '0',
  FACTURA_ID bigint(20) default NULL,
  ID bigint(20) default NULL,
  PR_TOTAL double NOT NULL default '0',
  PR_UNIT double NOT NULL default '0',
  PRODUCTO varchar(255) default NULL,
  PRIMARY KEY  (ITEM_FACTURA_ID),
  KEY ITEM_FACTURA_N1 (FACTURA_ID),
  CONSTRAINT `0_2903` FOREIGN KEY (`FACTURA_ID`) REFERENCES `factura` (`FACTURA_ID`)
) TYPE=InnoDB;

--
-- Dumping data for table `item_factura`
--


/*!40000 ALTER TABLE item_factura DISABLE KEYS */;
LOCK TABLES item_factura WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE item_factura ENABLE KEYS */;

--
-- Table structure for table `cheque`
--

DROP TABLE IF EXISTS cheque;
CREATE TABLE cheque (
  CHEQUE_ID bigint(20) NOT NULL default '0',
  FECHA_EMISION date default NULL,
  FECHA_VTO date default NULL,
  ID bigint(20) default NULL,
  BANCO varchar(255) binary default NULL,
  NUMERO bigint(20) default NULL,
  LOC_PLAZA varchar(255) binary default NULL,
  IMPORTE double NOT NULL default '0',
  QUIEN_ENTREGA varchar(255) binary default NULL,
  ESTADO varchar(255) binary default NULL,
  A_QUIEN_REMITE varchar(255) binary default NULL,
  PERIODO varchar(255) binary default NULL,
  PRIMARY KEY  (CHEQUE_ID)
) TYPE=InnoDB;

--
-- Dumping data for table `cheque`
--


/*!40000 ALTER TABLE cheque DISABLE KEYS */;
LOCK TABLES cheque WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE cheque ENABLE KEYS */;

