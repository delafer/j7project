package de.creditreform.common.db.batch;

import java.io.IOException;
import java.io.Serializable;

import de.creditreform.common.pdf.Renderer;
import de.creditreform.common.xml.XmlAnonimizerEngine;
import de.creditreform.common.xml.XmlAnonimizerEngine.Result;
import de.creditreform.common.xml.utils.XmlTools;

public final class DocumentTransformer implements Serializable, Cloneable {

	private static final long serialVersionUID = 1883774707723926529L;

	private String originalXml;
	private String prettyPrintXml;
	private Result result;
	private byte[] pdf = null;

	public DocumentTransformer() {
	}

	public DocumentTransformer(String originalXml) {
		this.originalXml = originalXml;
	}


	public Result result() {
		if (null == result) {
			result = XmlAnonimizerEngine.anonimize(originalXml);
		}
		return result;
	}

	public String getTransformedXml() {

			if (result().ok()) {
					return XmlTools.prettyPrintXML(result().getXml(), "UTF-8");
			} else {
					return "";
			}
	}

	public String getXml() {
		return result().isPrettyPrint() ? getPrettyXml() : getTransformedXml();
	}

	public String getPrettyXml() {
		if (null == prettyPrintXml) {

			if (result().ok()) {
					this.prettyPrintXml = XmlTools.prettyPrintXML(result().getXml(), "UTF-8");
			} else {
					this.prettyPrintXml = "";
			}
		}
		return this.prettyPrintXml;
	}


	public byte[] getPdf() {
		if (null == pdf) {
			try {
				pdf = Renderer.createPdf(getPrettyXml());
			} catch (IOException e) {
				e.printStackTrace();
				pdf = new byte[0];
			}
		}
		return pdf;

	}

}
