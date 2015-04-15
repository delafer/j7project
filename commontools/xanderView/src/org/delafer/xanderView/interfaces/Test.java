package org.delafer.xanderView.interfaces;

import net.sf.sevenzipjbinding.SevenZip;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			 SevenZip.initSevenZipFromPlatformJAR();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
