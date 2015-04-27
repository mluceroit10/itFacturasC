package cliente;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitadorPrecio extends PlainDocument{
	private static final long serialVersionUID = 1L;			 
	private JTextField miJTextField;
	
	public LimitadorPrecio(JTextField mijtext){
		miJTextField=mijtext;
	}
	
	public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException{
		for (int i=0;i<arg1.length();i++){
			if (arg1.charAt(i)!='.' && !Character.isDigit(arg1.charAt(i)))
				return;
			
		}
		super.insertString(arg0, arg1, arg2);
	}
	
}
