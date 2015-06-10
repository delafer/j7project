package net.j7.commons.jni;

import net.j7.commons.strings.StringUtils;

public class LibraryLoader {

	public LibraryLoader() {
		// TODO Auto-generated constructor stub
	}

	public enum ArchVM {x32, x64};

	public enum OS {Windows, Linux, MacOS, SunOS, Unknown};




	public static ArchVM getDataModel() {
		String prop = System.getProperty ("sun.arch.data.model"); //$NON-NLS-1$
		if (StringUtils.empty(prop)) prop = System.getProperty ("com.ibm.vm.bitmode"); //$NON-NLS-1$
		if (StringUtils.empty(prop)) prop = System.getProperty ("os.arch"); //$NON-NLS-1$

		return prop.contains("64") ? ArchVM.x64 : ArchVM.x32;

	}

	public OS getPlatform() {
		String osName = System.getProperty("os.name").toLowerCase();

		if (osName.contains("linux")) return OS.Linux;
		if (osName.startsWith("win")) return OS.Windows;
		if (osName.contains("mac os") || osName.contains("macos") || osName.contains("darwin")) return OS.MacOS;
		if ((osName.contains("solaris")) || (osName.contains("sunos")))return OS.SunOS;
		return OS.Unknown;
	}

	public static void main(String[] args) {
		LibraryLoader ll = new LibraryLoader();
		System.out.println(ll.getPlatform());
		System.out.println(ll.getDataModel());
	}

}
