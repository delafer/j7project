package de.creditreform.common.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;

import de.creditreform.common.helpers.TextFileUtils.AbrstractFileParser;
import de.creditreform.common.helpers.TextFileUtils.TextWriter;

public class ReformatTxt {


	public static void main(String[] args) {
		try {
			doit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void doit() throws Exception {


		Map<String, Entry> map = new HashMap<String, Entry>();

		AbrstractFileParser afp = new TextFileUtils.AbrstractFileParser("e:\\export_all.csv") {

			@Override
			public void processLine(String line, int lineNumber) throws Exception {
				if (line==null || StringUtils.empty(line)) return ;
				Entry ent = new Entry(line);
				Entry em = map.get(ent.key());
				if (em == null) {
					map.put(ent.key(), ent);
				} else {
					if (ent.isBetter(em)) {
						map.remove(ent.key());
						map.put(ent.key(), ent);
					}
				}

			}

		};
		afp.read();
		ArrayList<Entry> arrent = new ArrayList<Entry>();
		arrent.addAll(map.values());

		Collections.sort(arrent);

		TextWriter tw = TextFileUtils.createTextWriter("e:\\result.csv", false);

		for (Entry entry : arrent) {
			tw.writeLn(entry.csvLine());
		}

		tw.flush();
		tw.close();

	}


	public static class Entry implements Comparable<Entry>{

		String name;
		String land;
		String goodName;

		public Entry(String txt) {
			String[] a = txt.split(";");
			this.name = a[0];
			this.land = a[1];
			this.goodName =  WordUtils.capitalizeFully(name.trim());
		}

		public String key() {
			StringBuilder sb = new StringBuilder();
			char ch;
			for (int i = 0; i < name.length(); i++) {
				ch = name.charAt(i);
				if (ch==' ') continue;
				sb.append(ch);
			}
			return sb.toString().trim().toLowerCase();
		}

		public String normalName() {
			return goodName;
		}

		public String csvLine() {
			return normalName()+";"+land;
		}


		public boolean isBetter(Entry old) {
			if (goodName.length() < old.goodName.length() && old.goodName.startsWith(goodName)) return true;
			if (goodName.length() > old.goodName.length()) return true;
			return false;
		}

		public String comp() {
			return land  + "_" + goodName;
		}

		@Override
		public int compareTo(Entry o) {
			return this.comp().compareToIgnoreCase(o.comp());
		}

	}

}
