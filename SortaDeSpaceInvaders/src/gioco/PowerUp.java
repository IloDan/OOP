package gioco;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PowerUp extends AbstractGameObject {



	public PowerUp(int x, int y, int sx, int sy, Color c) {
		super(x, y, sx, sy, c);
	}

	@Override
	public void move() {
		y-=sy;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(c);
	    int[] x1  = {this.getX()+32,this.getX()+40,this.getX()+62,this.getX()+42,this.getX()+50,this.getX()+30,this.getX()+5,this.getX()+18,this.getX()-1,this.getX()+22,this.getX()+32};
	    int [] y1 = {this.getY()+28,this.getY()+52,this.getY()+58,this.getY()+70,this.getY()+95,this.getY()+75,this.getY()+92,this.getY()+65,this.getY()+48,this.getY()+50,this.getY()+28};
	    g.fillPolygon(x1, y1, 11);
	    
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,50,50);
	}

}
