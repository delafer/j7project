package de.creditreform.common.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import de.creditreform.common.helpers.AbstractZipParser.FileUtils;
import de.creditreform.common.helpers.StringUtils;

public class CliArgs {


	private boolean pdfGeneration;
	private String outputPdf;
	private String outputXml;
	private String inputXml;
	private String xmlRules;

	public CliArgs(String fileName) throws FileNotFoundException, IOException {

		File f = new File(fileName);
		if (!f.exists()) throw new FileNotFoundException();
		read(fileName);

	}

	private void read(String fileName) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileInputStream(fileName));

		this.xmlRules = p.getProperty(EntryPointCLI.PROPERTIES_XML_RULES);
		this.pdfGeneration = StringUtils.asBoolean(p.getProperty(EntryPointCLI.ENABLE_PDF_GENERATION));
		this.outputPdf = correctDirectoryPath(p.getProperty(EntryPointCLI.OUTPUT_PDF_PATH));
		this.outputXml = correctDirectoryPath(p.getProperty(EntryPointCLI.OUTPUT_XML_PATH));
		this.inputXml = correctDirectoryPath(p.getProperty(EntryPointCLI.INPUT_XML_PATH));
	}

	private String correctDirectoryPath(String str) {
		if (StringUtils.isEmpty(str)) return "";
		str = str.trim();
		if (!str.endsWith("\\") && !str.endsWith("/")) str += "/";
		return FileUtils.convertToSystemPath(str);
	}



	public boolean isPdfGeneration() {
		return pdfGeneration;
	}

	public String getOutputPdf() {
		return outputPdf;
	}

	public String getOutputXml() {
		return outputXml;
	}

	public String getInputXml() {
		return inputXml;
	}

	public String getXmlRules() {
		return xmlRules;
	}


	@Override
	public String toString() {
		return String.format("CliArgs [pdfGeneration=%s, outputPdf=%s, outputXml=%s, inputXml=%s, xmlRules=%s]", pdfGeneration, outputPdf, outputXml, inputXml, xmlRules);
	}




}
