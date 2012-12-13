package view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


/**
 * This class contains code to display rendered LateX code
 *
 */
@SuppressWarnings("serial")
class LatexPanel extends JPanel{
	private Image image = null;
	
	public void updateImage(Image image){
		this.image = image;
	}
	
	/**
	 * Method that paints the LaTeX component
	 */
	@Override
	public void paintComponent(Graphics g){
		if(image != null){			
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null);
		}
	}
}
