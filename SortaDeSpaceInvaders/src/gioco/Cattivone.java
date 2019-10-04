package gioco;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Cattivone extends AbstractGambeObject {

	private int tipoCattivone, w,h;
	ImageIcon alien1 = new ImageIcon("images/alien1Skin.gif");
    ImageIcon alien2 = new ImageIcon("images/alien2Skin.gif");
    ImageIcon alien3 = new ImageIcon("images/alien3Skin.gif");
    ImageIcon alienBoss = new ImageIcon("images/boss1.gif");
    ImageIcon alienBoss2 = new ImageIcon("images/boss2.gif");
    ImageIcon alienBoss3 = new ImageIcon("images/boss3.gif");
    
	public Cattivone(int x, int y, int sx, int sy, Color color, int tc, int w, int h) {
		super(x, y, sx, sy, color);
		this.tipoCattivone=tc;
		this.w=w;
		this.h=h;
	}

	public int getTipoCattivone() {
		return tipoCattivone;
	}

	public void setTipoCattivone(int tipoCattivone) {
		this.tipoCattivone = tipoCattivone;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	@Override
	public void move() {
		x+=sx;
	}

	@Override
	public void paint(Graphics g) {
		 // Varient 1
        if (this.tipoCattivone  % 3 == 0) {
            alien1.paintIcon(null, g, this.getX(), this.getY());
        // Varient 2
        } else if (this.tipoCattivone % 3 == 1 && this.tipoCattivone  != 100) {
            alien2.paintIcon(null, g, this.getX(), this.getY());
        // Varient 3
        } else if (this.tipoCattivone  % 3 == 2) {
            alien3.paintIcon(null, g, this.getX(), this.getY());
        // Boss Enemy
        } if (this.tipoCattivone  == 100)
        {
            if(SpacePanel.getVitaCattivone()>20){
                alienBoss.paintIcon(null, g, this.getX(), this.getY());
            }
            else if(SpacePanel.getVitaCattivone()>10){
                alienBoss2.paintIcon(null, g, this.getX(), this.getY());
            }
            else if(SpacePanel.getVitaCattivone()>0){
                alienBoss3.paintIcon(null, g, this.getX(), this.getY());
            }
        }
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(),this.getY(), w,h);
	}

}
