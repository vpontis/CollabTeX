package server;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This file contains the regexes used by both the ServerRequest and
 * ControllerRequest obejcts. They assume that the input is in the format
 * of an HTTP get request
 */
public class Regex {
	/**
	 * Returns a field of a regular expression
	 * @param field 
	 * @param input to find the field 
	 * @return the field from the input
	 */
	public static String getField(String field, String input){
		String regexPattern = "(?<=((?<=(\\&))" + field + "\\=))(.*?)(?=((?<![\\\\])\\&))";
		Pattern regex = Pattern.compile(regexPattern);
		Matcher matcher = regex.matcher(input);
		matcher.find();
		String response = matcher.group();
		response = unEscape(response);
		return response;
	}
	
	/**
	 * Escapes the text
	 * @param regular text
	 * @return escaped text
	 */
	public static String escape(String text){
		text = text.replace("&", "\\&");
		text = text.replace("=", "\\=");
		return text;
	}
	
	/**
	 * Unescapes the text
	 * @param escaped text
	 * @return regular text
	 */
	public static String unEscape(String text){
		text = text.replace("\\&", "&");
		text = text.replace("\\=", "=");
		return text;
	}
	
	/**
	 * Changes the request line into a dictionary
	 * @param request with key values in the form of a get request
	 * @return a dictionary which maps all keys to values
	 */
	public static Map<String, String> parseRequest(String request){
		int index = 0;
		String regexPattern = "(?<=(\\&))(.*?)(?=((?<![\\\\])\\=))";
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(request);
		boolean found = matcher.find(index);
		Map<String, String> map = new HashMap<String, String>();
		
		while(found){
			String key = matcher.group().replace("&", "");
			String value;
			if(key.equals("change") || key.equals("content") || key.equals("docContent"))
				value = unEscape(request.substring(matcher.end()+1, request.length()-1));
			else
				value = Regex.getField(key, request);
			map.put(key, value);			
			index = matcher.end();
			found = matcher.find(index);
		}
		return map;
	}
}
