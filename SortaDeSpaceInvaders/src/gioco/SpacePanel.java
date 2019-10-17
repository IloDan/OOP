package gioco;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class SpacePanel extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Nave ship;
	private Nave vita;
	private Cattivone tmpCattivone;
	private PiuPiu piu;
	private PiuPiu badPiu, badPiu2, badPiu3;
	private Scudo scudo;
	private ImageIcon background = new ImageIcon("image/backgroundSkin.jpg");
	private Timer timer;
	private static int cattivone = 30;
	private int numvite = 3;
	private int level = 1;
	private int score = 0;
	private int markerX, markerY;
	Random r = new Random();
	private boolean canPiuPiu = true;
	private boolean cattivocanpiu = true;
	private boolean hitMarker=true;
	private ArrayList<Cattivone> listaCattivoni = new ArrayList();
	private ArrayList<Nave> vite = new ArrayList();
	private ArrayList<Scudo> scudi = new ArrayList();
	private ArrayList<PiuPiu> piuCattivi = new ArrayList();

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
		//cattivi normali
		if (level != 3 && level != 6 && level != 9 && level != 12) {
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 5; j++) {
					tmpCattivone = new Cattivone((20 + (i * 100)), (20 + (j * 60)), level, 0, null, j, 40, 40);
					listaCattivoni.add(tmpCattivone);
				}
			}
		}
		
		//Cattivoni
		if (level == 3 || level == 6 || level == 9 || level == 12) {
			tmpCattivone = new Cattivone(20, 20, 3, 0, null, 100, 150, 150);
			listaCattivoni.add(tmpCattivone);
		}
		
		//nave
		ship = new Nave(375, 700, null);
		//conteggio vite
		for (int column = 0; column < numvite; column++) {
			vita = new Nave(48 + (column * 20), 770, Color.WHITE);
			vite.add(vita);
		}

		// Sets the values for 3 rows and 3 columns of shields
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				scudo = new Scudo(100 + (column * 250), 650 - (row * 10), Color.RED, 70, 10);
				scudi.add(scudo);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		
		background.paintIcon(null, g, 0, -150);
		//+100 a nemico colpito
		if (piu != null) {
			if (hitMarker) {
				g.setColor(Color.WHITE);
				if (level != 3 && level != 6 && level != 9 && level != 12) {
					g.drawString("+ 100", markerX + 20, markerY -= 1);
				} else {
					g.drawString("- 1", markerX + 75, markerY += 1);
				}
			}
		}
		
		//disegna tre tipi di nemici
		try {
			for (int index = 0; index < listaCattivoni.size(); index++) {
				listaCattivoni.get(index).paint(g);
			}
		} catch (IndexOutOfBoundsException e) {
		}
		ship.paint(g);

		//disegna gli scudi
		for (int index = 0; index < scudi.size(); index++) {
			scudi.get(index).paint(g);
		}
		
		//prova a disegnare il proiettile
		if (piu != null) {
			piu.paint(g);
		}
		//genera spari casuali dai nemici
		if (level != 3 && level != 6 && level != 9 && level != 12) {
			if (cattivocanpiu) {
				for (int index = 0; index < listaCattivoni.size(); index++) {
					if (r.nextInt(30) == index) {
						badPiu = new PiuPiu(listaCattivoni.get(index).getX(), listaCattivoni.get(index).getY(), 0, -4,
								Color.YELLOW);
						piuCattivi.add(badPiu);
					}
					cattivocanpiu = false;
				}
			}
		}
		//genera spari piu veloci per Cattivoni
		if (level == 3 || level == 6 || level == 9 || level == 12) {
			if (cattivocanpiu) {
				for (int index = 0; index < listaCattivoni.size(); index++) {
					if (r.nextInt(5) == index) {
						badPiu = new PiuPiu(listaCattivoni.get(index).getX() + 75,
								listaCattivoni.get(index).getY() + 140, 0, 0, Color.YELLOW);
						badPiu2 = new PiuPiu(listaCattivoni.get(index).getX(), listaCattivoni.get(index).getY() + 110,
								0, 0, Color.YELLOW);
						badPiu3 = new PiuPiu(listaCattivoni.get(index).getX() + 150,
								listaCattivoni.get(index).getY() + 110, 0, 0, Color.YELLOW);
						piuCattivi.add(badPiu);
						piuCattivi.add(badPiu2);
						piuCattivi.add(badPiu3);
					}
					cattivocanpiu = false;
				}
			}
		}
//disegna i proiettili nemici
		for (int index = 0; index < piuCattivi.size(); index++) {
			piuCattivi.get(index).paint(g);
		}

		// Score
		g.setColor(Color.WHITE);
		g.drawString("Score: " + score, 260, 20);
		// Life
		g.setColor(Color.WHITE);
		g.drawString("Lives:", 11, 780);
		for (int index = 0; index < vite.size(); index++) {
			vite.get(index).lifeDraw(g);
		}
		// Sets level display
		g.setColor(Color.WHITE);
		g.drawString("Level " + level, 750, 20);
		// Draws a health display for boss level
		if (level == 3 || level == 6 || level == 9 || level == 12) {
			g.setColor(Color.WHITE);
			g.drawString("Boss Health: " + cattivone, 352, 600);
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
//ciao
		// PiuPiu
		if (piu != null) {
			piu.move();
			if (piu.getY() < 0) {
				canPiuPiu = true;
			}
			// Cattivo colpito
			for (int i = 0; i < listaCattivoni.size(); i++) {
				if (piu.isBoom(listaCattivoni.get(i))) {
					piu = new PiuPiu(0, 0, 0, 0, null);
					canPiuPiu = true;
					// Updates score for normal levels
					if (level != 3 && level != 6 && level != 9 && level != 12) {
						score += 100;
						hitMarker = true;
						markerX = listaCattivoni.get(i).getX(); // Gets positions that the "+ 100" spawns off of
						markerY = listaCattivoni.get(i).getY();
						listaCattivoni.remove(i);
					}
				if (level == 3 || level == 6 || level == 9 || level == 12) {
					hitMarker=true;
					markerX=listaCattivoni.get(i).getX();
					markerY=listaCattivoni.get(i).getY()+165;
					cattivone-=1;
					if(cattivone==0) {
						listaCattivoni.remove(i);
						score+=9000;
				}
				}
				}
			}

//Nave colpisce scudo
			for (int index = 0; index < scudi.size(); index++) {
				if (piu.isBoom(scudi.get(index))) {
					// Each if statement changes color of the shield, indicating "strength"
					// STRONG
					if (scudi.get(index).getColor() == Color.RED) {
						scudi.get(index).setColor(Color.ORANGE);
						piu = new PiuPiu(0, 0, 0, 0, null);
						canPiuPiu = true;
						// GOOD
					} else if (scudi.get(index).getColor() == Color.ORANGE) {
						scudi.get(index).setColor(Color.YELLOW);
						piu = new PiuPiu(0, 0, 0, 0, null);
						canPiuPiu = true;
						// OKAY
					} else if (scudi.get(index).getColor() == Color.YELLOW) {
						scudi.get(index).setColor(Color.WHITE);
						piu = new PiuPiu(0, 0, 0, 0, null);
						canPiuPiu = true;
						// WEAK, BREAKS ON HIT
					} else if (scudi.get(index).getColor() == Color.WHITE) {
						scudi.remove(index);
						piu = new PiuPiu(0, 0, 0, 0, null);
						canPiuPiu = true;
					}
				}
			}
		}
//movimento proiettili cattivi
		if (level != 3 && level != 6 && level != 9 && level != 12) {
			if (badPiu != null) {
				for (int index = 0; index < piuCattivi.size(); index++) {
					piuCattivi.get(index).move();
					if (piuCattivi.get(index).getY() > 800) {
						piuCattivi.remove(index);
					}
				}
			}
		}
//movimento proiettili cattivoni(piu forti)
		if (level == 3 || level == 6 || level == 9 || level == 12) {
			if (badPiu != null) {
				for (int index = 0; index < piuCattivi.size(); index++) {
					piuCattivi.get(index).setSpeedY(-2 * level); // Boss beam
					piuCattivi.get(index).move();// speed will // increase each // level
					if (piuCattivi.get(index).getY() > 800) {
						piuCattivi.remove(index);
					}
				}
			}
		}

//Cattivo colpisce Scudo
		try {
			for (int j = 0; j < scudi.size(); j++) {
				for (int index = 0; index < piuCattivi.size(); index++) {
					if (piuCattivi.get(index).isBoom(scudi.get(j))) {
						// STRONG
						if (scudi.get(j).getColor() == Color.RED) {
							scudi.get(j).setColor(Color.ORANGE);
							piuCattivi.remove(index);
							// GOOD
						} else if (scudi.get(j).getColor() == Color.ORANGE) {
							scudi.get(j).setColor(Color.YELLOW);
							piuCattivi.remove(index);
							// OKAY
						} else if (scudi.get(j).getColor() == Color.YELLOW) {
							scudi.get(j).setColor(Color.WHITE);
							piuCattivi.remove(index);
							// WEAK, BREAKS ON HIT
						} else if (scudi.get(j).getColor() == Color.WHITE) {
							scudi.remove(j);
							piuCattivi.remove(index);
						}
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {
		}
//cattivo colpisce nave
		for (int i = 0; i < piuCattivi.size(); i++) {
			if (piuCattivi.get(i).isBoom(ship)) {
				piuCattivi.remove(i);
				vite.remove(vite.size() - 1);
			}
		}
//controllo per far sparare di nuovo i cattivi
		if (piuCattivi.isEmpty()) {
			cattivocanpiu = true;
		}
//Cattivo si schianta con scudo
		for (int i = 0; i < listaCattivoni.size(); i++) {
			for (int j = 0; j < scudi.size(); j++) {
				if (listaCattivoni.get(i).isBoom(scudi.get(j))) {
					listaCattivoni.remove(i);
					scudi.remove(j);
				}
			}
//alieni dopo punto critico(perdita vita e reset livello)
			if (listaCattivoni.get(i).getY() + 50 >= 750) {
				listaCattivoni.clear();
				scudi.clear();
				vite.clear();
				piuCattivi.clear();
				cattivone = 30;
				numvite -= 1;
				setup();
			}
		}

		if (listaCattivoni.isEmpty()) {
			piuCattivi.clear();
			scudi.clear();
			// bonusEnemyList.clear();
			vite.clear();
			level += 1;
			cattivone = 30;
			setup();
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