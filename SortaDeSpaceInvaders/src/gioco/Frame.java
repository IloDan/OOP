package gioco;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Frame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SpacePanel game;
	public Frame (){
		super("Minghiate");
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        // Create an instance of the Game class and turn on double buffering
        //  to ensure smooth animation
        game = new SpacePanel();
        game.setDoubleBuffered(true);
        this.setSize(800,800);
        // Add the Breakout instance to this frame's content pane to display it
    this.add(game);
  //       this.getContentPane().add(game); 
       this.pack();
     this.setResizable(false);
    // this.setFocusable(true);
       this.setLocationRelativeTo(null);
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
