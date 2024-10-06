package vavi.awt.image.jna.avif;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : avif/avif.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class avifImageTiming extends Structure {
	/** timescale of the media (Hz) */
	public long timescale;
	/** presentation timestamp in seconds (ptsInTimescales / timescale) */
	public double pts;
	/** presentation timestamp in "timescales" */
	public long ptsInTimescales;
	/** in seconds (durationInTimescales / timescale) */
	public double duration;
	/** duration in "timescales" */
	public long durationInTimescales;
	public avifImageTiming() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("timescale", "pts", "ptsInTimescales", "duration", "durationInTimescales");
	}
	/**
	 * @param timescale timescale of the media (Hz)<br>
	 * @param pts presentation timestamp in seconds (ptsInTimescales / timescale)<br>
	 * @param ptsInTimescales presentation timestamp in "timescales"<br>
	 * @param duration in seconds (durationInTimescales / timescale)<br>
	 * @param durationInTimescales duration in "timescales"
	 */
	public avifImageTiming(long timescale, double pts, long ptsInTimescales, double duration, long durationInTimescales) {
		super();
		this.timescale = timescale;
		this.pts = pts;
		this.ptsInTimescales = ptsInTimescales;
		this.duration = duration;
		this.durationInTimescales = durationInTimescales;
	}
	public avifImageTiming(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends avifImageTiming implements Structure.ByReference {
	}

    public static class ByValue extends avifImageTiming implements Structure.ByValue {
	}
}