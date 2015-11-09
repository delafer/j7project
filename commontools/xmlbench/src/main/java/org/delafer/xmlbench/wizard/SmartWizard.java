package org.delafer.xmlbench.wizard;

import org.delafer.xmlbench.gui.FlowLayout;
import org.delafer.xmlbench.wizard.buttons.ButtonManager;
import org.delafer.xmlbench.wizard.buttons.PageButtonData;
import org.delafer.xmlbench.wizard.pages.AbstractWizardPage;
import org.delafer.xmlbench.wizard.pages.CachePageWP;
import org.delafer.xmlbench.wizard.pages.FirstPageWP;
import org.delafer.xmlbench.wizard.pages.PreCompressPageWP;
import org.delafer.xmlbench.wizard.pages.ProgressPageWP;
import org.delafer.xmlbench.wizard.pages.TestDataPageWP;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class SmartWizard extends Composite {

	
	private LinkedChain<AbstractWizardPage> chain = new LinkedChain<AbstractWizardPage>();
	private Composite cmpMain;
//	private Composite page0;
	private BufferedComposite bufferedCmp;
	private Button btnPrevious;
	private Button btnNext;
	private Composite cmpExtraButtons;
	
	public static final GridData gridData(int horizontalAlignment, int verticalAlignment, boolean fillHorizontal, boolean fillVertical, int horizontalWidth, int verticalWidth) {
		GridData ret = new GridData(horizontalAlignment, verticalAlignment, fillHorizontal, fillVertical, 1, 1);
		if (horizontalWidth != -1) ret.widthHint = horizontalWidth;
		if (verticalWidth != -1) ret.heightHint = verticalWidth;
		return ret;
	}
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SmartWizard(Composite parent, int style) {
		super(parent, style);
		
		
		GridLayout baseLayout = new GridLayout(1, false);
		setLayout(baseLayout);
		
		
		cmpMain = new Composite(this, SWT.NONE);
		cmpMain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		bufferedCmp = new BufferedComposite(cmpMain);
		
		
		Composite cmpProgress = new Composite(this, SWT.NONE);
		cmpProgress.setLayoutData(gridData(SWT.FILL, SWT.BOTTOM, true, false, -1, 1));
		cmpProgress.setLayout(new FillLayout(SWT.VERTICAL));
		
		
		Composite cmpButtons = new Composite(this, SWT.NONE);
		cmpButtons.setLayoutData(gridData(SWT.FILL, SWT.BOTTOM, true, false, -1, 40));
		
		cmpButtons.setLayout(new GridLayout(3, false));
		
		
		cmpExtraButtons = new Composite(cmpButtons, SWT.NONE);
		cmpExtraButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
		cmpExtraButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		
//		btnNewButton = new Button(cmpButtons, SWT.NONE);
//		btnNewButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
//		btnNewButton.setText("New Button");
		
		btnPrevious = ButtonManager.instance().createButton(cmpButtons, ButtonManager.BTN_PREV);
		btnPrevious.setLayoutData(gridData(SWT.RIGHT, SWT.CENTER, false, true, 90, -1));
		
		btnNext = ButtonManager.instance().createButton(cmpButtons, ButtonManager.BTN_NEXT);
		btnNext.setLayoutData(gridData(SWT.RIGHT, SWT.CENTER, false, true, 90, -1));
		
		Listener listener = new Listener() {
			
			public void handleEvent(Event event) {
				if (event.widget==btnNext) {
					SmartWizard.this.doNext();
				} else 
				if (event.widget==btnPrevious) {
					SmartWizard.this.doPrevious();
				};
				
			}
		};
		
		btnPrevious.addListener(SWT.Selection, listener);
		btnNext.addListener(SWT.Selection, listener);
		
	}
	
			
	public void doNext() {
		chain.getCurrent().getValue().beforeExit();
		if (!chain.getCurrent().getValue().nextAllowed()) return ;
		drawChain(chain.getCurrent().getChild());
		
	}
	
	public void doPrevious() {
		chain.getCurrent().getValue().beforeExit();
		drawChain(chain.getCurrent().getParent());
		
	}
	
	public void drawChain(LinkedChain<AbstractWizardPage>.Chain next) {
		if (next == null) return ;
		
		if (next != chain.getCurrent()) 
			chain.getCurrent().getValue().onExit();
		
		Composite cmp = bufferedCmp.getHidden();
		next.getValue().drawComposite(cmp);
		next.getValue().onEnter();
		bufferedCmp.swapComposite();
		
		chain.setCurrent(next);
		
		
		boolean isLast = (next.getChild() == null);
		boolean isFirst = (next.getParent() == null);
		btnNext.setEnabled(!isLast);
		btnNext.setVisible(!isLast);
		btnPrevious.setEnabled(!isFirst);
		btnPrevious.setVisible(!isFirst);
	}

	public void init() {
		chain.add(chain.newChain(new TestDataPageWP(this), 1));
		chain.add(chain.newChain(new FirstPageWP(this), 2));
		chain.add(chain.newChain(new PreCompressPageWP(this), 3));
		chain.add(chain.newChain(new CachePageWP(this), 4));
		chain.add(chain.newChain(new ProgressPageWP(this), 5));
		chain.setCurrent(chain.get(1));
		
		drawChain(chain.getCurrent());
		
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public Composite getUserButtonsPanel() {
		return this.cmpExtraButtons;
	}
	
	public void removeButtons() {
		ButtonManager.instance().removeDynamicButtons();
	}
	
	public void addButton(final PageButtonData button) {
		
		Button btn = ButtonManager.instance().createButton(getUserButtonsPanel(), button);
		
		Listener btnListener = new Listener() {
			public void handleEvent(Event event) {
				button.onKeyPress(event);
			}
		};
		
		btn.addListener(SWT.Selection, btnListener);
		getUserButtonsPanel().layout(true);
		
	}
	
	public static class BufferedComposite {

		private StackLayout layout;
		private Composite active;
		private Composite inactive;
		private Composite cmpMain;
		
		private BufferedComposite(Composite cmpMain) {
			this.cmpMain = cmpMain;
			
			this.layout = new StackLayout();
			this.cmpMain.setLayout(this.layout);
			this.active = new Composite(cmpMain, SWT.NONE);
			this.inactive = new Composite(cmpMain, SWT.NONE);
			this.layout.topControl = active;
		}
		
		public Composite getHidden() {
			return this.inactive;
		}
		
		public Composite getActive() {
			return this.active;
		}
		
		public void swapComposite() {
			final Composite tmp = this.active;
			this.active = this.inactive;
			this.inactive = tmp;
			//System.out.println("Active is:"+System.identityHashCode(active));
			this.layout.topControl = active;
			
			active.layout(true);
			this.cmpMain.layout();
			
			UIHelpers.disposeComposite(this.inactive, false);
			
		}
		
	}
}

