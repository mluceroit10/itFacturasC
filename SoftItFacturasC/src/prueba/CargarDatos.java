package prueba;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import persistencia.domain.Comercio;
import persistencia.domain.Localidad;
import persistencia.domain.Provincia;
import server.SingletonPersistencia;

import common.Utils;

public class CargarDatos {
	public CargarDatos() {
	}
	
	
	public static void main(String[] args) {
		PersistenceManager pm = SingletonPersistencia.getInstance().getPM();
		
		System.out.println("Provincias...");
		Provincia p1 = new Provincia();
		p1.setNombre("Córdoba");
		
		System.out.println("Localidades.");
		Localidad l1 = new Localidad();
		l1.setNombre("Río Cuarto");
		l1.setCodPostal(5800);
		l1.setProvincia(p1);
		
		System.out.println("Comercio.");
		Comercio d1 = new Comercio();
		d1.setNombre("Comercio Del Valle");
		d1.setLocalidad(l1);
		d1.setCategoria("");//ingr Brutos
		d1.setCuit("");
		d1.setDireccion("");
		d1.setInicioAct(Utils.crearFecha(23,05,2011));
		d1.setTelefono("");
		
		Transaction tx= pm.currentTransaction();
		try{
			try {
				tx.begin();
				System.out.println("creando las provincias");
				pm.makePersistent(p1);
				
				System.out.println("Creando las localidades");
				pm.makePersistent(l1);
				
				System.out.println("Creando el comercio");
				pm.makePersistent(d1);
				
				tx.commit();
			}catch(Exception ex){ex.printStackTrace();
			if (tx.isActive()) tx.rollback();
			}
		}finally{
			if (tx.isActive()) tx.rollback();
		}
	}
}
