package persistencia;
import java.util.Collection;
import java.util.Iterator;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import persistencia.domain.Oid;

/**
 * ID generator
 */

public class OidGenerator{
	static Oid oid = null;
	static PersistenceManagerFactory pmf=null;
	static PersistenceManager pm= null;
	static Transaction tx;
	static long highValueAux;
	static private long currentLowValue = 0;
	static private long currentHighVaue = -1;
	
	static private long highValue=-1;
	
	public static void init(PersistenceManagerFactory pmf1)
	{
		try
		{
			pmf=pmf1;
			pm=pmf.getPersistenceManager();
			tx= pm.currentTransaction();
			tx.begin();
			
			Class oidClass = Oid.class;
			Extent clnOid = pm.getExtent(oidClass, false);
			String filter = new String("");
			Query query = pm.newQuery(clnOid,filter);
			Collection oids= (Collection)query.execute();
			
			if (!oids.isEmpty())
			{
				for (Iterator i = oids.iterator(); i.hasNext(); ) {
					oid = (Oid)i.next();
				}
				highValue = oid.getOid().longValue();
				oid.setOid(new Long(highValue+1));
			}
			else
			{
				oid= new Oid();
				oid.setOid(new Long(1));
				pm.makePersistent(oid);
				highValue=1;
			}
			
			
			tx.commit();
		}
		catch (Exception ex) {
			
			ex.printStackTrace();
			tx.rollback();
		}
		
		
	}
	
	
	
	/**
	 * Return new ID.
	 *
	 * @return new ID
	 */
	static synchronized public Long getNewId()
	{  try
	{  currentLowValue++;
	if (currentHighVaue < 0 || currentLowValue > 0xffff)
	{ currentLowValue = 0;
	currentHighVaue = getNextHighValue().longValue();
	}
	return new Long(currentHighVaue | currentLowValue);
	}
	catch(Exception e)
	{ return new Long(0);
	}
	}
	
	
	
	/**
	 * Return new high value.
	 *
	 * @return new high value
	 */
	synchronized static public Long getNextHighValue()
	{
		try {
			highValue++;
			highValueAux = highValue;
			highValueAux <<= 16;
			highValueAux &= 0xffffffffffff0000L;
			
			pm=pmf.getPersistenceManager();
			tx= pm.currentTransaction();
			tx.begin();
			
			Class oidClass = Oid.class;
			Extent clnOid = pm.getExtent(oidClass, false);
			String filter = new String("");
			Query query = pm.newQuery(clnOid,filter);
			Collection oids= (Collection)query.execute();
			for (Iterator i = oids.iterator(); i.hasNext(); ) {
				oid = (Oid)i.next();
			}
			oid.setOid(new Long(highValue));
			tx.commit();
			
			return new Long(highValueAux);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return null;
		}
	}
	
	
	public void setHighValue(long highValue)
	{  this.highValue = highValue;
	}
	public void setPmf(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}
	
	
	
}
