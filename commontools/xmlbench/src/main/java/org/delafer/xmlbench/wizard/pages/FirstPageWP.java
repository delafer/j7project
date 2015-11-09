/**
 * 
 */
package org.delafer.xmlbench.wizard.pages;

import org.delafer.xmlbench.wizard.SmartWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Alexander Tavrovsky
 *
 */
public class FirstPageWP extends AbstractWizardPage{

	/**
	 * 
	 */
	public FirstPageWP(SmartWizard wizard) {
		super(wizard);
	}

	@Override
	public void drawComposite(Composite composite) {
		composite.setLayout(new FillLayout());
		FirstPage cmp = new FirstPage(composite, SWT.NONE);
		this.setBasePage(cmp);
	}

}
