/**
 * 
 */
package org.delafer.xmlbench.wizard.pages;

import org.delafer.xmlbench.wizard.SmartWizard;
import org.delafer.xmlbench.wizard.buttons.tasks.AsyncButtonTask;
import org.delafer.xmlbench.wizard.buttons.tasks.IButtonTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Alexander Tavrovsky
 *
 */
public class CachePageWP extends AbstractWizardPage{

	/**
	 * 
	 */
	public CachePageWP(SmartWizard wizard) {
		super(wizard);
	}

	@Override
	public void drawComposite(Composite composite) {
		composite.setLayout(new FillLayout());
		CachePage cmp = new CachePage(composite, SWT.NONE);
		this.setBasePage(cmp);
		
	}

	@Override
	public void onEnter() {
		super.onEnter();
	}

}
