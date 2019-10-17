package gioco;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends AbstractGambeObject {

	
	public Player(int x, int y, int sx, int sy, Color c) {
		super(x, y, sx, sy, c);
		
	}

	@Override
	public void move() {
		if()
	}

	@Override
	public void paint(Graphics g) {
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(50,50);
	}

}
