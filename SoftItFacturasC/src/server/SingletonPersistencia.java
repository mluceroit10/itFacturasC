package server;

import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import persistencia.OidGenerator;

import common.RootAndIp;

public class SingletonPersistencia {
	
	private static SingletonPersistencia instance = new SingletonPersistencia();
	private String pmfClass = "com.triactive.jdo.PersistenceManagerFactoryImpl";
	private String driver = "com.mysql.jdbc.Driver";
	
	private String autoCreateTable = "true";
	private Properties props = new Properties();
	private PersistenceManager pm;
	private PersistenceManagerFactory pmf;
	
	private SingletonPersistencia(){
		super();
		props.setProperty("javax.jdo.PersistenceManagerFactoryClass",pmfClass);
		props.setProperty("javax.jdo.option.ConnectionDriverName", driver);
		props.setProperty("javax.jdo.option.ConnectionURL",RootAndIp.getUrlDb());
		props.setProperty("javax.jdo.option.ConnectionUserName", RootAndIp.getUserName());
		props.setProperty("javax.jdo.option.ConnectionPassword", RootAndIp.getPassword());
		props.setProperty("com.triactive.jdo.autoCreateTables", autoCreateTable);
		try{
			pmf = JDOHelper.getPersistenceManagerFactory(props);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		try {
			pm = pmf.getPersistenceManager();
			OidGenerator.init(pmf);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public PersistenceManager getPM(){
		return pm;
	}
	
	public static SingletonPersistencia getInstance(){
		return instance;
	}
	
}
