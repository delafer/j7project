package net.j7.commons.utils;

public class Metrics {

	public long start;

	private Metrics() {
		this.start = System.currentTimeMillis();
	}

	public static Metrics start() {
		return new Metrics();
	}

	public void end() {
		long end = System.currentTimeMillis();
		System.out.println(">>>"+(end - this.start));
	}

}
