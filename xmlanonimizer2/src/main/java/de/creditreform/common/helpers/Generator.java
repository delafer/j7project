package de.creditreform.common.helpers;

import de.creditreform.common.helpers.TextFileUtils.TextWriter;

public class Generator {

	public static void main(String[] args) throws Exception{

		TextWriter tw = TextFileUtils.createTextWriter("D:\\resss.csv", "UTF-8", true);

		for (int i = 0; i < 2000; i++) {
			tw.writeLn(Args.fill("%1,./data/auskunft2.xml,Wirtschaftsauskunft,0017,005,zz897ad4-3724-24c4-3941-%2,", 3004126+i,1357+i));
		}

		tw.flush();
		tw.close();



	}

}
