package de.creditreform.common.db;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.creditreform.common.cli.CliArgs;
import de.creditreform.common.db.batch.DocumentTransformer;
import de.creditreform.common.helpers.AbstractFileProcessor;
import de.creditreform.common.helpers.AbstractFileProcessor.FileInfo;
import de.creditreform.common.helpers.Args;
import de.creditreform.common.helpers.Convertors;
import de.creditreform.common.helpers.StringUtils;
import de.creditreform.common.helpers.TextFileUtils;
import de.creditreform.common.helpers.TextFileUtils.AbrstractFileParser;
import de.creditreform.common.helpers.TextFileUtils.TextWriter;

/**
 * The Class BatchExecutor.
 * @author tavrovsa
 */
public class DataUpdater {
	private static final Logger log = LoggerFactory.getLogger(DataUpdater.class);

	CliArgs args;

	private static Charset CHARSET_UTF8;
	static {
		CHARSET_UTF8 = Charset.forName("UTF-8");
	}



	/**
	 * Instantiates a new batch executor.
	 *
	 * @param conn the java.sql.Connection instance
	 * @param dbSchema the database schema
	 */
	public DataUpdater(CliArgs args) {

		this.args = args;

	}

	private String readFile( File file ) throws Exception {

		StringBuilder sb = new StringBuilder();
		AbrstractFileParser afp = new TextFileUtils.AbrstractFileParser(file.getAbsolutePath(), "UTF-8") {

			@Override
			public void processLine(String line, int lineNumber) throws Exception {
				sb.append(line);
				sb.append(StringUtils.LF_DOS);
			}

		};
		afp.read();

		return sb.toString();

//	    BufferedReader reader = new BufferedReader( new FileReader (file));
//	    String         line = null;
//	    StringBuilder  stringBuilder = new StringBuilder();
//
//	    while( ( line = reader.readLine() ) != null ) {
//	        stringBuilder.append( line );
//	        stringBuilder.append( StringUtils.LF_DOS );
//	    }
//
//	    reader.close();
//
//	    return stringBuilder.toString();
	}


	public void startJob() throws IOException {

		AbstractFileProcessor afp = new AbstractFileProcessor(args.getInputXml(), "*.xml") {

			@Override
			public void processFile(File file, FileInfo fileInfo) throws Exception {
				DocumentTransformer transformer = new DocumentTransformer(readFile(file));
				if (transformer.result().ok()) {
					writeFile(transformer, fileInfo);
					if (args.isPdfGeneration()) {
						writeFilePdf(transformer, fileInfo);
					}
				}
				else {
					log.info(Args.fill("Skipped. Unknown format file: %1", fileInfo.getPathRelative()+'\\'+fileInfo.getFileName()));
				}

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub

			}
		};

		afp.start();

	}


	public static Set<String> ALREADY_DONE = new HashSet<String>(128);

	private void forceMkDir(String targetDir) {
		 if (ALREADY_DONE.contains(targetDir)) return ;
	      final File folder = new File(targetDir);
	      if (!folder.exists()) {
	         try {
				FileUtils.forceMkdir(folder);
			} catch (IOException io) {io.printStackTrace();}
	      }
	      ALREADY_DONE.add(targetDir);
	}




	/**
	 * @param res
	 * @param prettyXml
	 * @throws IOException
	 */
	private void writeFile(DocumentTransformer transformer, FileInfo fileInfo) throws IOException {
		String crefo = transformer.result().getCrefoNr();
		String targetDir = de.creditreform.common.helpers.FileUtils.convertToSystemPath(args.getOutputXml() + fileInfo.getPathRelative() + '/');
		forceMkDir(targetDir);

		String newFileName = fileInfo.getFileName();

		if (!newFileName.toLowerCase().contains("mailboxentry")) {
			int lnd = newFileName.lastIndexOf("-");
			int fid = newFileName.indexOf("_");
			if (lnd > fid && lnd > 0 && fid > 0) {
				newFileName = newFileName.substring(0, fid+1) + "Name_"+crefo+ newFileName.substring(lnd);
				System.out.println(">>>"+newFileName);
			}
		}

		String fullName = targetDir+newFileName;

		log.info(Args.fill("Done. Processed file name: %1 (Size: %2)", fullName, Convertors.autoSize(fileInfo.getFile().length())));

		TextWriter tw = TextFileUtils.createTextWriter(fullName, "UTF-8", false);
		tw.write(transformer.getXml());
		tw.close();
	}

	private void writeFilePdf(DocumentTransformer transformer, FileInfo fileInfo) throws IOException {
		String crefo = transformer.result().getCrefoNr();
		String targetDir = de.creditreform.common.helpers.FileUtils.convertToSystemPath(args.getOutputPdf() + fileInfo.getPathRelative() + '/');
		forceMkDir(targetDir);

		String newFileName = fileInfo.getFileNameBase();

		if (!newFileName.toLowerCase().contains("mailboxentry")) {
			int lnd = newFileName.lastIndexOf("-");
			int fid = newFileName.indexOf("_");
			if (lnd > fid && lnd > 0 && fid > 0) {
				newFileName = newFileName.substring(0, fid+1) + "Name_"+crefo+ newFileName.substring(lnd);
			}
		}

		String fullName = targetDir+newFileName+".pdf";
		byte[] pdfContent = transformer.getPdf();
		log.info(Args.fill("Done. Processed file name: %1 (Size: %2)", fullName, Convertors.autoSize(pdfContent.length)));


		FileUtils.writeByteArrayToFile(new File(fullName), pdfContent);


	}



	/**
	 * Not null.
	 *
	 * @param value the value
	 * @return the string
	 */
	protected String notNull(String value) {
		return value != null ? value : "";
	}









}
