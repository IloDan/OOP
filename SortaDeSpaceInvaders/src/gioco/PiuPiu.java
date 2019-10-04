package gioco;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PiuPiu extends AbstractGambeObject {
	
	public PiuPiu(int x, int y, int sx,int sy, Color color) {
		super(x,y,sx,sy,color);
	}

	@Override
	public void move() {
		y-=sy;
		x+=sx;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(this.getX(),this.getY(), 7, 15);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,7,15);
	}

}
