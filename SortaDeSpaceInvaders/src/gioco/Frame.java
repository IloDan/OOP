package gioco;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Frame extends JFrame{
	private static final long serialVersionUID = 1L;
	private SpacePanel game;
	
	public Frame (){
		super("SpaceInvaders");
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//Crea un istanza della classe gioco e attiva il double buffering per rendere piu fluide le animazioni
        game = new SpacePanel();
        game.setDoubleBuffered(true);
        
        this.setSize(800,800);
        this.add(game);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
//avvia il gioco
        game.start();
        
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				 new Frame().setVisible(true);			
			}
		});
	}
}
