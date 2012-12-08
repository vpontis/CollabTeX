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
		String[] stringSplit = latexString.split("$$");
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < stringSplit.length; i++ ){
			if (i % 2 == 0){
				out.append("\\textrm{");
				String escaped = escape(stringSplit[i]);
				out.append(escaped);
				out.append("}");
			}
			else{
				out.append(stringSplit[i]);
			}
		}
		String string = out.toString();
		System.out.println(string);
		TeXIcon icon = new TeXFormula(string)
					.createTeXIcon(TeXConstants.STYLE_DISPLAY,20);
		icon.setInsets(new Insets(5,5,5,5));
		return icon;
	}

	public static String escape(String string) {
//        "#"=>"\\#",
//        "$"=>"\\$",
//        "%"=>"\\%",
//        "&"=>"\\&",
//        "~"=>"\\~{}",
//        "_"=>"\\_",
//        "^"=>"\\^{}",
//        "\\"=>"\\textbackslash",
//        "{"=>"\\{",
//        "}"=>"\\}",
        string = string.replaceAll("\\\\", "\\\\textbackslash");
		string = string.replaceAll("#", "\\#");
		string = string.replaceAll("\\$", "\\$");
		string = string.replaceAll("%", "\\%");
		string = string.replaceAll("~", "\\~");
		string = string.replaceAll("_", "\\_");
		string = string.replaceAll("\\{", "\\{");
		string = string.replaceAll("\\}", "\\}");
		string = string.replaceAll("\\^","\\^{}");
		return string;
	}
	
}
