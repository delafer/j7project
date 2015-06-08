package org.delafer.xanderView.gui.config;

public class Abc {

	public Abc() {
		// TODO Auto-generated constructor stub
	}

	public static int toPBV(int v, int maxVal) {
		if (v != 0) {
			v -= maxVal;
			if (v>=0) v++;
		}
		return v;
	}

	public static int fromPBV(int v, int maxVal) {
		if (v != 0) {
			if (v > 0) v--;
			v += maxVal;
		}
		return v;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 64; i++) {

			System.out.println(i +"="+(toPBV(i, 32)));
		}

	}

}
