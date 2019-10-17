package gioco;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface GameObject {
	

	public void move();
	public void paint (Graphics g);
	public Rectangle getBounds();
	
	public int getX();
	public void setX(int x);
	public int getY();
	public void setY(int y);
	public int getSpeedX();
	public void setSpeedX(int sx);
	public int getSpeedY();
	public void setSpeedY(int sy);
}
