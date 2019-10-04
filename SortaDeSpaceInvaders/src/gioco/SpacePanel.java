package gioco;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpacePanel extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Nave ship;
	private PiuPiu piu;
	private ImageIcon background = new ImageIcon("image/backgroundSkin.jpg");
	private Timer timer;
	private boolean canPiuPiu = true;

	public SpacePanel() {
		this.setSize(800, 800);
		this.setPreferredSize(new Dimension(800, 800));
		this.setBackground(Color.BLACK);
		addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	@Override
	public void paint(Graphics g) {
		background.paintIcon(null, g, 0, -150);

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
			ship.setSpeedX(4);
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			ship.setSpeedX(-4);
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			if (canPiuPiu) {
				piu = new PiuPiu(ship.getX() + 22, ship.getY() - 20, ship.getSpeedX(), 15, Color.RED);
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

		if (piu != null) {
			piu.move();
			if (piu.getY() < 0) {
				canPiuPiu = true;
			}
		}
	}

	public void start() {
		ship = new Nave(375, 700, null);
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