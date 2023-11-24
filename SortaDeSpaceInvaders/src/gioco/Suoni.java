package gioco;

import java.io.*;
import javax.sound.sampled.*;

public class Suoni extends Thread{
    private String fileName;
    private File url;
    private Clip clip;
    long cliptime;
    public Suoni(String wavfile){
        fileName = wavfile;
    }

    public void play() throws LineUnavailableException, UnsupportedAudioFileException, IOException{
        url = new File(fileName);
        clip = AudioSystem.getClip();

        AudioInputStream ais = AudioSystem.getAudioInputStream( url );
        clip.open(ais);
        clip.start();
    }
    public void Pausa() {
    	cliptime=clip.getMicrosecondPosition();
    	clip.stop();
    }
    public void Riprendi() {
    	clip.setMicrosecondPosition(cliptime);
    	clip.start();
    }
}