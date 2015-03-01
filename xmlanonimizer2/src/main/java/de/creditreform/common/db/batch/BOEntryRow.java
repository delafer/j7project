package de.creditreform.common.db.batch;

import java.io.IOException;
import java.io.Serializable;

import de.creditreform.common.pdf.Renderer;
import de.creditreform.common.xml.XmlAnonimizerEngine;
import de.creditreform.common.xml.XmlAnonimizerEngine.Result;
import de.creditreform.common.xml.utils.XmlTools;

public final class BOEntryRow implements Serializable, Cloneable {

	private static final long serialVersionUID = 1883774707723926529L;

	private long csId;
	private Long csIdPdf;
	private String originalXml;
	private String transformedXml = null;
	private byte[] pdf = null;

	public BOEntryRow() {
	}

	public BOEntryRow(long csId, Long csIdPdf, String originalXml) {
		this.csId = csId;
		this.csIdPdf = csIdPdf;
		this.originalXml = originalXml;
	}


	public String getAnonimizedXml() {
		if (null == transformedXml) {
			Result res = XmlAnonimizerEngine.anonimize(originalXml);

			if (res.ok()) {
					this.transformedXml = XmlTools.prettyPrintXML(res.getXml(), "UTF-8");
			} else {
					this.transformedXml = "";
			}
		}
		return this.transformedXml;
	}


	public byte[] getPdf() {
		if (null == pdf) {
			try {
				pdf = Renderer.createPdf(getAnonimizedXml());
			} catch (IOException e) {
				pdf = new byte[0];
			}
		}
		return pdf;

	}


	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		BOEntryRow bd = new BOEntryRow();
		bd.csId = this.csId;
		bd.originalXml = this.originalXml;
		bd.transformedXml = this.transformedXml;
		bd.pdf = this.pdf;
		return bd;
	}


	/**
	 * @return the csId
	 */
	public long getCsId() {
		return csId;
	}


	/**
	 * @param csId the csId to set
	 */
	public void setCsId(long csId) {
		this.csId = csId;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == null || this == obj) return !(obj == null);
		if (!(obj instanceof BOEntryRow)) return false;
		return csId == ((BOEntryRow) obj).csId;
	}



	@Override
	public int hashCode() {
		return (int) (csId ^ (csId >>> 32));
	}

	@Override
	public String toString() {
		return String.format("BOEntryRow [csId=%s]", csId);
	}

	/**
	 * @return the csIdPdf
	 */
	public Long getCsIdPdf() {
		return csIdPdf;
	}


}
