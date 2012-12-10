package view;

import java.awt.Insets;
 	
import org.scilab.forge.jlatexmath.*;

public class Latex {
	/**
	 * Tests is a certain string is latex code
	 * @param string
	 * @return if the string is valid latex code
	 */
	public static boolean isLatex(String string){
		try{
			new TeXFormula(string);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Converts a string of latex into a TeXIcon from which an image
	 * can be generated
	 * @param latexString from the document area
	 * @return TeXIcon that can build an image from the given latex
	 */
	public static TeXIcon getLatex(String latexString){
		//we only care about latex embedded in $$, all else is regular text
		String[] stringSplit = latexString.split("\\$\\$");
		
		StringBuilder out = new StringBuilder();
		
		String beginning = "\\begin{array}{l} ";
		String end = "\\end{array}";
		
		out.append(beginning);
		for (int i = 0; i < stringSplit.length; i++ ){
			if (i % 2 == 0){
				out.append("\\textrm{");
				String escaped = escape(stringSplit[i]);
				out.append(escaped);
				out.append("}");
			}
			else{
				out.append(handleNewLines(stringSplit[i]));
			}
		}
		out.append(end);
		
		String string = out.toString();
		System.out.println(string);
		
		TeXIcon icon = new TeXFormula(string)
					.createTeXIcon(TeXConstants.STYLE_TEXT, (float) 20.,
							TeXConstants.UNIT_CM, (float) 10.5, 0);
		icon.setInsets(new Insets(5,5,5,5));
		
		//TODO figure out how to wrap the text
		return icon;
	}

	/**
	 * Escapes special characters in latex so that they are displayed properly
	 * This defends the way we format against injection
	 * @param string of raw latex code
	 * @return a string where the latex special characters are escaped
	 */
	public static String escape(String string) {
		string = string.replaceAll("\\\\(?!n)", "\\\\backslash");
		string = string.replaceAll("~", "\\\\~");
		string = string.replaceAll("#", "\\\\#");
		string = string.replaceAll("\\$", "\\\\\\$");
		string = string.replaceAll("%", "\\\\%");
		string = string.replaceAll("_", "\\\\_");
		string = string.replaceAll("\\{", "\\\\{");
		string = string.replaceAll("\\}", "\\\\}");
		string = string.replaceAll("\\^","\\\\^{}");
		string = string.replaceAll("\\@","\\\\@");
		string = string.replaceAll("\\&","\\\\&");
		string = string.replaceAll("\\n", "\\} \\\\\\\\ \\\\textrm\\{");
//		StringBuilder out = new StringBuilder();
//		String[] lines = string.split("\\n");
//		for (int i = 0; i < lines.length; i++){
//			out.append("\\textrm{");
//			out.append(lines[i]);
//			if (i != lines.length - 1 || string.substring(lines.length - 4).equals("\\n"))
//				out.append("} \\\\ ");
//		}
		return string;
	}
	
	
	public static String handleNewLines(String string){
		string = string.replaceAll("\\n", "\\\\\\\\");
		return string;
	}
}
