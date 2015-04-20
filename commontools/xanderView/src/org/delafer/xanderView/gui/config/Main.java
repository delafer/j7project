package org.delafer.xanderView.gui.config;


public class Main {

	public static void main(String[] args) {

		ApplConfiguration cfg = ApplConfiguration.instance();
		cfg.set("abc", "1235");

		try {
		Thread.currentThread().sleep(20000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("exiting");

	}

}
