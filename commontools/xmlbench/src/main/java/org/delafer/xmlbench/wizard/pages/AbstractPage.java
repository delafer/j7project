/**
 *
 */
package org.delafer.xmlbench.wizard.pages;

import org.eclipse.swt.widgets.Composite;

/**
 * @author Alexander Tavrovsky
 *
 */
public abstract class AbstractPage extends Composite {

	/**
	 * @param parent
	 * @param style
	 */
	public AbstractPage(Composite parent, int style) {
		super(parent, style);
	}

	public void onEnter() {
	}

	public void onExit() {
	}

	public void beforeExit() {

	}

}
