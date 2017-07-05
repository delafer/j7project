package org.delafer.xanderView.file.entry;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import org.libjpegturbo.turbojpeg.test.Main;

public class EncodeName {

	public EncodeName() {
		// TODO Auto-generated constructor stub
	}

	public static String correctFileName(String name) {
		   if (name == null) return null;
		    int len = name.length();
			StringBuilder sb = new StringBuilder(len);
			char ch;
			for (int i = 0; i < len; i++) {
				ch = name.charAt(i);
				if ((ch<' ') ||
					(ch==',' || ch=='/' || ch=='\\' || ch=='"' ||
					 ch=='?' || ch==';' || ch== ':' || ch=='>' ||
					 ch=='<' || ch=='|' || ch=='*')) {
						ch = '_';
					}
				sb.append(ch);
			}
			return sb.toString().trim();
	   }

	char[] c = new char[] {' ','~','!', '@', '#', '$', '%', '^', '&', '(', ')', '-', '+', '_','=','[',']', '{','}'};

	public static void main3(String[] args) {
		Map<Character, Character> a = new TreeMap<>();
		List<Character> b = new LinkedList<>();

		for (int i = 0; i < 26; i++) {
			char c = (char)(65 + i);
			b.add(Character.valueOf(c));
		}
		try {
		SecureRandom s = SecureRandom.getInstanceStrong();
			String a1 = "", b1 = "";
			Map<Integer, Integer> xx = new TreeMap<Integer, Integer>();
			for (int i = 0; i < 26; i++) {
				Character f = Character.valueOf((char)(65 + i));
				Character t;
				int x;
				do {
					x =  s.nextInt(b.size());
					t = b.get(x);
				} while (f.equals(t));
				b.remove(x);
//				System.out.println("case '"+f+"': return "+((int)f-(int)t)+";");
				a1 += ((int)f-(int)t)+",";
				b1 += ((int)t-(int)f)+",";
				xx.put(((int)t-65), ((int)f - 65));
			}
			System.out.println(a1);
			System.out.println();
//			System.out.println(b1);
			for (Map.Entry<Integer, Integer> x : xx.entrySet()) {
				System.out.print(x.getKey()-x.getValue()+",");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	final static int[] codes = new int[] {-8,-6,1,-9,-12,-10,2,5,-6,-10,-12,8,-8,2,-11,-2,16,8,5,-5,-1,16,16,5,1,15};
	final static int[] codes2 = new int[] {-16,-1,-5,-8,-2,-16,-16,6,8,-8,-15,-2,9,-5,6,10,12,2,-5,10,8,1,12,-1,5,11};

	public static String encrypt(String name) {
		char ch, uch;

		StringBuilder sb = new StringBuilder(name.length());

		for (int i = 0; i < name.length(); i++) {
			ch = name.charAt(i);
			if (Character.isLetter(ch)) {
				boolean uc = Character.isUpperCase(ch);
				uch = Character.toUpperCase(ch);
				int code = (int)uch - 65;
				int ncode = codes[code];
				char r = ((char)shift(((int)uch - ncode), i));
				sb.append(uc ? r : Character.toLowerCase(r));
			} else
			{
				sb.append(special(ch));
			}
		}
		return sb.toString();
	}

	private static char special(char ch) {
		switch (ch) {
		case ' ':
			return '_';
		case '~':
			return '@';
		case '!':
			return '[';
		case '@':
			return '}';
		case '#':
			return '$';
		case '$':
			return '^';
		case '%':
			return '~';
		case '^':
			return ')';
		case '&':
			return '!';
		case '(':
			return '-';
		case ')':
			return '#';
		case '-':
			return ' ';
		case '+':
			return '=';
		case '_':
			return '$';
		case '=':
			return '(';
		case '[':
			return '%';
		case ']':
			return '{';
		case '{':
			return '+';
		case '}':
			return ']';
		default:
			return ch;
		}
	}

	public static String decrypt(String name) {
		char ch, uch;

		StringBuilder sb = new StringBuilder(name.length());

		for (int i = 0; i < name.length(); i++) {
			ch = name.charAt(i);
			if (Character.isLetter(ch)) {
				boolean uc = Character.isUpperCase(ch);
				uch = Character.toUpperCase(ch);
				uch = (char)(shift2((int)uch, i));
				System.out.println(":"+uch);
				int code = (int)uch - 65;
				int ncode = codes2[code];
				char r = ((char)((int)uch - ncode));
				sb.append(uc ? r : Character.toLowerCase(r));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	private static int shift(int ch, int order ) {
		int co = ch - 65;
		co += (order % 26);
		return (co % 26) + 65;
//		return ch;
	}

	private static int shift2(int ch, int order ) {
		int co = (ch - 65) + 26;
		co -= (order % 26);
//		System.out.println(co+" "+(co % 26)+" "+((co % 26) + 65));
		return (co % 26) + 65;
//		return ch;
	}

	public static void main(String[] args) {
		System.out.println((encrypt("Teenage sex anal (portno) pthcf 16 yo.")));
		System.out.println((encrypt("AAAB")));
		System.out.println((encrypt("AABB")));
//		System.out.println(shift2(shift(69, 133), 133));
	}
}
