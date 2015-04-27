-- MySQL dump 9.09
--
-- Host: localhost    Database: dbitcomerciosred
---------------------------------------------------------
-- Server version	4.0.15-nt

--
-- Table structure for table `jdo_table`
--

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
INSERT INTO jdo_table VALUES (1,1,'persistencia.domain.Oid','OID'),
(2,1,'persistencia.domain.Localidad','LOCALIDAD'),
(3,1,'persistencia.domain.Provincia','PROVINCIA'),
(4,1,'persistencia.domain.Comercio','COMERCIO'),
(7,0,'persistencia.domain.ItemFactura','ITEM_FACTURA'),
(8,0,'persistencia.domain.Factura','FACTURA'),
(9,0,'persistencia.domain.PlanillaES','PLANILLA_ES'),
(10,0,'persistencia.domain.MovimientoCaja','MOVIMIENTO_CAJA'),
(11,0,'persistencia.domain.Cliente','CLIENTE'),
(13,0,'persistencia.domain.Cheque','CHEQUE'),
(14,0,'persistencia.domain.NotaCredito','NOTA_CREDITO'),
(16,0,'persistencia.domain.FacturaCliente','FACTURA_CLIENTE');
UNLOCK TABLES;
/*!40000 ALTER TABLE jdo_table ENABLE KEYS */;

--
-- Table structure for table `oid`
--


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
INSERT INTO oid VALUES (35184372088832,4);
UNLOCK TABLES;
/*!40000 ALTER TABLE oid ENABLE KEYS */;

--
-- Table structure for table `provincia`
--

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
INSERT INTO provincia VALUES (105553116266496,131073,'Córdoba'),(105553116266497,131074,'Buenos Aires'),(105553116266498,131075,'Santa Fe');
UNLOCK TABLES;
/*!40000 ALTER TABLE provincia ENABLE KEYS */;

--
-- Table structure for table `localidad`
--

CREATE TABLE localidad (
  LOCALIDAD_ID bigint(20) NOT NULL default '0',
  COD_POSTAL int(11) NOT NULL default '0',
  ID bigint(20) default NULL,
  NOMBRE varchar(255) binary default NULL,
  PROVINCIA_ID bigint(20) default NULL,
  PRIMARY KEY  (LOCALIDAD_ID),
  KEY LOCALIDAD_N1 (PROVINCIA_ID)
) TYPE=InnoDB;

--
-- Dumping data for table `localidad`
--


/*!40000 ALTER TABLE localidad DISABLE KEYS */;
LOCK TABLES localidad WRITE;
INSERT INTO localidad VALUES
       (70368744177664,5800,131077,'Rio Cuarto',105553116266496),
       (70368744177665,5847,131078,'Coronel Moldes',105553116266496),
       (70368744210432,0,790019,'Vicuña Mackenna',105553116266496),
       (70368744243200,0,1012519,'Brinkmann',105553116266496),
       (70368744243201,0,1012604,'Rosario',105553116266498),
       (70368744275968,5800,2755243,'Cordoba Capital',105553116266496),
       (70368744308736,5800,3475091,'Higueras',105553116266496),
       (70368744308737,1440,3486957,'Capital Federal',105553116266497),
       (70368744341504,0,6502247,'Malena',105553116266496),
       (70368744374272,0,190258336,'Tosquita',105553116266496),
       (70368744407040,5900,202673351,'Villa Maria',105553116266496),
       (70368744439808,2677,219322255,'Ucacha',105553116266496),
       (70368744472576,2664,220400689,'Benjamín Gould',105553116266496),
       (70368744505344,0,247905811,'San Justo',105553116266497),
       (70368744538112,0,273426048,'Sampacho',105553116266496),
       (70368744570880,0,279579081,'Las Vertientes',105553116266496),
       (70368744603648,0,312911087,'Holmberg',105553116266496),
       (70368744636416,5800,364506086,'Bulnes',105553116266496),
       (70368744701952,2421,549063998,'Morteros',105553116266496);
UNLOCK TABLES;
/*!40000 ALTER TABLE localidad ENABLE KEYS */;

--
-- Table structure for table `cliente`
--

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
  KEY CLIENTE_N1 (LOCALIDAD_ID)
) TYPE=InnoDB;

LOCK TABLES cliente WRITE;
INSERT INTO cliente (CLIENTE_ID,CUIT,DIRECCION,ID,ING_BRUTOS_CL,IVA_CL,LOCALIDAD_ID,NOMBRE,TELEFONO,ELIMINADO,DEUDA,CODIGO) VALUES
       (175921860444161,'27-14940329-4','Av 25 De Mayo Y Utreras',131085,'','Resp. Inscripto',70368744177665,'Heredia Norma Beatriz','',0,0,1),
       (175921860444163,'27-05816129-8','Moreno 16',131087,'','Resp. Inscripto',70368744177665,'Villegas Rosa','',0,1236.01,2),
       (175921860444164,'20-06647447-0','Ameghino Y Dixon',131088,'','Resp. Inscripto',70368744177665,'Morano Federico Alfredo','',0,733.41,3),
       (175921860444165,'20-16404004-7','Roque Saenz Peña 289',131089,'202981244','Resp. Inscripto',70368744177665,'Angeli Sergio Javier','',0,0,4),
       (175921860444167,'20-08008991-1','Aristizabal 572',131091,'209342308','Resp. Inscripto',70368744177665,'Moiso Juan Carlos','',1,285.02,5),
       (175921860444168,'20-06647447-0','Sarmiento Y 9 De Julio',131092,'0209038125','Resp. Inscripto',70368744177665,'Morano Federico Alfredo_','',0,1012.8,6),
       (175921860444170,'20-18046657-7','Mitre 787',131094,'','Monotributo',70368744177665,'Airaudo Miguel Ceferino','',0,108.48,7),
       (175921860444172,'27-16121932-6','Almirante Brown 317',131096,'','Monotributo',70368744177665,'Moyano Lucia','',0,1089.38,8),
       (175921860444173,'27-29484837-6','Saavedra 235',131097,'','Monotributo',70368744177665,'Furlan Mariela','',0,300.06,9),
       (175921860444174,'20-12556343-1','Pje. Arcomano 48',131098,'','Monotributo',70368744177665,'Villarruel Raúl Enrique','',0,903.68,10),
       (175921860444175,'27-05450717-3','Mitre Y Cincuentenario',131099,'209443392','Resp. Inscripto',70368744177665,'Besso Nori Esther','',0,0,11),
       (175921860444177,'23-26925798-9','Juan Jose Paso Y Albertio',131101,'','Monotributo',70368744177665,'Vasquez Martin Amadeo','',0,529.78,12),
       (175921860444178,'23-14864994-4','Av. 9 De Julio 699',131102,'','Monotributo',70368744177665,'Dalio Maria Silvia','',0,0,13),
       (175921860444179,'','----',131103,'','Consumidor Final',70368744177665,'Achabal Carmen','',1,0,14),
       (175921860444180,'20-210406585-2','--',131104,'','Monotributo',70368744177665,'Gregorat','',0,1092.58,15),
       (175921860444181,'','--',131105,'','Consumidor Final',70368744177665,'Ver434444','',1,67.95,16),
       (175921860444182,'20-13078898-0','9 De Julio 368',131106,'','Monotributo',70368744177665,'Paschetta Javier Ignacio','',0,0,17),
       (175921860444183,'20-25796967-4','Belgrano 418',131107,'','Resp. Inscripto',70368744177665,'Gariboldi Diego Ruben','',0,597.34,18),
       (175921860444184,'','..',131108,'','Consumidor Final',70368744177665,'Dominguez Miroslava','',0,966.26,19),
       (175921860444186,'27-26210442-2','Santa Maria De Oro Y Cabrera',131110,'','Monotributo',70368744177665,'Arocena Nancy Verónica','',0,752.35,20),
       (175921860444190,'20-36679983-5','Ameguino 794',131114,'','Monotributo',70368744177665,'Rosales Lucas Matias','',0,70.2,21),
       (175921860444194,'','--',131118,'','Consumidor Final',70368744177665,'Escudero Basilio','',0,1320.25,22),
       (175921860444196,'27-17160850-9','Santa Maria De Oro 555',131120,'','Resp. Inscripto',70368744177665,'Centeno Gabriela Alejandra','',0,526.5,23),
       (175921860444198,'20-13344933-8','Chacabuco 98',131122,'','Monotributo',70368744177664,'Doblas Jose Osmar','154293166',0,1124.9,24),
       (175921860444199,'27-21999115-6','Tucumán 1050',131123,'','Monotributo',70368744177664,'Rama Analia','3585132477',0,0,25),
       (175921860444202,'27-18559031-9','Luis Pasteur 1411',131126,'','Monotributo',70368744177664,'Strazzi Gabriela Beatriz','4661074',0,1101.44,26),
       (175921860444203,'27-24352484-4','Tucumán 1650',131127,'','Monotributo',70368744177664,'Irusta Patricia Rosana','4662241',0,278.51,27),
       (175921860444204,'','Entre Rios',131128,'','Consumidor Final',70368744177664,'Moreno Claudia','',0,0,28),
       (175921860444205,'20-12281513-4','Fray Benitto Tessitore 160',131129,'','Monotributo',70368744177664,'Macho Miguel Angel','',0,350.65,29),
       (175921860444206,'','Pirovano 146',131130,'','Consumidor Final',70368744177664,'Valfre Luis','',0,0,30),
       (175921860444209,'','Rafael Obligado',131133,'','Consumidor Final',70368744177664,'Gomez','',1,0,31),
       (175921860444210,'','Salta',131134,'','Consumidor Final',70368744177664,'Pizzería Belen','',0,336.03,32),
       (175921860444211,'23-32015494-4','Yapeyu 120',131135,'','Monotributo',70368744177664,'Giraudo Natalia Cecilia','',0,-2.27,33),
       (175921860444213,'','Aristobulo Del Valle 441',131137,'','Consumidor Final',70368744177664,'Varela Carolina Mirta','4652398',1,0,34),
       (175921860444217,'','Santiago del Estero',131141,'','Consumidor Final',70368744177664,'Pizería Taty','',0,0,35),
       (175921860444221,'27-22843735-8','Estrada Norte 357',131145,'','Monotributo',70368744177664,'Alfonso Ester Lidia','',0,0,36),
       (175921860444223,'','Lago Nahuel Huapi',131147,'','Consumidor Final',70368744177664,'Moreira Ivana','154012623',0,77.7,37),
       (175921860444225,'20-16486067-2','Roberto Pairo 1290',131149,'','Monotributo',70368744177664,'Pereyra Luis','156548490',0,0,38),
       (175921860444230,'','Marcelo T. De Alvear',131154,'','Consumidor Final',70368744177664,'Esmoris Valeria Noemi','',1,0,39),
       (175921860444231,'27-26163823-7','M T De Alvear 1598',131155,'','Monotributo',70368744177664,'Girardi Romina Pamela','',0,283.33,40),
       (175921860444233,'20-25136638-2','M T De Alvear 1994',131157,'','Monotributo',70368744177664,'Caceres Jorge Eduardo','155093651',0,169.93,41),
       (175921860476928,'20-06649564-8','Rosso Pierino 705',788885,'','Monotributo',70368744177664,'Aguirre Luis Alberto','',0,0,42),
       (175921860476931,'27-29402478-1','La Rioja 2269',789842,'','Monotributo',70368744177664,'Abregu Malvina Soledad','',1,0,43),
       (175921860476932,'20-16121931-3','Hipolito Irugoyen 464',790037,'','Resp. Inscripto',70368744210432,'Aguilera Gerardo Eduardo','',0,0,44),
       (175921860476934,'20-08401140-2','Montevideo 1329',790392,'','Monotributo',70368744177664,'Norabueno Alfredo Armando','4663545',1,0,45),
       (175921860476936,'20-21407548-3','San Martin 40',791408,'','Monotributo',70368744177665,'Chiecher Roberto','',0,393.21,46),
       (175921860476937,'20-31451257-0','Catamarca 634',791591,'','Monotributo',70368744177664,'Cipriani Mauricio German','4664151',0,0,47),
       (175921860476938,'30-70965800-6','Saavedra 27',793603,'','Resp. Inscripto',70368744177665,'Cartuja S.a.','',0,0,48),
       (175921860476939,'20-06651486-3','Republica Arabe Siria 735',793790,'','Monotributo',70368744177664,'Cattaneo Hugo Ramon','4700246',0,0,49),
       (175921860476940,'30-71110095-0','Dante Aluguieri 581',796190,'','Resp. Inscripto',70368744177665,'Carnes El Pichon S.a.','',0,0,50),
       (175921860476941,'20-06655201-3','Hernandes 547',797931,'','Monotributo',70368744177665,'Crespi Miguel Angel','',0,166.34,51),
       (175921860476942,'20-06614559-0','Suipacha 136',798124,'','Monotributo',70368744177664,'Castro Jose','',1,0,52),
       (175921860476943,'27-26175739-2','Pje. Antonio Saenz 3132',799200,'','Monotributo',70368744177664,'Colombo Natalia','',1,0,53),
       (175921860476944,'20-21416977-1','Lopez De Vega 1471',800645,'','Monotributo',70368744177664,'Castagneris Jorge','154387922',0,0,54),
       (175921860476946,'27-23913498-5','Pringles 547',801046,'','Monotributo',70368744177664,'Conde Veronica','4646289',0,0,55),
       (175921860476949,'20-12630550-9','Martin Fierro 474',804644,'','Monotributo',70368744177664,'Constantino Ricardo','4635418',0,0,56),
       (175921860476950,'','Ranqueles 930',807143,'','Consumidor Final',70368744177664,'Cuello Monica Noemi','',0,240.18,57),
       (175921860476951,'27-21638738-6','Moreno 560',807354,'','Monotributo',70368744210432,'Cornejo Maria Rosana','',0,0,58),
       (175921860476952,'30-71092941-2','Av. Italia 1321',807568,'','Resp. Inscripto',70368744177664,'Dos Banderas S.r.l.','',0,0,59),
       (175921860476953,'20-08556193-7','Luis Sahores 232',807786,'','Resp. Inscripto',70368744210432,'Destassi Luis','',0,0,60),
       (175921860476954,'20-26385487-0','Antonio Lucero 1330',808006,'','Monotributo',70368744177664,'Davis Matias','',0,159.3,61),
       (175921860476955,'27-21507260-1','San Martin 176',812186,'','Resp. Inscripto',70368744177664,'El Toboso','',0,0,62),
       (175921860476956,'30-70760498-7','Av. Italia Esq. Aramburu',813789,'','Resp. Inscripto',70368744177664,'El Lomo Y Cia. S.r.l.','',1,0,63),
       (175921860476957,'23-24096928-9','Tristan Burgos 396',815625,'','Resp. Inscripto',70368744177664,'Farias Santiago','',0,0,64),
       (175921860476958,'27-27630626-5','Santa Maria De Oro 878',816654,'','Monotributo',70368744177664,'Flesia Alejandra','4662534',0,0,65),
       (175921860476959,'20-29114162-6','9 De Julio',816877,'','Resp. Inscripto',70368744210432,'Fiori Franco','',0,758.66,66),
       (175921860476960,'27-17733523-7','Moreno 2415',817103,'','Monotributo',70368744177664,'Funes Maria Eusebia','',0,0,67),
       (175921860476961,'20-25349516-3','9 De Julio 681',822117,'','Resp. Inscripto',70368744177665,'Goicochea Juan Jose','',0,105.39,68),
       (175921860476962,'27-11860505-0','Antonio Lucero 1381',828024,'','Monotributo',70368744177664,'Gauchat Maria Rosa','',0,0,69),
       (175921860476963,'20-06562490-0','Chile 214',828255,'','Monotributo',70368744177664,'Gaspero Juan Carlos','',1,0,70),
       (175921860476964,'27-21969008-3','Julio Roca Esq. Italia',830630,'','Monotributo',70368744210432,'Gil Claudia','0358315431556',0,460.77,71),
       (175921860476965,'27-21153703-0','A. Rodriguez 563',832377,'','Monotributo',70368744210432,'Gonzales Liliana Leticia','',0,0,72),
       (175921860476966,'27-29176607-8','Perez Bulnes 2063',834801,'','Monotributo',70368744177664,'Gonzales Lorena','',0,378.3,73),
       (175921860476967,'20-21013357-8','La Rioja 1743',835040,'','Monotributo',70368744177664,'Gonzales Walter','',0,0,74),
       (175921860476968,'20-216945501-9','Laprida Y Pte. Peron',835281,'','Monotributo',70368744177664,'Gnc Altamirano','',1,0,75),
       (175921860476970,'27-25671709-9','Julio Roca 553',840064,'','Monotributo',70368744210432,'Insaurralde Elisa','',0,987.65,76),
       (175921860476971,'27-10437037-9','Entre Rios 1100',840311,'','Monotributo',70368744177664,'Jaime Nelida','4660405',0,926.12,77),
       (175921860476972,'20-27695321-5','Morelli 180',842630,'','Monotributo',70368744210432,'Jaurejiberry Ignacio','',0,0,78),
       (175921860476973,'27-22347367-4','Roque Saenz Peña 320',846338,'','Monotributo',70368744177665,'Lopez Monica Maria','',0,127.61,79),
       (175921860476974,'27-10525683-9','Pje. La Forgee 3051',846588,'','Monotributo',70368744177664,'Lencina Ana','',1,0,80),
       (175921860476975,'20-17562911-5','San Martin 1047',848927,'','Monotributo',70368744210432,'Lopez Ceferino','',0,0,81),
       (175921860476976,'27-14876788-8','Gral. Bustos 2979',849181,'','Monotributo',70368744177664,'Lopez Monica','154192253',0,0,82),
       (175921860476977,'27-13955217-8','La Rioja 2050',850380,'','Monotributo',70368744177664,'Luna Silvia Beatriz','',0,100,83),
       (175921860476978,'27-94009928-0','San Martin 420',852303,'','Resp. Inscripto',70368744210432,'Li Saihua','',0,0,84),
       (175921860476979,'20-27695333-9','Hipolito Irigoyen 263',853042,'','Resp. Inscripto',70368744210432,'Lucero Carlos Adrian','03583-154486434',0,1285.03,85),
       (175921860476980,'27-24464954-4','Alfonso Coronel 1384',862029,'','Monotributo',70368744210432,'Mercado Marisa','',0,0,86),
       (175921860476981,'27-27695400-3','A. Rodriguez',864000,'','Monotributo',70368744210432,'Mercado Gabriela','',0,0,87),
       (175921860476983,'20-05528599-4','Belgrano 1142',865763,'','Resp. Inscripto',70368744210432,'Montiel Hector Ramón','',0,0,88),
       (175921860476984,'27-21999024-9','Alberto Soria 350',867284,'','Monotributo',70368744177664,'Noriega Silvia','4899768',0,80.86,89),
       (175921860476985,'27-17576973-6','Julio Borda 715',872091,'','Monotributo',70368744177664,'Palomino Bibiana','',0,0,90),
       (175921860476986,'27-14365017-6','Yapeyu 1412',872364,'','Monotributo',70368744177664,'Paez Noelia','4349613',0,276.23,91),
       (175921860476987,'30-71115811-8','Sto. Cabral 1091',875712,'','Monotributo',70368744177664,'Pizza Sport S.a.','',0,0,92),
       (175921860476988,'27-18511786-9','Marcelo T. Alvear 1936',875989,'','Monotributo',70368744177664,'Pendito Estella Maris','156009121',0,0,93),
       (175921860476989,'27-16991606-9','Publicitarios Argentinos 551',884595,'','Monotributo',70368744177664,'Rama Maria Alejandra','',0,0,94),
       (175921860476990,'20-27296505-7','Av. Ide Y Jorge Newbery',886710,'','Monotributo',70368744210432,'Riera Lucas','',1,549.73,95),
       (175921860476991,'20-10724240-7','Sta. Maria De Oro 557',890425,'','Monotributo',70368744177664,'Rojo Victor','154339829',0,0,96),
       (175921860476992,'20-33269135-0','Yamanas 2310',890712,'','Monotributo',70368744177664,'Rojo Ariel','',1,0,97),
       (175921860476993,'27-06173637-4','Rio Grande 607',891000,'','Monotributo',70368744177664,'Rincon Miriam','',0,189.46,98),
       (175921860476994,'20-24464985-9','Pascual Biseglia 790',895071,'','Monotributo',70368744210432,'Riera Mauro Alberto','',0,161.14,99),
       (175921861296130,'20-17576707-0','La Pampa 736',22539958,'','Monotributo',70368744177664,'Cipriani Victor','4631197',0,0,100);
UNLOCK TABLES;

--
-- Table structure for table `comercio`
--

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
  NRO_NOTA_CREDITO bigint(20) default NULL,
  NRO_REMITO bigint(20) default NULL,
  TELEFONO varchar(255) binary default NULL,
  PRIMARY KEY  (COMERCIO_ID),
  KEY COMERCIO_N1 (LOCALIDAD_ID)
) TYPE=InnoDB;

--
-- Dumping data for table `comercio`
--


/*!40000 ALTER TABLE comercio DISABLE KEYS */;
LOCK TABLES comercio WRITE;
INSERT INTO comercio VALUES (140737488355328,'','','',131077,'2011-05-23',70368744177665,'Comercio Del Valle',1,1,1,'');
UNLOCK TABLES;
/*!40000 ALTER TABLE comercio ENABLE KEYS */;

--
-- Table structure for table `proveedor`
--

--
-- Table structure for table `factura`
--

CREATE TABLE factura (
  FACTURA_ID bigint(20) NOT NULL default '0',
  ANULADA tinyint(1) NOT NULL default '0',
  ID bigint(20) default NULL,
  IMPORTE_TOTAL double NOT NULL default '0',
  NRO_FACTURA bigint(20) default NULL,
  PERIODO varchar(255) binary default NULL,
  TIPO_FACTURA varchar(255) binary default NULL,
  CLIENTE_ID bigint(20) default NULL,
  PRIMARY KEY  (FACTURA_ID)   ,
  KEY FACTURA_CLIENTE_N2 (CLIENTE_ID)
) TYPE=InnoDB;

--
-- Table structure for table `factura_cliente`
--

CREATE TABLE factura_cliente (
  FACTURA_CLIENTE_ID bigint(20) NOT NULL default '0',
  COND_VENTA varchar(255) binary default NULL,
  FECHA_IMPRESION date default NULL,
  FECHA_PAGO date default NULL,
  IMPORTE_ABONADO double NOT NULL default '0',
  INGR_BRUTOS varchar(255) binary default NULL,
  IVA_F varchar(255) binary default NULL,
  REMITO_NRO varchar(255) binary default NULL,
  PRIMARY KEY  (FACTURA_CLIENTE_ID),
  KEY FACTURA_CLIENTE_N2 (CLIENTE_ID)
) TYPE=InnoDB;

--
-- Table structure for table `nota_credito`
--

CREATE TABLE nota_credito (
  NOTA_CREDITO_ID bigint(20) NOT NULL default '0',
  FECHA date default NULL,
  ID_FACTURA_NC bigint(20) default NULL,
  NRO_FACTURA_NC bigint(20) default NULL,
  OBSERVACIONES varchar(255) binary default NULL,
  TIPO_FACTURA_NC varchar(255) binary default NULL,
  PRIMARY KEY  (NOTA_CREDITO_ID)
) TYPE=InnoDB;

--
-- Table structure for table `planilla_es`
--

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
-- Table structure for table `movimiento_caja`
--

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
  PRIMARY KEY  (MOVIMIENTO_CAJA_ID),
  KEY MOVIMIENTO_CAJA_N1 (PLANILLA_ID),
  KEY MOVIMIENTO_CAJA_N2 (FACTURA_ID)
) TYPE=InnoDB;

CREATE TABLE item_factura (
  ITEM_FACTURA_ID bigint(20) NOT NULL default '0',
  CANTIDAD double NOT NULL default '0',
  FACTURA_ID bigint(20) default NULL,
  ID bigint(20) default NULL,
  PR_TOTAL double NOT NULL default '0',
  PR_UNIT double NOT NULL default '0',
  PRODUCTO varchar(255) default NULL,
  PRIMARY KEY  (ITEM_FACTURA_ID),
  KEY ITEM_FACTURA_N1 (FACTURA_ID)
) TYPE=InnoDB;






