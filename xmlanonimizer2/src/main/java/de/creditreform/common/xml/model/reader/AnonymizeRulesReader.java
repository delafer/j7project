package de.creditreform.common.xml.model.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;

import de.creditreform.common.helpers.AbstractZipParser;
import de.creditreform.common.helpers.IniModelReader;
import de.creditreform.common.helpers.AbstractZipParser.EntryInfo;

public class AnonymizeRulesReader {






	public static void main(String[] args) {

		try {
			AnonymizeRulesReader reader = new AnonymizeRulesReader();
			reader.read("D:/Anonym/rules.zip");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void read(String zippedModelName) throws IOException {

		AbstractZipParser zipPrs = new AbstractZipParser(new FileInputStream(zippedModelName), "*.properties") {

			@Override
			public void processEntry(ZipEntry file, EntryInfo entryInfo) throws Exception {
				readModel(entryInfo);
			}
		};

		zipPrs.read();

	}


	protected void readModel(EntryInfo entryInfo) throws IOException {
		IniModelReader ir = new IniModelReader();
		entryInfo.readFileData();
		ir.read(entryInfo.getFileData());

	}

}
