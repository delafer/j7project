/**
 * 
 */
package org.delafer.xmlbench.wizard.pages;

import org.delafer.xmlbench.config.Config;
import org.delafer.xmlbench.resources.FilesFactory;
import org.delafer.xmlbench.resources.IFileAbstract;
import org.delafer.xmlbench.wizard.SmartWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;


/**
 * @author Alexander Tavrovsky
 *
 */
public class TestDataPageWP extends AbstractWizardPage{

	/**
	 * 
	 */
	public TestDataPageWP(SmartWizard wizard) {
		super(wizard);
	}

	@Override
	public void drawComposite(Composite composite) {
		composite.setLayout(new FillLayout());
		TestDataPage cmp = new TestDataPage(composite, SWT.NONE);
		this.setBasePage(cmp);
	}
	
	
	@Override
	public boolean nextAllowed() {
		IFileAbstract file = FilesFactory.getFile(Config.instance().selectedFile);
		
		boolean ok = file != null && file.isFileExists();
		
		if (!ok) {
			MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING);
		    messageBox.setMessage("To continue -> Please select a valid non empty test file");
			messageBox.open();
		}
		
		return ok;
	}

}
