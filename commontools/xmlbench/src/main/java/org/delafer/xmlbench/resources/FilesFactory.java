package org.delafer.xmlbench.resources;

public class FilesFactory {
	
	
	private static IFileAbstract[] files;
	
	static {
		files = new IFileAbstract[5];
		files[0] = new InternalFile("xml/test1.xml");
		files[1] = new InternalFile("xml/test2.xml");
		files[2] = new InternalFile("xml/test3.xml");
		files[3] = new InternalFile("xml/test4.xml");
		files[4] = new ExternalFile(null);
	}
	
	public static ExternalFile getUserDefinedFile() {
		return (ExternalFile)files[4];
	}
	
	public static IFileAbstract[] getFiles() {
		return files;
	}
	
	public static IFileAbstract getFile(int num) {
		return files[num];
	}
}
