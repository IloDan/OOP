package gioco;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Simpson extends Canvas implements Runnable{

	public static final String TITLE= "Simpson";
	public static final int WIDTH=1000;
	public static final int HEIGHT=WIDTH/4*3;
	
	public boolean running;
	
	private void tick() {}
	private void render() {
		BufferStrategy bs=getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g=bs.getDrawGraphics();
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.dispose();
		bs.show();
	}
	private void start() {
		if(running)
			return;
		running=true;
		new Thread(this, "SimpsonMain-Thread").start();
	}
	private void stop() {
		if(!running)
			return;
		running=false;
		//stop
	}
	@Override
	public void run() {
		double target=60.0;
		double nsPerTick=1000000000.0/target;
		long lastTime=System.nanoTime();
		long timer=System.currentTimeMillis();
		double unprocessed=0.0;
		int fps=0;
		int tps=0;
		boolean canRender=false;
		
		while(running) {
			long now= System.nanoTime();
			unprocessed+=(now-lastTime)/nsPerTick;
			lastTime=now;
			
			if(unprocessed>=1.0) {
				tick();
				unprocessed--;
				tps++;
				canRender=true;
			}else canRender=false;
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(canRender) {
				render();
				fps++;
			}
			if(System.currentTimeMillis()-1000>timer) {
				timer+=1000;
				System.out.printf("FPS: %d| TPS: %d\n",fps,tps);
				fps=0;
				tps=0;
			}
		}
		System.exit(0);
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
}
