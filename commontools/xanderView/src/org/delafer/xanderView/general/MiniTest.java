package org.delafer.xanderView.general;

import org.delafer.xanderView.common.ImageSize;

public class MiniTest {

	public MiniTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		
		ImageSize monitor = new ImageSize(800,600);
		ImageSize pic = new ImageSize(8000, 6000);
		
		
		float requestedHeightScaling = ((float) monitor.height / (float) pic.height);
		float requestedWidthScaling = ((float) monitor.width / (float) pic.width);
		
		
		float actualScaling = Math.min(requestedHeightScaling, requestedWidthScaling);
		
			
	}

}
