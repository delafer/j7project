package org.delafer.xmlbench.wizard.buttons.tasks;

import org.delafer.xmlbench.wizard.buttons.ButtonManager;


public class AsyncButtonTask {

	public static final int[] NEXT_BUTTON_ID = new int[] {ButtonManager.UID_NEXT_BTN};

	/**
	 * @param args
	 */
	public static void doTask(IButtonTask task, int[] buttonsArr) {
		ButtonsEnabler enabler = new ButtonsEnabler(task, buttonsArr);
		enabler.onTaskStart();
		executeAsync(new AsyncCommand(enabler));
	}
	
	
	public static void executeAsync(AsyncCommand command) {
		new Thread(command).start();
	}
	
	public static class AsyncCommand implements Runnable {
		
		private ButtonsEnabler enabler;
		
		public AsyncCommand(ButtonsEnabler enabler) {
			this.enabler = enabler; 
		}


		public void run() {
			try {
				boolean ok = enabler.doTask();
				if (ok) enabler.onTaskSuccess();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
