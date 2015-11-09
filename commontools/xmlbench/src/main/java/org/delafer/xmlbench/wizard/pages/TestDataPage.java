package org.delafer.xmlbench.wizard.pages;

import org.delafer.xmlbench.config.Config;
import org.delafer.xmlbench.dialogs.FileOpenDialog;
import org.delafer.xmlbench.resources.ExternalFile;
import org.delafer.xmlbench.resources.FilesFactory;
import org.delafer.xmlbench.resources.IFileAbstract;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TestDataPage extends AbstractPage {
	private Text txtFileName;
	private Text txtFilePath;
	
	private Button[] radioBtn;
	private Button selected;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TestDataPage(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		radioBtn = new Button[5];
		
		Label lblSelectedXmlFile = new Label(this, SWT.NONE);
		lblSelectedXmlFile.setText("Selected XML File to run benchmark");
		
		txtFileName = new Text(this, SWT.BORDER);
		txtFileName.setEditable(false);
		txtFileName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPath = new Label(this, SWT.NONE);
		lblPath.setText("Path");
		
		txtFilePath = new Text(this, SWT.BORDER);
		txtFilePath.setEditable(false);
		txtFilePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Group group = new Group(this, SWT.NONE);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Button button = new Button(group, SWT.RADIO);
		radioBtn[0] = button;
		radioBtn[0].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		radioBtn[1] = new Button(group, SWT.RADIO);
		radioBtn[1].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		radioBtn[2] = new Button(group, SWT.RADIO);
		radioBtn[2].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		radioBtn[3] = new Button(group, SWT.RADIO);
		radioBtn[3].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		radioBtn[4] = new Button(group, SWT.RADIO);
		radioBtn[4].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		radioBtn[4].setText("User defined data");
		
		SelectionAdapter selection = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button)e.widget;
				TestDataPage.this.selected = btn;
				IFileAbstract fileInf = (IFileAbstract) btn.getData();
				txtFileName.setText(fileInf.getName());
				txtFilePath.setText(fileInf.getPath());
			}
		};
		
		for (int i = 0; i < radioBtn.length; i++) {
			radioBtn[i].addSelectionListener(selection);
		}
		
		Button btnNewButton = new Button(group, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String path = FileOpenDialog.open();
				ExternalFile extFile = FilesFactory.getUserDefinedFile();
				extFile.setFullPath(path);
				radioBtn[4].setText(extFile.getInformation());
				selectButton(4);
			}
		});
		btnNewButton.setText("Select XML File");

	}
	
	@Override
	public void onEnter() {
		IFileAbstract[] files = FilesFactory.getFiles();
		for (int i = 0; i < 5; i++) {
			radioBtn[i].setText(files[i].getInformation());
			radioBtn[i].setData(files[i]);
			if ((i) == Config.instance().selectedFile) {
				selectButton(i);
			}
		}
	}

	private void selectButton(int i) {
		if (selected != null) selected.setSelection(false);
		radioBtn[i].setSelection(true);
		radioBtn[i].notifyListeners(SWT.Selection, null);
	}
	
	
	private int selectedRadio() {
		for (int i = 0; i < radioBtn.length; i++) {
			if (radioBtn[i].getSelection()) return i;
		}
		return Config.instance().selectedFile;
	}
	
	@Override
	public void onExit() {
	//	Config.instance().selectedFile = selectedRadio();
	}
	
	@Override
	public void beforeExit() {
		Config.instance().selectedFile = selectedRadio();
	}
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
