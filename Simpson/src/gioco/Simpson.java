package gioco;

import java.awt.Canvas;
import java.awt.event.*;

import javax.swing.JFrame;

public class Simpson extends Canvas implements Runnable{

	public static final String TITLE= "Simpson";
	public static final int WIDTH=1000;
	public static final int HEIGHT=WIDTH/4*3;
	
	public void start() {
		new Thread(this, "SimpsonMain-Thread").start();
	}
	public void stop() {
		System.exit(0);
		//stop
	}
	@Override
	public void run() {
		System.out.println("Running");
	}

	public static void main(String[] args) {
		Simpson game=new Simpson();
		JFrame frame=new JFrame(TITLE);
		frame.add(game);
		frame.setSize(WIDTH,HEIGHT);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.err.println("Uscita gioco");
				game.stop();
			}
		});
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
		game.start();
	}
	//simpson

}
