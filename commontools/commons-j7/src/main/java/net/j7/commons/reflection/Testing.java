package net.j7.commons.reflection;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.annotation.XmlRootElement;

public class Testing {

	public Testing() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ModulesLoader loader = new ModulesLoader() {


			@Override
			public void checkClass(Class<?> ldClass) {
				if (ldClass.isAnnotationPresent(XmlRootElement.class)) System.out.println(ldClass.getName());
			}

		};
		try {
			loader.findExt("de");
			loader.findExt("org");
			loader.findExt("net");
			loader.findExt("com");
			loader.findExt("java");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

