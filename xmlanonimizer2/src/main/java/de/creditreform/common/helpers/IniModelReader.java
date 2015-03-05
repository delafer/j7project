package de.creditreform.common.helpers;

import java.util.HashMap;
import java.util.Map;

import de.creditreform.common.xml.model.resources.CommonSpec;

public class IniModelReader extends IniReader {

	private CommonSpec cs;

	public enum BlockType {
		General("general"), Tags("tags"), DataTags("datatags"), Rules("replace.values"), ReplacementMode("replace.mode"), Unknown("");

		public String tag;
		BlockType(String id) {
			tag = id;
			Values.values.put(id.toLowerCase(), this);
		}

		public static BlockType valueBy(String name) {
			BlockType ret = Values.values.get(name.toLowerCase());
			return ret != null ? ret : BlockType.Unknown;
		}

		private static class Values {
			static final Map<String, BlockType> values;
			static {
				values = new HashMap<String, BlockType>();
			}
			private Values() {};
		}
	}

	public enum KeyType {
		RootElement("RootElement"),
		Namespace("Namespace"),
		IgnoreCase("IgnoreCase"),
		PrettyPrint("Pretty.print.xml"),
		DefaultReplace("Default.replace.mode"),
		DataTags("datatags"),//deprecated
		Unknown("");

		public String tag;
		KeyType(String id) {
			Values.values.put(id.toLowerCase(), this);
		}

		public static KeyType valueBy(String name) {
			KeyType ret = Values.values.get(name.toLowerCase());
			return ret != null ? ret : KeyType.Unknown;
		}

		private static class Values {
			static final Map<String, KeyType> values;
			static {
				values = new HashMap<String, KeyType>();
			}
			private Values() {};
		}
	}



	BlockType blckName;

	public IniModelReader() {
		super();
		cs = new CommonSpec();
	}


	public void onNewValue(String name, String value, String blockName) {
		newValue(name, KeyType.valueBy(name), value, this.blckName);
	}


	public void onNewBlock(String blockName) {
		newBlock(BlockType.valueBy(blockName));
	}

	private void newValue(String keyText, KeyType keyEnum, String value, BlockType block) {
		//2222
		System.out.println(Args.fill("Key(enum)=%1, Key(txt)=%2, block=%3, value=[%4]", keyEnum.toString(), keyText, block.toString(), value));
		switch (block) {
		case Tags:
			break;
		case DataTags:
			break;
		case Rules:
			break;
		case ReplacementMode:
			break;
		case General:
			readGeneral(keyText, keyEnum, value);
			break;
		case Unknown:
		default:
			break;
		}

	}


	private void readGeneral(String keyText, KeyType keyEnum, String value) {
		switch (keyEnum) {
		case Namespace:

			break;

		default:
			break;
		}

	}


	private void newBlock(BlockType block) {
		///111
		this.blckName = block;

	}


	public CommonSpec getSpec() {
		return cs;
	}



}
