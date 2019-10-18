 package gioco;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.*;

public class Player extends AbstractGambeObject {
	ImageIcon bart=new ImageIcon("Images/Bart.gif");
	
	
	public Player(int x, int y, int sx, int sy, Color c) {
		super(x, y, sx, sy, c);
		
	}

	@Override
	public void move() {
		x+=sx;
	}

	@Override
	public void paint(Graphics g) {
		bart.paintIcon(null, g, this.getX(), this.getY());
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(50,50);
	}

}
