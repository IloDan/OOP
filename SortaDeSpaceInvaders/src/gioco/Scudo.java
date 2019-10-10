package gioco;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Scudo extends AbstractGambeObject {

	private int width, height;
	
	public Scudo(int x, int y, Color color, int width, int height) {
		super(x, y, color);
		this.width = width;
		this.height = height;
	}
	

	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void paint(Graphics g) {
		 g.setColor(c);
	     g.fillRect(this.getX(), this.getY(), 90, 10);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(),this.getY(),90,10);
	}


	@Override
	public void move() {
		// TODO Auto-generated method stub
	}

}
