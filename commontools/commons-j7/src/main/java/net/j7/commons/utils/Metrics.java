package net.j7.commons.utils;

import java.io.PrintStream;

public final class Metrics {

	private static final String TIME = "Time: ";

	public long start;
	int counter;


	private static transient boolean disabled = false;
	private static transient PrintStream  stream = System.out;

	private Metrics() {
		restart();
	}

	public final static Metrics start() {
		return new Metrics();
	}


	public final void measure() {
		this.measure(TIME);
	}

	public final void restart() {
		this.start = System.currentTimeMillis();
		counter = 0;
	}

	public final void measure(String text) {
		if (disabled) return ;
		long startPrev = this.start;
		this.start = System.currentTimeMillis();
		counter++;

		StringBuilder sb = new StringBuilder();
		sb.append(counter).append(") ");
		sb.append(text).append((start - startPrev));
		stream.println(sb);

	}

	public final static void enable(boolean state) {
		Metrics.disabled = !state;
	}

	public final static void usePrintStream(PrintStream  prnStream) {
		Metrics.stream = prnStream;
	}

}
