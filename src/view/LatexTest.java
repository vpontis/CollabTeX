package view;

import static org.junit.Assert.*;

import org.junit.Test;

public class LatexTest {

	
	@Test
	public void isLatexTest(){
		String latex = "\\frac {V_m} {K_M+S}";
		assertTrue(Latex.isLatex(latex));
	}
}
