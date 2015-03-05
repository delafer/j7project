package de.creditreform.common.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.creditreform.common.xml.model.DocumentType;
import de.creditreform.common.xml.model.EntryXml;
import de.creditreform.common.xml.model.MetaTag;
import de.creditreform.common.xml.model.XmlModel;
import de.creditreform.common.xml.model.resources.IAnonimizeSpec;
import de.creditreform.common.xml.model.resources.IAnonimizeSpec.ReplacementType;
import de.creditreform.common.xml.model.resources.MultiValue;
import de.creditreform.common.xml.transformer.AnonimizeData;
import de.creditreform.common.xml.transformer.SaxTransformer;

public class XmlAnonimizerEngine {

	static Logger log = LoggerFactory.getLogger(XmlAnonimizerEngine.class);

	public XmlAnonimizerEngine() {
		// TODO Auto-generated constructor stub
	}


	public static Result anonimize(String str) {
		if (StringUtils.isEmpty(str)) return new Result(str);
		try {
			Result r = anonimize(new ByteArrayInputStream(str.getBytes("UTF-8")));
			return r;
		} catch (UnsupportedEncodingException e) {
			log.error("",e);
		}
		return Result.error();
	}

	public static Result anonimize(InputStream is) {
		if (is==null) return null;
		SaxTransformer sp = new SaxTransformer();
		try {
			XmlModel model = sp.readXmlModel(is);

			if (DocumentType.TYPE_UNKNOWN.equals(model.documentType)) return Result.error();

			IAnonimizeSpec spec = AnonimizeData.instance().getProcessor(model.documentType);

			IAnonimizeSpec specNew = spec.getNewInstance(model.getValues());

			Map<MetaTag, MultiValue<EntryXml>> fields = model.getAnonimizeFields();
			for (Map.Entry<MetaTag, MultiValue<EntryXml>> next : fields.entrySet()) {

				MetaTag key = next.getKey();
				MultiValue<EntryXml> value = next.getValue();

				for (int i = 0; i < value.size(); i++) {

					EntryXml nextEntry = value.getValue(i);


					switch (specNew.getDataReplacementMode(key)) {
					case ReplaceAll:
						nextEntry.setValue(ReplacementType.ReplaceAll,specNew.getNewData(key, i));
						break;
					case TextRecursive:
						nextEntry.setValue(ReplacementType.TextRecursive,specNew.getNewData(key, i));
						break;
					case OnlyText:
						nextEntry.setValue(ReplacementType.OnlyText,specNew.getNewData(key, i));
						break;
					case RemoveBlock:
						nextEntry.setIgnored(true);
						break;
					case Ignore:
					default:
						break;
					}


				}


			}

			Result result = new Result();
			result.setXml(model.xmlModel.render().toString());
			result.setCrefoNr(specNew.getData(MetaTag.valueOf("CrefoNr")));
			return result;

		} catch (Exception e) {
			log.error("Error during xml processing: ", e);
		} finally {
			try {
				is.close();
			} catch (IOException ignore) {}
		}
		return null;
	}


	public static class Result implements Serializable {

		private static final long serialVersionUID = 8660182412509731747L;

		public String xml;
		public String crefoNr;
		public int errorCode;

		static int SUCCESS = 0;
		static int ERROR = -1;

		public Result() {
		}


		public Result(String xml) {
			this.xml = xml;
		}

		public boolean ok() {
			return SUCCESS == errorCode;
		}

		public static Result error() {
			Result ret =  new Result();
			ret.setErrorCode(ERROR);
			return ret;
		}
		/**
		 * @return the xml
		 */
		public String getXml() {
			return xml;
		}
		/**
		 * @param xml the xml to set
		 */
		public void setXml(String xml) {
			this.xml = xml;
		}
		/**
		 * @return the crefoNr
		 */
		public String getCrefoNr() {
			return crefoNr;
		}
		/**
		 * @param crefoNr the crefoNr to set
		 */
		public void setCrefoNr(String crefoNr) {
			this.crefoNr = crefoNr;
		}


		/**
		 * @return the errorCode
		 */
		public int getErrorCode() {
			return errorCode;
		}


		/**
		 * @param errorCode the errorCode to set
		 */
		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}
	}

}
