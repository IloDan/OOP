package gioco;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Cattivone extends AbstractGambeObject {

	private int tipoCattivone, w, h;
	ImageIcon alien1 = new ImageIcon("image/alien1Skin.gif");
	ImageIcon alien2 = new ImageIcon("image/alien2Skin.gif");
	ImageIcon alien3 = new ImageIcon("image/alien3Skin.gif");
	ImageIcon alienBoss = new ImageIcon("image/boss1.gif");
	ImageIcon alienBoss2 = new ImageIcon("image/boss2.gif");
	ImageIcon alienBoss3 = new ImageIcon("image/boss3.gif");

	public Cattivone(int x, int y, int sx, int sy, Color color, int tc, int w, int h) {
		super(x, y, sx, sy, color);
		this.tipoCattivone = tc;
		this.w = w;
		this.h = h;
	}

	@Override
	public void move() {
		x += sx;
	}

	@Override
	public void paint(Graphics g) {
		// Varient 1
		if (this.tipoCattivone % 3 == 0) {
			alien1.paintIcon(null, g, this.getX(), this.getY());
			// Varient 2
		} else if (this.tipoCattivone % 3 == 1 && this.tipoCattivone != 100) {
			alien2.paintIcon(null, g, this.getX(), this.getY());
			// Varient 3
		} else if (this.tipoCattivone % 3 == 2) {
			alien3.paintIcon(null, g, this.getX(), this.getY());
			// Boss Enemy
		}
		if (this.tipoCattivone == 100) {
			if (SpacePanel.getVitaCattivone() > 20) {
				alienBoss.paintIcon(null, g, this.getX(), this.getY());
			} else if (SpacePanel.getVitaCattivone() > 10) {
				alienBoss2.paintIcon(null, g, this.getX(), this.getY());
			} else if (SpacePanel.getVitaCattivone() > 0) {
				alienBoss3.paintIcon(null, g, this.getX(), this.getY());
			}
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), w, h);
	}

}
