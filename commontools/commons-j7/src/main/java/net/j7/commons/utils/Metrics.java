package net.j7.commons.utils;

public class Metrics {

	public long start;
	int counter;

	private Metrics() {
		this.start = System.currentTimeMillis();
		counter = 0;
	}

	public static Metrics start() {
		return new Metrics();
	}


	public void measure() {
		this.measure("Time: ");
	}

	public void measure(String text) {
		long startPrev = this.start;
		this.start = System.currentTimeMillis();
		counter++;

		StringBuilder sb = new StringBuilder();
		sb.append(counter).append(") ");
		sb.append(text).append((start - startPrev));
		System.out.println(sb);

	}

}
