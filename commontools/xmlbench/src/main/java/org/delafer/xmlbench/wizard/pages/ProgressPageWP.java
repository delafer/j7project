/**
 * 
 */
package org.delafer.xmlbench.wizard.pages;

import org.delafer.xmlbench.config.Helpers;
import org.delafer.xmlbench.wizard.SmartWizard;
import org.delafer.xmlbench.wizard.buttons.ButtonManager;
import org.delafer.xmlbench.wizard.buttons.PageButtonData;
import org.delafer.xmlbench.wizard.job.ThreadController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

/**
 * @author Alexander Tavrovsky
 *
 */
public class ProgressPageWP extends AbstractWizardPage{

	private ThreadController thr;

	/**
	 * 
	 */
	public ProgressPageWP(SmartWizard wizard) {
		super(wizard);
	}

	@Override
	public void drawComposite(Composite composite) {
		composite.setLayout(new FillLayout());
		ProgressPage cmp = new ProgressPage(composite, SWT.NONE);
		this.setBasePage(cmp);
	}
	
	

	@Override
	public void onExit() {
		if (thr != null) thr.stop();
		super.onExit();
	}

	@Override
	public void addButtons() {
		
		thr = new ThreadController((ProgressPage)cmp);
		
		PageButtonData startBtn = new PageButtonData(ButtonManager.BTN_PRG_START) {

			public void onKeyPress(Event event) {
				//System.out.println("Pressed Start");
				thr.start();
				
			}
		};
		
		PageButtonData pauseBtn = new PageButtonData(ButtonManager.BTN_PRG_PAUSE) {

			public void onKeyPress(Event event) {
				//System.out.println("Pressed pause");
				thr.pause();
			}
		};
		
		
		PageButtonData stopBtn = new PageButtonData(ButtonManager.BTN_PRG_STOP) {

			public void onKeyPress(Event event) {
				//System.out.println("Pressed Stop");
				thr.stop();
			}
		};
		
		PageButtonData gcBtn = new PageButtonData(ButtonManager.BTN_PRG_GC) {

			public void onKeyPress(Event event) {
				Helpers.freeMemory();
			}
		};
		
		addButton(startBtn);
		addButton(pauseBtn);
		addButton(stopBtn);
		addButton(gcBtn);
	}
	
	
	
}
