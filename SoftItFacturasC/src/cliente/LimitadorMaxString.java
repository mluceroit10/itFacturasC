package cliente;

import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitadorMaxString extends PlainDocument{
	
	private static final long serialVersionUID = 1L;
	private JTextArea miJTextField;
	private int nroMaxCaracteres=9;
	
	public LimitadorMaxString(JTextArea mijtext, int nroMaxCaract){
		miJTextField=mijtext;
		nroMaxCaracteres=nroMaxCaract;
	}
	
	public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException{
		for (int i=0;i<arg1.length();i++){
			if ((miJTextField.getText().length()+arg1.length())>nroMaxCaracteres){
				miJTextField.transferFocus();
				return;
			}/*else 
			if (!Character.isDigit(arg1.charAt(i)))
			return;*/
		}
		super.insertString(arg0, arg1, arg2);
	}
	
}

