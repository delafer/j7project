package org.delafer.xanderView.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.sun.media.sound.JavaSoundAudioClip;

public class SoundBeep {
	void beep() {
		try {
			JavaSoundAudioClip clip = new JavaSoundAudioClip(new FileInputStream(new File("/tmp/go.wav")));
			clip.play();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
