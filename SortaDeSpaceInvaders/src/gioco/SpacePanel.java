package gioco;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class SpacePanel extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Nave ship;
	private Cattivone tmpCattivone;
	private PiuPiu piu;
	private ImageIcon background = new ImageIcon("image/backgroundSkin.jpg");
	private Timer timer;
	private static int cattivone = 30;
	private boolean canPiuPiu = true;
	private int level = 1;
	public int score = 0;
	private ArrayList<Cattivone> listaCattivoni = new ArrayList();

	public SpacePanel() {
		this.setSize(800, 800);
		this.setPreferredSize(new Dimension(800, 800));
		this.setBackground(Color.BLACK);
		addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	public static int getVitaCattivone() {
		return cattivone;
	}

	public void setup() {
		if (level != 3 && level != 6 && level != 9 && level != 12) {
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 5; j++) {
					tmpCattivone = new Cattivone((20 + (i * 100)), (20 + (j * 60)), level, 0, null, j, 40, 40);
					listaCattivoni.add(tmpCattivone);
				}
			}
		}
		if (level == 3 || level == 6 || level == 9 || level == 12) {
			tmpCattivone = new Cattivone(20, 20, 3, 0, null, 100, 150, 150);
			listaCattivoni.add(tmpCattivone);
		}
		ship = new Nave(375, 700, null);
	}

	@Override
	public void paint(Graphics g) {
		background.paintIcon(null, g, 0, -150);

		/*
		 * if (piu != null) { if (hitMarker) { g.setColor(Color.WHITE); if (level != 3
		 * && level != 6 && level != 9 && level != 12) { g.drawString("+ 100", markerX +
		 * 20, markerY -= 1); } else { g.drawString("- 1", markerX + 75, markerY += 1);
		 * } } }
		 */
		for (int index = 0; index < listaCattivoni.size(); index++) {
			listaCattivoni.get(index).paint(g);
		}

		ship.paint(g);
		if (piu != null) {
			piu.paint(g);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e);
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			ship.setSpeedX(3);
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			ship.setSpeedX(-3);
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			if (canPiuPiu) {
				piu = new PiuPiu(ship.getX() + 22, ship.getY() - 20, ship.getSpeedX(), 10, Color.RED);
				canPiuPiu = false;
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			ship.setSpeedX(0);
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			ship.setSpeedX(0);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public void updateG(int fps) {
		ship.move();
		// movimento cattivoni
		if ((listaCattivoni.get(listaCattivoni.size() - 1).getX()
				+ listaCattivoni.get(listaCattivoni.size() - 1).getSpeedX()) > 760
				|| (listaCattivoni.get(0).getX() + listaCattivoni.get(0).getSpeedX()) < 0) {
			for (int index = 0; index < listaCattivoni.size(); index++) {
				listaCattivoni.get(index).setSpeedX(listaCattivoni.get(index).getSpeedX() * -1);
				listaCattivoni.get(index).setY(listaCattivoni.get(index).getY() + 10);
			}
		} else {
			for (int index = 0; index < listaCattivoni.size(); index++) {
				listaCattivoni.get(index).move();
			}
		}

		// PiuPiu
		if (piu != null) {
			piu.move();
			if (piu.getY() < 0) {
				canPiuPiu = true;
			}
			// Cattivone colpito
			for (int i = 0; i < listaCattivoni.size(); i++) {
				if (piu.isBoom(listaCattivoni.get(i))) {
					piu = new PiuPiu(0, 0, 0, 0, null);
					canPiuPiu = true;
					// Updates score for normal levels
					if (level != 3 && level != 6 && level != 9 && level != 12) {
						score += 100;
						// hitMarker = true;
						// markerX = enemyList.get(index).getXPosition(); // Gets positions that the "+
						// 100" spawns off of
						// markerY = enemyList.get(index).getYPosition();
						listaCattivoni.remove(i);
					}
				}
			}
		}
	}

	public void start() {
		setup();
		timer = new Timer(1000 / 120, new ActionListener() {

			// Tracks the number of frames that have been produced.
			// May be useful for limiting action rates
			private int fps = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				// Update the game's state and repaint the screen

				updateG(fps++);
				repaint();
			}
		});
		timer.setRepeats(true);
		timer.start();
	}
}