package org.delafer.xanderView.file;


public class FilePointer {
	
	String[] fileList;
	int position;

	private static int getPosition(String[] fileList, String currentFile) {
		
		String nameToFind = FileUtils.getFileName(currentFile);
		for (int i = 0; i < fileList.length; i++) {
			String entry = fileList[i];
			String entryName = FileUtils.getFileName(entry);
			if (nameToFind.equals(entryName)) {
				System.out.println(nameToFind+" vs "+entryName);
				return i;
			}
		}
		
		return 0;
		
		
	}
	
	public FilePointer(String[] fileList, String currentFile) {
		this.fileList = fileList;
		this.position = getPosition(fileList, currentFile);
	}
	
	public String next() {
		position++;
		if (position>=fileList.length) position = 0;
		return fileList[position];
	}
	
	public String current() {
		return fileList[position];
	}
	
	public String prev() {
		position--;
		if (position<0) position = fileList.length-1;
		return fileList[position];
	}
	
	
//	public static class FileData {
//		String fullName;
//		String nameOnly;
//		
//		
//	}
	
}
