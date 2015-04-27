package reports;
import java.util.Hashtable;

import dori.jasper.engine.JRDataSource;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JRField;

public class DataSourceJasper implements JRDataSource {
	
	private Object[][] data = null;
	
	private Hashtable tabla = null; 
	
	private int index = -1;
	
	public DataSourceJasper(Object[][] newData, Hashtable newTabla) {
		data = newData;
		tabla = newTabla;
	}
	
	public boolean next() throws JRException {		
		index++;
		return (index < data.length);
	}
	
	public Object getFieldValue(JRField field) throws JRException {
		String fieldName = field.getName();
		return data[index][((Integer) tabla.get(fieldName)).intValue()];
	}
	
}