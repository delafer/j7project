package org.delafer.xanderView.sound;

import java.io.*;

import net.j7.commons.streams.ReusableStream;

import com.sun.media.sound.JavaSoundAudioClip;

public class SoundBeep {
	void beep() {
		try {
			InputStream is = SoundBeep.class.getResourceAsStream("sound01.wav");
			System.out.println(is);
			JavaSoundAudioClip clip = new JavaSoundAudioClip(is);
			clip.play();


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SoundBeep b = new SoundBeep();
		b.beep();
	}
}
