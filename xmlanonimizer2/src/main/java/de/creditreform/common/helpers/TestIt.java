package de.creditreform.common.helpers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TestIt {

	public TestIt() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {
			List<String> lines = Files.readAllLines(Paths.get("d:\\ReportResponse.properties"), Charset.forName("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (String string : lines) {
				sb.append(string).append(StringUtils.LF_DOS);
			}
			IniModelReader ir = new IniModelReader();
			ir.read(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
