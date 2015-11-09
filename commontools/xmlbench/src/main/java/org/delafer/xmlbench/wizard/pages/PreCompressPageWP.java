/**
 * 
 */
package org.delafer.xmlbench.wizard.pages;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.delafer.xmlbench.compressors.CompressionFactory;
import org.delafer.xmlbench.compressors.ICompressor;
import org.delafer.xmlbench.config.Config;
import org.delafer.xmlbench.resources.CompressedFile;
import org.delafer.xmlbench.resources.Convertors;
import org.delafer.xmlbench.resources.FilesFactory;
import org.delafer.xmlbench.resources.IFileAbstract;
import org.delafer.xmlbench.resources.SourceFactory;
import org.delafer.xmlbench.wizard.SmartWizard;
import org.delafer.xmlbench.wizard.buttons.tasks.AsyncButtonTask;
import org.delafer.xmlbench.wizard.buttons.tasks.IButtonTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * @author Alexander Tavrovsky
 *
 */
public class PreCompressPageWP extends AbstractWizardPage{

	/**
	 * 
	 */
	public PreCompressPageWP(SmartWizard wizard) {
		super(wizard);
	}

	@Override
	public void drawComposite(Composite composite) {
		composite.setLayout(new FillLayout());
		PreCompressPage cmp = new PreCompressPage(composite, SWT.NONE);
		this.setBasePage(cmp);
	}
	
	private static final int BUF_SIZE = 2048;

	@Override
	public void onEnter() {
		super.onEnter();
		
		IButtonTask task = new IButtonTask() {
			
			public boolean runTask() throws Exception {
				
				final IFileAbstract source = FilesFactory.getFile(Config.instance().selectedFile);
				
				InputStream srcIS = source.openInputStream();
				
				ByteArrayOutputStream destOS = new ByteArrayOutputStream((int)source.getSize());
				
				//DummyCompressionOutputStream destOS= new DummyCompressionOutputStream();
				final ICompressor cmp= CompressionFactory.getSelectedCompressor();
				
				OutputStream comprStream =   cmp.compressor(destOS);
				
				byte[] buf = new byte[BUF_SIZE];
				
				int read;
				while ((read = srcIS.read(buf))>0) {
					comprStream.write(buf, 0, read);
					Thread.yield();
				}
				comprStream.flush();
				comprStream.close();
				
				destOS.flush();
				
				CompressedFile compressedFile = new CompressedFile(destOS.toByteArray(), source);
				destOS.close();
				
				SourceFactory.setData(source, compressedFile);
				
				final long uncSize = source.getSize();
				final long compSize = compressedFile.getSize();
//				System.out.println("Original: "+source.getSize()+" compressed: "+compressedFile.getSize());
				final double ratio = uncSize != 0l ? ((double)compSize / (double)uncSize)*100d : 100d;
				
				Config.instance().sizeCompressed = compSize;
				Config.instance().ratioCompresion = ratio;
				Config.instance().sizedSaved = uncSize - compSize;
				
				Display.getDefault().asyncExec(new Runnable() {
		            public void run() {
		            	
		            	PreCompressPage page = (PreCompressPage)getBasePage();
		            	
		            	if (page == null || page.isDisposed()) return ;
		            	
		            	page.txtComprName.setText(cmp.getName());
		            	page.txtFileName.setText(source.getName());
		            	page.txtUncompSize.setText(Convertors.autoSize(uncSize));
		            	page.txtCompSize.setText(Convertors.autoSize(compSize));
		            	page.txtRatio.setText(Convertors.formatValue(ratio)+" %");
		            	page.txtSizeSaved.setText(Convertors.autoSize(uncSize - compSize));
		            	page.ratioBar.setSelection((int) ratio);
		            }
		         });
				
				
				
				return SourceFactory.isInitialized();
			}
		};
		
		AsyncButtonTask.doTask(task, AsyncButtonTask.NEXT_BUTTON_ID);
	}

	
	
}
