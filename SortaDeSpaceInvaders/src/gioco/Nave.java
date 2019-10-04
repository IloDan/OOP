package gioco;

import java.awt.*;
import javax.swing.*;

public class Nave extends AbstractGambeObject {
	ImageIcon nave = new ImageIcon("image/shipSkin.gif");

	public Nave(int x, int y, Color c) {
		super(x,y,c);
	}

	@Override
	public void move() {
		x += sx;
		if (x > 800) {
			x = -50;
		}
		if (x < -50) {
			x = 800;
		}
	}

	@Override
	public void paint(Graphics g) {
		nave.paintIcon(null, g, this.getX(), this.getY());
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), 50, 50);
	}

}
