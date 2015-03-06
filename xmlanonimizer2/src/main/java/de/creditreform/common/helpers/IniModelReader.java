package de.creditreform.common.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.creditreform.common.xml.model.MetaTag;
import de.creditreform.common.xml.model.resources.CommonSpec;
import de.creditreform.common.xml.model.resources.IAnonimizeSpec.ReplacementType;

@SuppressWarnings("unused")
public class IniModelReader extends IniReader {

	private CommonSpec cs;

	private String namespace;
	private boolean prettyPrint;
	private String rootElement;
	private ReplacementType defaultReplType;
	private boolean ignoreCase;
	private List<IniTag> tags;
	private Map<MetaTag, ReplacementType> replMode;

	public IniModelReader() {
		super();
		cs = new CommonSpec();
		namespace = "";
		prettyPrint = false;
		rootElement = "";
		defaultReplType = ReplacementType.RT_DEFAULT;
		ignoreCase = true;
		tags = new ArrayList<IniTag>();
		replMode = new HashMap<MetaTag, ReplacementType>();
	}


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
			readTags(keyText, keyEnum, value); break;
		case Rules:
			readRules(keyText, keyEnum, value); break;
		case ReplacementMode:
			readReplModes(keyText, keyEnum, value); break;
		case General:
			readGeneral(keyText, keyEnum, value); break;
		case DataTags:
		case Unknown:
		default:
			break;
		}

	}



	private void readRules(String keyText, KeyType keyEnum, String value) {
		// TODO Auto-generated method stub

	}


	private void readReplModes(String keyText, KeyType keyEnum, String value) {
		if (StringUtils.isEmpty(value)) return ;
		ReplacementType t = ReplacementType.valueBy(keyText, null);
		if (null == t) return ;

		String[] vals = StringUtils.splitValues(value);
		for (String next : vals) {
			if (StringUtils.isEmpty(next)) continue;
			MetaTag mt = MetaTag.valueOf(next);
			replMode.put(mt, t);
		}

	}


	private void readTags(String key, KeyType keyEnum, String value) {
		this.tags.add(new IniTag(key, value));

	}


	private void readGeneral(String keyText, KeyType keyEnum, String value) {
		switch (keyEnum) {
		case Namespace:
			this.namespace = value; break;
		case PrettyPrint:
			this.prettyPrint = StringUtils.asBoolean(value); break;
		case RootElement:
			this.rootElement = value; break;
		case DefaultReplace:
			defaultReplType = ReplacementType.valueBy(value, ReplacementType.OnlyText); break;
		case IgnoreCase:
			ignoreCase = StringUtils.asBoolean(value); break;
		case Unknown:
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


	static class IniTag {
		String name;
		String path;

		public IniTag(String name, String path) {
			this.name = name;
			this.path = path;
		}

		@Override
		public int hashCode() {
			int result = ((name == null) ? 0 : name.toLowerCase().hashCode());
			result = 31 * result + ((path == null) ? 0 : path.toLowerCase().hashCode());
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;

			if (!(obj instanceof IniTag)) return false;

			IniTag o = (IniTag) obj;

			if (!this.name.equals(o.name)) return false;
			if (!this.path.equals(o.path)) return false;

			return true;
		}
	}

}
