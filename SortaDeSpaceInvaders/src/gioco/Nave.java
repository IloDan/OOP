package gioco;

import java.awt.*;
import javax.swing.*;

public class Nave extends AbstractGambeObject {
	ImageIcon nave = new ImageIcon("image/shipSkin.gif");
    ImageIcon bonusEnemy = new ImageIcon("image/bonusEnemySkin.gif");
    ImageIcon lifeCounterShip = new ImageIcon("image/shipSkinSmall.gif");
	
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
	
	public void bonusDraw(Graphics g) {

        bonusEnemy.paintIcon(null, g, this.getX(), this.getY());
    }

    // Draw ships for life counter
    public void lifeDraw(Graphics g) {

        lifeCounterShip.paintIcon(null, g, this.getX(), this.getY());
    }

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), 50, 50);
	}

}
