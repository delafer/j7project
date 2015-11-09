package org.delafer.xmlbench.wizard.pages;

import org.delafer.xmlbench.wizard.SmartWizard;
import org.delafer.xmlbench.wizard.buttons.PageButtonData;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractWizardPage {
	
	
	private SmartWizard wizard;
	protected AbstractPage cmp;	
	
	public AbstractWizardPage(SmartWizard wizard) {
		this.wizard = wizard;
	}


	
	public abstract void drawComposite(Composite composite);
	
	public void setBasePage(AbstractPage page) {
		this.cmp = page;
	}
	
	protected AbstractPage getBasePage() {
		return this.cmp;
	}
	
	public void beforeExit() {
		getBasePage().beforeExit();
	}
	
	public void onEnter() {
		getBasePage().onEnter();
		addButtons();
	}
	
	public void onExit() {
		getBasePage().onExit();
		removeButtons();
	}
	
	public boolean nextAllowed() {
		return true;
	}
	
	
	public void addButtons() {
	}
	
	protected void addButton(PageButtonData button) {
		if (button == null) return ;
		wizard.addButton(button);
	}
	
	private void removeButtons() {
		wizard.removeButtons();
	}
	
	
	
}
