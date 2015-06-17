package org.delafer.xanderView.sound;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import net.j7.commons.streams.Streams;

public class SoundBeep {

//	public  void beep2()
//	{ InputStream is = SoundBeep.class.getResourceAsStream("sound01.wav");
//	    try
//	    {
//	        Clip clip = AudioSystem.getClip();
//	        clip.open(AudioSystem.getAudioInputStream(is));
//	        clip.start();
//	    }
//	    catch (Exception exc)
//	    {
//	        exc.printStackTrace(System.out);
//	    }
//	}
	public final static void beep() {
		InputStream is = null;
		AudioInputStream stream= null;
		try {
			is = new BufferedInputStream(SoundBeep.class.getResourceAsStream("sound01.wav"));
		    AudioFormat format;
		    DataLine.Info info;
		    Clip clip;

		    stream = AudioSystem.getAudioInputStream(is);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			Streams.close(stream);
		}


	}

	public static void main(String[] args) {
		SoundBeep b = new SoundBeep();
		b.beep();
		try {
			Thread.currentThread().sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
