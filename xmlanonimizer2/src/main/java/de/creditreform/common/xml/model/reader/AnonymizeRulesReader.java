package de.creditreform.common.xml.model.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;

import de.creditreform.common.helpers.AbstractZipParser;
import de.creditreform.common.helpers.IniModelReader;
import de.creditreform.common.helpers.AbstractZipParser.EntryInfo;
import de.creditreform.common.xml.transformer.AnonimizeData;

public class AnonymizeRulesReader {




	public void read(String zippedModelName) throws IOException {

		AbstractZipParser zipPrs = new AbstractZipParser(new FileInputStream(zippedModelName), "*.properties") {

			@Override
			public void processEntry(ZipEntry file, EntryInfo entryInfo) throws Exception {
				readModel(entryInfo);
			}
		};

		zipPrs.read();
		AnonimizeData.instance().initialize();
	}


	protected void readModel(EntryInfo entryInfo) throws Exception {
		IniModelReader ir = new IniModelReader();
		entryInfo.readFileData();
		ir.read(entryInfo.getFileData());
		AnonimizeData.instance().addDocumentFormat(ir.getCommonSpec());

	}

}
