package de.creditreform.common.db;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.creditreform.common.cli.CliArgs;
import de.creditreform.common.db.batch.DocumentTransformer;
import de.creditreform.common.helpers.AbstractFileProcessor;
import de.creditreform.common.helpers.AbstractFileProcessor.FileInfo;
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
				writeFile(transformer, fileInfo);

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub

			}
		};

		afp.start();

	}






	/**
	 * @param res
	 * @param prettyXml
	 * @throws IOException
	 */
	private void writeFile(DocumentTransformer transformer, FileInfo fileInfo) throws IOException {
		String crefo = transformer.getResult().getCrefoNr();
		TextWriter tw = TextFileUtils.createTextWriter(args.getOutputXml()+fileInfo.getFileName(), "UTF-8", false);
		tw.write(transformer.getPrettyXml());
		tw.close();
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
