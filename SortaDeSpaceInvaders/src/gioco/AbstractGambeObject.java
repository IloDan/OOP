package gioco;

import java.awt.Color;

public abstract class AbstractGambeObject implements GameObject{
	int x,y;
	int sx,sy;
	Color c;
	
	public AbstractGambeObject(int x, int y, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.c = color;
	}

	public AbstractGambeObject(int x, int y, int sx, int sy, Color c) {
		super();
		this.x = x;
		this.y = y;
		this.sx = sx;
		this.sy = sy;
		this.c = c;
	}
	
	@Override
	public int getX() {
		return x;
	}
	@Override
	public void setX(int x) {
		this.x=x;
	}
	@Override
	public int getY() {
		return y;
	}
	@Override
	public void setY(int y) {
		this.y=y;
	}
	@Override
	public int getSpeedX() {
		return sx;
	}
	@Override
	public void setSpeedX(int sx) {
		this.sx=sx;
	}
	@Override
	public int getSpeedY() {
		return sy;
	}
	@Override
	public void setSpeedY(int sy) {
		this.sy=sy;
	}	
	public Color getColor() {
		return c;
	}
	public void setColor(Color c) {
		this.c = c;
	}
	public boolean isBoom(AbstractGambeObject other) {
		return other.getBounds().intersects(this.getBounds());
	}
	
}
