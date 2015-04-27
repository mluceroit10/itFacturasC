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
INSERT INTO jdo_table VALUES (1,1,'persistencia.domain.Oid','OID'),(2,3,'persistencia.domain.Localidad','LOCALIDAD'),(3,11,'persistencia.domain.Provincia','PROVINCIA'),(4,1,'persistencia.domain.Comercio','COMERCIO'),(7,9,'persistencia.domain.ItemFactura','ITEM_FACTURA'),(8,0,'persistencia.domain.Factura','FACTURA'),(9,5,'persistencia.domain.PlanillaES','PLANILLA_ES'),(10,11,'persistencia.domain.MovimientoCaja','MOVIMIENTO_CAJA'),(11,5,'persistencia.domain.Cliente','CLIENTE'),(13,2,'persistencia.domain.Cheque','CHEQUE'),(14,5,'persistencia.domain.NotaDebito','NOTA_DEBITO'),(16,8,'persistencia.domain.FacturaCliente','FACTURA_CLIENTE');
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
INSERT INTO oid VALUES (35184372088832,197);
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
INSERT INTO provincia VALUES (105553116266496,131073,'Córdoba'),(105553116299264,11141130,'Buenos Aires'),(105553116594176,12910601,'Santa Fe');
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
INSERT INTO localidad VALUES (70368744177664,5800,131075,'Río Cuarto',105553116266496),(70368744243200,5800,12910606,'Córdoba Capital',105553116266496),(70368744243201,2301,12910608,'Ramona',105553116594176),(70368744243202,0,12910610,'Rosario',105553116594176),(70368744243203,5800,12910613,'Higueras',105553116266496);
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
INSERT INTO cliente VALUES (387028093075456,1,'',-1091,'We',0,10616845,'','Consumidor Final',70368744177664,'We','we'),(387028093108224,2,'',33,'Rt',0,11272199,'','Consumidor Final',70368744177664,'Try','');
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
INSERT INTO comercio VALUES (140737488355328,'','27-30310394-0','Saavedra 480 Planta Alta B',131077,'2011-05-23',70368744177664,'Radio Fm Libre 105.5',6,5,0,7,'(0358) 4655211 / 4642419');
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
INSERT INTO factura VALUES (492581209341952,0,11665440,234,1,'11-2014','NotaDebito',387028093075456),(492581209341953,0,11665459,345,2,'11-2014','NotaDebito',387028093075456),(492581209374720,0,12255311,34,3,'12-2014','NotaDebito',387028093075456),(492581209374721,0,12255331,33,4,'12-2014','NotaDebito',387028093108224),(562949953617920,0,10616849,467,1,'11-2014','FacturaCliente-C',387028093075456),(562949953650688,0,12255268,544555,2,'12-2014','FacturaCliente-C',387028093108224),(562949953650689,0,12255285,4545,3,'12-2014','FacturaCliente-C',387028093075456),(562949953650690,0,12255302,4545,4,'12-2014','FacturaCliente-C',387028093075456),(562949953650691,0,12255362,5000000,5,'12-2014','FacturaCliente-C',387028093075456);
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
INSERT INTO factura_cliente VALUES (562949953617920,'CONTADO','2014-11-25','2014-11-27',2171,'','Consumidor Final',''),(562949953650688,'CONTADO','2014-12-01','2014-12-01',544555,'','Consumidor Final',''),(562949953650689,'CONTADO','2014-12-01','2014-12-01',4545,'','Consumidor Final',''),(562949953650690,'CONTADO','2014-12-05','2014-12-05',4545,'','Consumidor Final',''),(562949953650691,'CONTADO','2014-12-01','2014-12-01',5000000,'','Consumidor Final','');
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
INSERT INTO nota_debito VALUES (492581209341952,'2014-11-26',10616849,1,'','C'),(492581209341953,'2014-11-26',10616849,1,'','C'),(492581209374720,'2014-12-01',12255302,4,'','C'),(492581209374721,'2014-12-01',12255268,2,'','C');
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
INSERT INTO planilla_es VALUES (316659348930560,'2014-11-26',11468817,1,'2014',701);
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
INSERT INTO movimiento_caja VALUES (351843721150464,1,1,'pago contado',562949953617920,'2014-11-25','EFECTIVO',10616854,467,NULL,'11-2014',316659348930560,'Factura Cliente-Tipo C',1,NULL),(351843721150465,2,1,'pago mes octubre',562949953617920,'2014-11-25','EFECTIVO',10616862,234,NULL,'11-2014',316659348930560,'Factura Cliente-Tipo C',1,1),(351843721183232,3,1,'6767',562949953617920,'2014-11-27','EFECTIVO',11862038,356,NULL,'11-2014',NULL,'Factura Cliente-Tipo C',1,2),(351843721183233,4,1,'356',562949953617920,'2014-11-27','EFECTIVO',11862059,536,NULL,'11-2014',NULL,'Factura Cliente-Tipo C',1,3),(351843721183234,5,1,'455555',562949953617920,'2014-11-27','EFECTIVO',11862089,455,NULL,'11-2014',NULL,'Factura Cliente-Tipo C',1,4),(351843721183235,6,1,'56',562949953617920,'2014-11-27','EFECTIVO',11862106,56,NULL,'11-2014',NULL,'Factura Cliente-Tipo C',1,5),(351843721183236,7,1,'67',562949953617920,'2014-11-27','EFECTIVO',11862126,67,NULL,'11-2014',NULL,'Factura Cliente-Tipo C',1,6),(351843721216000,8,1,'pago contado',562949953650688,'2014-12-01','EFECTIVO',12255273,544555,NULL,'12-2014',NULL,'Factura Cliente-Tipo C',1,NULL),(351843721216001,9,1,'pago contado',562949953650689,'2014-12-01','EFECTIVO',12255289,4545,NULL,'12-2014',NULL,'Factura Cliente-Tipo C',1,NULL),(351843721216002,10,1,'pago contado',562949953650690,'2014-12-05','EFECTIVO',12255306,4545,NULL,'12-2014',NULL,'Factura Cliente-Tipo C',1,NULL),(351843721216003,11,1,'pago contado',562949953650691,'2014-12-01','EFECTIVO',12255368,5000000,NULL,'12-2014',NULL,'Factura Cliente-Tipo C',1,NULL);
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
INSERT INTO item_factura VALUES (246290604818432,1,562949953617920,10616851,233,233,'weeeeeeeeeeeeeee'),(246290604818433,1,562949953617920,10616852,234,234,'22222222224'),(246290604851200,1,492581209341952,11665442,234,234,'fhgfhg'),(246290604851201,1,492581209341953,11665461,345,345,'ghfhfgh'),(246290604883968,1,562949953650688,12255270,544555,544555,'fhfghfgh'),(246290604883969,1,562949953650689,12255287,4545,4545,'4545'),(246290604883970,1,562949953650690,12255304,4545,4545,'454545'),(246290604883971,1,492581209374720,12255313,34,34,'444'),(246290604883972,1,492581209374721,12255334,33,33,'333'),(246290604883973,1,562949953650691,12255365,5000000,5000000,'hjhg');
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

