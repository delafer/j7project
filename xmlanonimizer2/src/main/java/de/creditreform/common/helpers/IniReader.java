package de.creditreform.common.helpers;

import java.util.Scanner;

public abstract class IniReader {

	private String blkName = "root";

	public IniReader() {
	}


	public void read(String str) throws Exception {

		Scanner scanner = new Scanner(str);
		int id;

		while (scanner.hasNextLine()) {
		  String s = scanner.nextLine();
		  if (StringUtils.isEmpty(s)) continue;
		  s = s.trim();
		  if (s.startsWith("#")) continue;

		  if (s.length()> 2 && s.startsWith("[") && s.endsWith("]")) {
			  onNewBlockInt(s.substring(1, s.length()-1).trim());
		  } else
	      if ((id = s.indexOf('=', 1))>0) {
	    	  onNewValueInt(s.substring(0, id), s.substring(id+1));
	      }

		}
		scanner.close();
	}


	private void onNewValueInt(String name, String value) throws Exception {
		value = value.trim();
		int idx = value.lastIndexOf("--");
		if (idx>=0) value = value.substring(0, idx).trim();
		onNewValue(name.trim(), value.trim(), this.blkName);

	}

	public abstract void onNewValue(String name, String value, String blockName)throws Exception;

	private void onNewBlockInt(String blockName) {
		this.blkName = blockName;
		onNewBlock(blockName);

	}


	public abstract void onNewBlock(String blockName);

}
