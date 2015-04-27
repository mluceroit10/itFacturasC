package prueba;

import java.util.Vector;

import persistencia.domain.FacturaCliente;
import server.ManipuladorPersistencia;

import common.Utils;

public class ModificarDatos {
	public ModificarDatos() {
	}
	
	
	public static void main(String[] args) throws Exception {
		/*ManipuladorPersistencia mp = new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			String filtro = "tipoFactura==\"FacturaCliente-B\"";
			Vector facts = mp.getObjectsOrdered(FacturaCliente.class,filtro,"id ascending");
			for(int i=0; i<facts.size();i++){
				FacturaCliente b = (FacturaCliente)facts.elementAt(i);
				System.out.print("importe "+b.getImporteTotal());
				double iva21=Utils.ivaProducto(Utils.decrementarIVA(b.getImporteTotal(),21),21);
				System.out.println("  iva "+iva21);
			}	
			mp.commit();
		}  finally{
			mp.rollback();
		}*/
		java.util.Date diaBusq= new java.util.Date(2014-1900,11-1,11);
		java.sql.Date fechaHoy = new java.sql.Date((diaBusq).getTime());
		java.sql.Date fechaSem = new java.sql.Date((diaBusq).getTime()+(3600000*24*10)); //una sem desp.
		System.out.println(fechaHoy);
		System.out.print(fechaSem);
	
	}
}
