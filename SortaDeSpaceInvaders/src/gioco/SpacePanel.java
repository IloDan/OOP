package gioco;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class SpacePanel extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Nave ship;
	private Nave vita;
	private Cattivone tmpCattivone;
	private PowerUp power;
	private PiuPiu piu;
	private PiuPiu badPiu, badPiu2, badPiu3;
	private Scudo scudo;
	private ImageIcon background = new ImageIcon("image/backgroundSkin.jpg");
	private Timer timer;
	private static int cattivone = 30;
	private int numvite = 3;
	private int level = 1;
	private int score = 0;
	private int highscore;
	private int markerX, markerY;
	private int piuspeed=7;
	Random r = new Random();
	
	File f=new File("Highscore.txt");
	//booleani
	private boolean existPwr;
	private boolean canPiuPiu = true;
	private boolean cattivocanpiu = true;
	private boolean hitMarker=true;
	private boolean pause=false;
	private boolean mute=false;
	private boolean hardcore=false;
	//liste
	private ArrayList<Cattivone> listaCattivoni = new ArrayList<Cattivone>();
	private ArrayList<Nave> vite = new ArrayList<Nave>();
	private ArrayList<Scudo> scudi = new ArrayList<Scudo>();
	private ArrayList<PiuPiu> piuCattivi = new ArrayList<PiuPiu>();
	//suoni
	private Suoni soundpiu=new Suoni("sounds/piupiu.wav");
	private Suoni soundbadpiu=new Suoni("sounds/badpiu.wav");
	private Suoni hitsound=new Suoni("sounds/hitmarker.wav");
	private Suoni soundboss=new Suoni("sounds/bossSound.wav");
	private Suoni scudisound=new Suoni("sounds/scudi.wav");
	private Suoni dannosound=new Suoni("sounds/danno.wav");
	private Suoni deathsound=new Suoni("sounds/deathsound.wav");
	private Suoni levelup=new Suoni("sounds/levelup.wav");
	private Suoni song=new Suoni("sounds/DragonForce - Through the Fire and Flames.wav");
	private Scanner fileScan,scan;
	
	public SpacePanel() {
		this.setSize(800, 800);
		this.setPreferredSize(new Dimension(800, 800));
		this.setBackground(Color.BLACK);
		addKeyListener(this);
		this.setup();
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	

	public static int getVitaCattivone() {
		return cattivone;
	}

	public void setup() {
		
		if (level == 1) {
            JOptionPane.showMessageDialog(null, "Benvenuto a Space Invaders!\n\n\n- Usa sinistra/destra per muoverti\n- Barra per sparare\n- P per mettere in pausa\n- M per mutare la base \n- La stella aumenta il rateo\n- Usa N per saltare il livello \n\nDIVERTITI!");
            int an = JOptionPane.showConfirmDialog(null, "Giocare in modalita HARDCORE?", "?", 0);
            if(an==0)
            	hardcore=true;
            else
            	hardcore=false;
		}
		
		//nemici normali
		if (level != 3 && level != 6 && level != 9 && level != 12) {
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 5; j++) {
					if(hardcore) {
						tmpCattivone = new Cattivone((20 + (i * 100)), (20 + (j * 60)), level+7, 0, null, j, 40, 40);
						listaCattivoni.add(tmpCattivone);
					}else {
						tmpCattivone = new Cattivone((20 + (i * 100)), (20 + (j * 60)), level, 0, null, j, 40, 40);
						listaCattivoni.add(tmpCattivone);
					}
				}
			}
		}
		
		//Boss
		if (level == 3 || level == 6 || level == 9 || level == 12) {
			tmpCattivone = new Cattivone(20, 20, 3, 0, null, 100, 150, 150);
			try {
				soundboss.play();
			} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}
			listaCattivoni.add(tmpCattivone);
		}
		
		if (level>7 && piuspeed<15 || hardcore)
			piuspeed=15;
			
		//nave
		ship = new Nave(375, 700, null);
		//conteggio vite
		for (int column = 0; column < numvite; column++) {
			vita = new Nave(48 + (column * 20), 10, Color.WHITE);
			vite.add(vita);
		}

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				scudo = new Scudo(100 + (column * 250), 650 - (row * 10), Color.blue, 70, 10);
				scudi.add(scudo);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		
		background.paintIcon(null, g, 0, -80);
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
						badPiu = new PiuPiu(listaCattivoni.get(index).getX(), listaCattivoni.get(index).getY(), 0, -4, Color.MAGENTA);
						piuCattivi.add(badPiu);
							try {
								soundbadpiu.play();
							} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
								e.printStackTrace();
							}	
					}
					cattivocanpiu = false;
				}
			}
		}
		//genera spari piu veloci per Boss
		if (level == 3 || level == 6 || level == 9 || level == 12) {
			if (cattivocanpiu) {
				for (int index = 0; index < listaCattivoni.size(); index++) {
					if (r.nextInt(5) == index) {
						badPiu = new PiuPiu(listaCattivoni.get(index).getX() + 75,listaCattivoni.get(index).getY() + 140, 0, 0, Color.YELLOW);
						badPiu2 = new PiuPiu(listaCattivoni.get(index).getX(), listaCattivoni.get(index).getY() + 110,0, 0, Color.YELLOW);
						badPiu3 = new PiuPiu(listaCattivoni.get(index).getX() + 150,listaCattivoni.get(index).getY() + 110, 0, 0, Color.YELLOW);
						piuCattivi.add(badPiu);
						piuCattivi.add(badPiu2);
						piuCattivi.add(badPiu3);
						try {
							soundbadpiu.play();
						} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}
					}
					cattivocanpiu = false;
				}
			}
		}
//disegna i proiettili nemici
		for (int index = 0; index < piuCattivi.size(); index++) {
			piuCattivi.get(index).paint(g);
		}
        if(existPwr)
        	power.paint(g);
// Score
		g.setColor(Color.WHITE);
		g.drawString("Score: " + score, 260, 20);
		
// Vite
		g.setColor(Color.WHITE);
		g.drawString("Lives:", 11, 20);
		for (int index = 0; index < vite.size(); index++) {
			vite.get(index).lifeDraw(g);
		}
// Livello
		g.setColor(Color.WHITE);
		g.drawString("Level " + level, 750, 20);
// vita boss
		if (level == 3 || level == 6 || level == 9 || level == 12) {
			g.setColor(Color.WHITE);
			g.drawString("Boss Health: " + cattivone, 352, 600);
		}
// Highscore
        g.setColor(Color.WHITE);
        g.drawString("Highscore: " + highscore, 440, 20);
	}
	
	
	
	

	public void updateG(int frame) {
		ship.move();
		
//Score
        try {
            fileScan = new Scanner(f);
            while (fileScan.hasNextInt()) {
                String pros = fileScan.nextLine();
                scan = new Scanner(pros);
                highscore = scan.nextInt();
            }
        } catch (FileNotFoundException e) {
        }

// Aggiorna highscore
        try {
            if (score > highscore) {
                String scoreString = Integer.toString(score);
                PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
                pw.write(scoreString);
                pw.close();
            }
        } catch (FileNotFoundException e) {
        }
// movimento Boss
		if ((listaCattivoni.get(listaCattivoni.size() - 1).getX()+ listaCattivoni.get(listaCattivoni.size() - 1).getSpeedX()) > 760|| (listaCattivoni.get(0).getX() + listaCattivoni.get(0).getSpeedX()) < 0) {
			for (int index = 0; index < listaCattivoni.size(); index++) {
				listaCattivoni.get(index).setSpeedX(listaCattivoni.get(index).getSpeedX() * -1);
				listaCattivoni.get(index).setY(listaCattivoni.get(index).getY() + 10);
			}
		} else {
			for (int index = 0; index < listaCattivoni.size(); index++) {
				listaCattivoni.get(index).move();
			}
		}
//movimento powerUp
		if(existPwr)
			power.move();
// movimento proiettili nave
		if (piu != null) {
			piu.move();
			if (piu.getY() < 0) {
				canPiuPiu = true;
			}
// nemico colpito
			for (int i = 0; i < listaCattivoni.size(); i++) {
				if (piu.isBoom(listaCattivoni.get(i))) {
					try {
						hitsound.play();
					} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}
					if(existPwr==false) {
						if(r.nextInt(6)==0) {		
							existPwr=true;
							power=new PowerUp(listaCattivoni.get(i).getX(), listaCattivoni.get(i).getY(), 0, -2, Color.yellow);
						}
					}
					piu = new PiuPiu(0, 0, 0, 0, null);
					canPiuPiu = true;
					
					if (level != 3 && level != 6 && level != 9 && level != 12) {
						score += 100;
						hitMarker = true;
						markerX = listaCattivoni.get(i).getX(); 
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
							score+=6000;
						}
					}
				}
			}
//Nave Raccoglie powerUp
			if(existPwr==true) {
				if (power.isBoom(ship)) {
					power=new PowerUp(0,0,0,0,null);
					existPwr=false;
					if(piuspeed<=40)
						piuspeed+=3;
						else if (piuspeed<60)
							piuspeed+=1;
					try {
						dannosound.play();
					} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}
				}
				if (power.getY() > 800) {
					existPwr=false;
				}
			}
//Nave colpisce scudo
			for (int index = 0; index < scudi.size(); index++) {
				if (piu.isBoom(scudi.get(index))) {
					if (scudi.get(index).getColor() == Color.blue) {
						scudi.get(index).setColor(Color.YELLOW);
						piu = new PiuPiu(0, 0, 0, 0, null);
						canPiuPiu = true;
						try {
							scudisound.play();
						} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
						}
					} else if (scudi.get(index).getColor() == Color.YELLOW) {
						scudi.get(index).setColor(Color.ORANGE);
						piu = new PiuPiu(0, 0, 0, 0, null);
						canPiuPiu = true;
						try {
							scudisound.play();
						} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
						}
					} else if (scudi.get(index).getColor() == Color.ORANGE) {
						scudi.get(index).setColor(Color.RED);
						piu = new PiuPiu(0, 0, 0, 0, null);
						canPiuPiu = true;
						try {
							scudisound.play();
						} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
						}
					} else if (scudi.get(index).getColor() == Color.RED) {
						scudi.remove(index);
						piu = new PiuPiu(0, 0, 0, 0, null);
						canPiuPiu = true;
						try {
							scudisound.play();
						} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
						}
					}
				}
			}
		}
//movimento proiettili nemici
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
//movimento proiettili Boss(piu forti)
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

//alieno colpisce Scudo
		try {
			for (int j = 0; j < scudi.size(); j++) {
				for (int index = 0; index < piuCattivi.size(); index++) {
					if (piuCattivi.get(index).isBoom(scudi.get(j))) {
						if (scudi.get(j).getColor() == Color.blue) {
							scudi.get(j).setColor(Color.YELLOW);
							piuCattivi.remove(index);
							try {
								scudisound.play();
							} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
							}
							
						} else if (scudi.get(j).getColor() == Color.YELLOW) {
							scudi.get(j).setColor(Color.ORANGE);
							piuCattivi.remove(index);
							try {
								scudisound.play();
							} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
							}
							
						} else if (scudi.get(j).getColor() == Color.ORANGE) {
							scudi.get(j).setColor(Color.RED);
							piuCattivi.remove(index);
							try {
								scudisound.play();
							} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
							}
							
						} else if (scudi.get(j).getColor() == Color.RED) {
							scudi.remove(j);
							piuCattivi.remove(index);
							try {
								scudisound.play();
							} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
							}
						}
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {
		}
//alieno colpisce nave
		for (int i = 0; i < piuCattivi.size(); i++) {
			if (piuCattivi.get(i).isBoom(ship)) {
				piuCattivi.remove(i);
				vite.remove(vite.size() - 1);
				try {
					dannosound.play();
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}
			}
		}
//controllo per far sparare di nuovo i nemici
		if (piuCattivi.isEmpty()) {
			cattivocanpiu = true;
		}
//alieno si schianta con scudo
		for (int i = 0; i < listaCattivoni.size(); i++) {
			for (int j = 0; j < scudi.size(); j++) {
				if (listaCattivoni.get(i).isBoom(scudi.get(j))) {
					scudi.remove(j);
				}
			}
//alieni dopo punto critico(perdita vita e reset livello)
			if (listaCattivoni.get(i).getY() + 50 >= 750) {		
				try {
					deathsound.play();
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
								e.printStackTrace();
				}
				int answer = JOptionPane.showConfirmDialog(null, "Giocare di nuovo?", "Hai perso con " + score + " punti", 0);         
	            if (answer == 0) {
	                vite.clear();
	                listaCattivoni.clear();
	                scudi.clear();
	                piuCattivi.clear();
	                score = 0;
	                level = 1;
	                cattivone = 30;
	                numvite = 3;
	                canPiuPiu = true;
	                cattivocanpiu = true;
	                existPwr=false;
	                piuspeed=7;
	                song.Pausa();
	                try {
						song.play();
					} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}
	                setup();
	            }
	            
	            if (answer == 1) {
	                System.exit(0);
	            }
			}
		}
		
        if (ship.isBoom) {
            int index = vite.size() - 1;
            vite.remove(index);
        } 
// Game over se finiscono le vite
        else if (vite.isEmpty()) {
        	try {
				deathsound.play();
			} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			}
// Giocare di nuovo?
            int answer = JOptionPane.showConfirmDialog(null, "Giocare di nuovo?", "Hai perso con " + score + " punti", 0);
            if (answer == 0) {
                vite.clear();
                listaCattivoni.clear();
                scudi.clear();
                piuCattivi.clear();
                score = 0;
                level = 1;
                cattivone = 30;
                numvite = 3;
                canPiuPiu = true;
                cattivocanpiu = true;
                existPwr=false;
                piuspeed=6;
                song.Pausa();
                try {
					song.play();
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}
              
                setup();
            }
            if (answer == 1) {
                System.exit(0);
            }
        }
//Prossimo livello reset 
		if (listaCattivoni.isEmpty()) {
			try {
				levelup.play();
			} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}
			piuCattivi.clear();
			scudi.clear();
			vite.clear();
			level += 1;
			existPwr=false;
			cattivone = 30;
			setup();
		}
	}
	
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e);
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			ship.setSpeedX(10);
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			ship.setSpeedX(-10);
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			if (canPiuPiu) {
				piu = new PiuPiu(ship.getX() + 22, ship.getY() - 20+piuspeed, ship.getSpeedX(), piuspeed, Color.green);
					try {
						soundpiu.play();
					} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {}
				canPiuPiu = false;
			}
		if (e.getKeyCode() == KeyEvent.VK_N) {
			listaCattivoni.clear();
			piuCattivi.clear();
			scudi.clear();
			vite.clear();
			level += 1;
			cattivone = 30;
			setup();
		}
		
		if(e.getKeyCode()== KeyEvent.VK_R) {
			int answer = JOptionPane.showConfirmDialog(null, "Vuoi resettare l'highscore?", "!", 0);
            if (answer == 0) {
                try {
                    String scoreString = Integer.toString(0);
                    PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
                    pw.write(scoreString);
                    pw.close();
                } catch (FileNotFoundException er) {
                }
            }
		}
		if (e.getKeyCode() == KeyEvent.VK_P) {
			if (pause) {
				timer.start();
				pause=false;
				song.Riprendi();
			}else {
				timer.stop();
				song.Pausa();
				pause=true;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_M) {
				if (mute) {
					mute=false;
					song.Riprendi();
				}else {
					song.Pausa();
					mute=true;
				}	
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
	public void actionPerformed(ActionEvent e) {}
	

//metodo che starta il timer e fa partire le animazioni
	public void start() {
		try {
			song.play();
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
			e1.printStackTrace();
		}
//Imposta un timer da far ripetere ogni 8 ms(120 fps)
		timer = new Timer(1000 / 120, new ActionListener() {
			private int frame = 0;
//Aggiorna lo stato del gioco e ridisegna lo schermo
			@Override
			public void actionPerformed(ActionEvent e) {
				updateG(frame++);
				repaint();
//				System.out.println(frame);
			}
		});
		timer.setRepeats(true);
		timer.start();
	}
}