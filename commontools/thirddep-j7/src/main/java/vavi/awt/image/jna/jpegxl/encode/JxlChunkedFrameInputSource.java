package vavi.awt.image.jna.jpegxl.encode;
import com.sun.jna.Callback;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.ptr.NativeLongByReference;
import vavi.awt.image.jna.jpegxl.JxlPixelFormat;


/**
 * This struct provides callback functions to pass pixel data in a streaming<br>
 * manner instead of requiring the entire frame data in memory at once.<br>
 * <i>native declaration : jxl/encode.h:776</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class JxlChunkedFrameInputSource extends Structure {
	/** C type : void* */
	public Pointer opaque;
	/** C type : get_color_channels_pixel_format_callback* */
	public JxlChunkedFrameInputSource.get_color_channels_pixel_format_callback get_color_channels_pixel_format;
	/** C type : get_color_channel_data_at_callback* */
	public JxlChunkedFrameInputSource.get_color_channel_data_at_callback get_color_channel_data_at;
	/** C type : get_extra_channel_pixel_format_callback* */
	public JxlChunkedFrameInputSource.get_extra_channel_pixel_format_callback get_extra_channel_pixel_format;
	/** C type : get_extra_channel_data_at_callback* */
	public JxlChunkedFrameInputSource.get_extra_channel_data_at_callback get_extra_channel_data_at;
	/** C type : release_buffer_callback* */
	public JxlChunkedFrameInputSource.release_buffer_callback release_buffer;
	/** <i>native declaration : jxl/encode.h</i> */
	public interface get_color_channels_pixel_format_callback extends Callback {
		void apply(Pointer opaque, JxlPixelFormat pixel_format);
	};
	/** <i>native declaration : jxl/encode.h</i> */
	public interface get_color_channel_data_at_callback extends Callback {
		Pointer apply(Pointer opaque, NativeLong xpos, NativeLong ypos, NativeLong xsize, NativeLong ysize, NativeLongByReference row_offset);
	};
	/** <i>native declaration : jxl/encode.h</i> */
	public interface get_extra_channel_pixel_format_callback extends Callback {
		void apply(Pointer opaque, NativeLong ec_index, JxlPixelFormat pixel_format);
	};
	/** <i>native declaration : jxl/encode.h</i> */
	public interface get_extra_channel_data_at_callback extends Callback {
		Pointer apply(Pointer opaque, NativeLong ec_index, NativeLong xpos, NativeLong ypos, NativeLong xsize, NativeLong ysize, NativeLongByReference row_offset);
	};
	/** <i>native declaration : jxl/encode.h</i> */
	public interface release_buffer_callback extends Callback {
		void apply(Pointer opaque, Pointer buf);
	};
	public JxlChunkedFrameInputSource() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("opaque", "get_color_channels_pixel_format", "get_color_channel_data_at", "get_extra_channel_pixel_format", "get_extra_channel_data_at", "release_buffer");
	}
	/**
	 * @param opaque C type : void*<br>
	 * @param get_color_channels_pixel_format C type : get_color_channels_pixel_format_callback*<br>
	 * @param get_color_channel_data_at C type : get_color_channel_data_at_callback*<br>
	 * @param get_extra_channel_pixel_format C type : get_extra_channel_pixel_format_callback*<br>
	 * @param get_extra_channel_data_at C type : get_extra_channel_data_at_callback*<br>
	 * @param release_buffer C type : release_buffer_callback*
	 */
	public JxlChunkedFrameInputSource(Pointer opaque, JxlChunkedFrameInputSource.get_color_channels_pixel_format_callback get_color_channels_pixel_format, JxlChunkedFrameInputSource.get_color_channel_data_at_callback get_color_channel_data_at, JxlChunkedFrameInputSource.get_extra_channel_pixel_format_callback get_extra_channel_pixel_format, JxlChunkedFrameInputSource.get_extra_channel_data_at_callback get_extra_channel_data_at, JxlChunkedFrameInputSource.release_buffer_callback release_buffer) {
		super();
		this.opaque = opaque;
		this.get_color_channels_pixel_format = get_color_channels_pixel_format;
		this.get_color_channel_data_at = get_color_channel_data_at;
		this.get_extra_channel_pixel_format = get_extra_channel_pixel_format;
		this.get_extra_channel_data_at = get_extra_channel_data_at;
		this.release_buffer = release_buffer;
	}
	public JxlChunkedFrameInputSource(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends JxlChunkedFrameInputSource implements Structure.ByReference {
		
	};
	public static class ByValue extends JxlChunkedFrameInputSource implements Structure.ByValue {
		
	};
}
