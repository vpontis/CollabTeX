package view;

import java.awt.Insets;
 	
import org.scilab.forge.jlatexmath.*;

public class Latex {
	public static boolean isLatex(String string){
		if(1==1){
			try{
				new TeXFormula(string);
				return true;
			}
			catch(Exception e){
				return false;
			}
		}
		return false;
	}
	
	public static TeXIcon getLatex(String latexString){
		TeXIcon icon = new TeXFormula(latexString)
					.createTeXIcon(TeXConstants.STYLE_DISPLAY,20);
		icon.setInsets(new Insets(5,5,5,5));
		return icon;
	}
	
}
