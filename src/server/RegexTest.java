package server;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {

	@Test
	public void escapeText(){
		String input = "vic&username=purpl^&";	
		String output = "vic\\&username\\=purpl^\\&";
		System.out.println(Regex.escape(input));
		assertEquals(output, Regex.escape(input));
		assertEquals("2\\&adf3", Regex.unEscape(Regex.escape("2\\&adf3")));
	}
	
	@Test
	public void regexTest(){
		String input = "opendoc&docName=document&userName=vpontis&version=2\\&adf3&\n";
		assertEquals("document", Regex.getField("docName", input));
		assertEquals("vpontis", Regex.getField("userName", input));
		assertEquals("2&adf3", Regex.getField("version", input));
	}
}
