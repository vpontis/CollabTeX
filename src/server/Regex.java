package server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	/**
	 * Returns a field of a regular expression
	 * @param field 
	 * @param input to find the field 
	 * @return the field from the input
	 */
	public static String getField(String field, String input){
		String regexPattern = "(?<=" + field + "\\=)(.*?)(?=((?<![\\\\])\\&))";
		Pattern regex = Pattern.compile(regexPattern);
		Matcher matcher = regex.matcher(input);
		matcher.find();
		String response = matcher.group();
		response = unEscapeText(response);
		return response;
	}
	
	/**
	 * Escapes the text
	 * @param regular text
	 * @return escaped text
	 */
	public static String escapeText(String text){
		text.replaceAll("\\&", "\\\\\\&");
		text.replaceAll("\\=", "\\\\\\=");
		return text;
	}
	
	/**
	 * Unescapes the text
	 * @param escaped text
	 * @return regular text
	 */
	public static String unEscapeText(String text){
		text.replaceAll("\\\\\\&", "\\&");
		text.replaceAll("\\\\\\=", "\\=");
		return text;
	}

}
