package org.delafer.xmlbench.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ResourcesRepository {
	
	public static InputStream getInternalFileStream(String name) {
		return ResourcesRepository.class.getResourceAsStream(name);
	}
	
	
	public static InputStream getExternalFileStream(String name) {
		
		try {
			FileInputStream fs = new FileInputStream(name);
			return fs;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
