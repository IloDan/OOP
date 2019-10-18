package gioco;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel  {

	private ImageIcon background = new ImageIcon("Images/BG1.jpg");
	
	public void setUp() {
		
	}
	
	public void paint(Graphics g) {
		background.paintIcon(null, g, 0, 0);
	}
	
	public void update() {
		
	}

	public void start() {
		
	}

}
