package net.j7.commons.jni;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import net.j7.commons.base.NotNull;
import net.j7.commons.io.*;
import net.j7.commons.io.SysInfo.ArchVM;
import net.j7.commons.io.SysInfo.OS;
import net.j7.commons.io.SysInfo.OSFamily;
import net.j7.commons.streams.Streams;


public class LibraryLoader {

	/**
	 * Lazy-loaded Singleton, by Bill Pugh *.
	 */
	private static final class Holder {
		/** The Constant INSTANCE. */
		private final static transient LibraryLoader INS = new LibraryLoader();
	}

	/**
	 * Gets the single instance of ResourcesDR.
	 * @return single instance of ResourcesDR
	 */
	public static final LibraryLoader instance() {
		return Holder.INS;
	}

	static Map<String, ArchVM> dmod;
	static Map<String, String> cnv64to32;
	private Map<String, Boolean> loaded;
	private OS os;
	private String arch;
	private ArchVM model;

	private String basePath;

	static {
		dmod = new HashMap<String, SysInfo.ArchVM>(9);
		dmod.put("ppc", ArchVM.x32);
		dmod.put("i386", ArchVM.x32);
		dmod.put("x86", ArchVM.x32);
		dmod.put("sparc", ArchVM.x32);

		dmod.put("ppc64", ArchVM.x64);
		dmod.put("ia64", ArchVM.x64);
		dmod.put("x86_64", ArchVM.x64);
		dmod.put("amd64", ArchVM.x64);
		dmod.put("sparcv9", ArchVM.x64);

		cnv64to32 = new HashMap<String, String>(4);
		cnv64to32.put("x86_64", "x86");
		cnv64to32.put("ppc64", "ppc");
		cnv64to32.put("amd64", "x86");
		cnv64to32.put("sparcv9", "sparc");
	}


	private LibraryLoader() {
		loaded = new HashMap<String, Boolean>();
		SysInfo si = SysInfo.instance();
		os = si.getOS();
		arch = getArch();
		model = si.getDataModel();
		basePath = getPath();
	}


	public String getExt() {
		switch (os) {
		case Windows:
		case Windows9xME:
		case WindowsNT5:
			return "dll";
		case MacOS:
			return "jnilib";
		default:
		case Linux:
		case UnixGeneric:
			return "so";
		}
	}

	public String getPath() {
		switch (os) {
		case Windows:
		case Windows9xME:
		case WindowsNT5:
			return "windows";
		case UnixGeneric:
		case Unknown:
			return "linux";
		case MacOS:
		case Linux:
		default:
			return os.name().toLowerCase();
		}
	}


	private String getArch() {

		String val = NotNull.string(SysInfo.instance().archOS()).toLowerCase();

		if (OSFamily.Unix.equals(os.family())) {
			if ("amd64".equals(val)) val = "x86_64";
			else
			if ("i386".equals(val)) val = "x86";
		}

		if (OSFamily.Windows.equals(os.family())) {
			if ("x86_64".equals(val)) val = "amd64";
			else
			if ("i386".equals(val)) val = "x86";
		}

		ArchVM vm = dmod.get(val);
		if (ArchVM.x64.equals(vm) && ArchVM.x32.equals(model)) {
			String ret = cnv64to32.get(val);
			if (null != ret) val = ret;
		}


		return val;
	}

	public FilePath getFSPath(String appName, String libraryName) {

		return FilePath.as().dir(SysInfo.instance().getAppDataDir(appName)).file(getJARPath(libraryName));


	}

	public String getJARPath(String libraryName) {
		StringBuilder sb = new StringBuilder();
		sb.append('/').append("libs");
		sb.append('/').append(basePath);
		sb.append('/').append(arch);
		sb.append('/').append(libraryName);
		sb.append('.').append(getExt());
		return sb.toString();
	}

	private boolean load(String name) {
		Boolean state = loaded.get(name);
		if (null == state) {
			state = Boolean.valueOf(load0(name));
			loaded.put(name, state);
		}
		return state;
	}

	private static String appName = "xanderView";
	private boolean load0(String name) {
		FilePath path = getFSPath(appName, name);
		String pathTxt = path.build();
		File fLib = new File(pathTxt);
		boolean load = true;
		if (!fLib.exists()) {
			InputStream is = getClass().getResourceAsStream(getJARPath(name));
			if (null != is) {
//				System.out.println("exists in jar: "+getJARPath(name));
				path.forceExists();
				path.build();
				copyLibrary(fLib, is);
//				System.out.println(pathTxt);
			} else {
				load = false;
//				System.out.println("doesn't exits: "+getJARPath(name));
			}

		}
		if (load) System.load(pathTxt);
		return load;
 	}


	private void copyLibrary(File fLib, InputStream is) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fLib);
			byte[] buffer = new byte[2048];
		    int bytesRead;
		    while ((bytesRead = is.read(buffer)) > 0) {
		        out.write(buffer, 0, bytesRead);
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		Streams.close(out);
		Streams.close(is);
	}


	public static void loadLibrary(String name) {
		LibraryLoader.instance().load(name);
	}

	public static void main(String[] args) {
		LibraryLoader.loadLibrary("liblz4-java");
		LibraryLoader.loadLibrary("lib7-Zip-JBinding");
		LibraryLoader.loadLibrary("liblz4-java");
	}

}
