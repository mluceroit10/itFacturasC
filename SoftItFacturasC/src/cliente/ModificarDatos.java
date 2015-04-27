package cliente;

import server.ManipuladorPersistencia;

public class ModificarDatos {
	public ModificarDatos() {
	}
	
	
	public static void main(String[] args) throws Exception {
		ManipuladorPersistencia mp = new ManipuladorPersistencia();
		try {
			mp.initPersistencia();
			/*	String filtro="tipoFactura==\"RemitoCliente\"";
			 Vector fClientes = mp.getObjects(FacturaCliente.class,filtro);
			 for(int i=0; i<fClientes.size();i++){
			 FacturaCliente b = (FacturaCliente)fClientes.elementAt(i);
			 System.out.println("pos "+(i+1)+" nro factC " +b.getNroFactura());
			 
			 String filtroRem = " remitoNro == \"" +b.getNroFactura()+ "\"";
			 Vector facturaClienteRem= mp.getObjects(FacturaCliente.class,filtroRem);
			 if (facturaClienteRem.size()==1)
			 b.setRemitoNro("Facturado");
			 }	
			 
			 */
			
			mp.commit();
		}  finally{
			mp.rollback();
		}
	}
}
