package org.delafer.xanderView.common;

import java.util.regex.Pattern;

public final class Strings {

	public final static transient char 		SPACE_CH = ' ';
	public final static transient String 	EMPTY		= "";
	public final static transient String 	SPACE		= " ";
	
	
	private static final transient String 	SPCHR_ID	= "\\";
    private final static transient char		SEPARATOR_1	= ';';
    private final static transient char		SEPARATOR_2	= ',';
    public final static transient String 	TRIPLE_DOT = "...";
    
	public final static char[] STRING_QUOTES = new char[] {'\'','"'};
	
	private static final String STR_NULL = "null";    
    /** LineFeed constant for currently running OS **/
    public static final transient String 	LF = System.getProperty("line.separator");
    /** used in CP/M, MP/M, DOS, OS/2, Microsoft Windows, Symbian OS **/
    public static final transient String 	LF_DOS = "\r\n";
    /**  used in (GNU/Linux, AIX, Xenix, Mac OS X, FreeBSD, etc.), BeOS, Amiga, RISC OS **/
    public static final transient String 	LF_UNIX = "\n";
    /** Commodore machines, Apple II family, Mac OS up to version 9 and OS-9 **/
    public static final transient String 	LF_MAC9 = "\r";
    /** TAB constant **/
    public static final transient String 	TAB = "\t";
    
	private static final transient int 		PATTERN_CMP = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;

	/**
	 * Checks if string is a wildcard mask.
	 * @param str the string
	 * @return true, if is wld mask
	 */
	public static boolean isWldMask(String str) {
		if (str == null) return false;

		char ch;
		for (int i = str.length()-1; i >= 0; i--) {
			ch = str.charAt(i);
			if (ch=='*' || ch=='?') return true;
		}
		return false;
	}

	/**
	 * Compare with wildcard.
	 * @param String the input string to compare
	 * @param String the wildcard
	 * @return true, if equal
	 */
	public static boolean compareWld(String str, String wildcard) {
		if (str==null) return wildcard == null;
		return checkMask(str.toLowerCase(), wildcard.toLowerCase());
	}

	/**
	 * Compare strings.
	 * @param String the first string
	 * @param String the second string
	 * @return true, if string are equal
	 */
	public final static boolean compareIgnoreCase(final String s1, final String s2) {
		return s1==s2 
		? true 
		: s1 != null 
				? s2 != null 
						? s1.equalsIgnoreCase(s2) 
						: false
				: s2 == s1;
	}
	
	
	public final static boolean compare(final String s1, final String s2) {
		return s1==s2 
				? true 
				: s1 != null 
						? s2 != null 
								? s1.intern() == s2.intern() 
								: false
						: s2 == s1;
	}

	public final static boolean empty(final String s) {
		return null==s || 0==s.length();
	}

	public final static boolean emptyExt(final String s) {
		 for (int i = 0; i < (s != null ? s.length() : 0); i++)
			 if (s.charAt(i)!=SPACE_CH) return false;
		 return true;
	}

	/**
	 * Simple replace.
	 *
	 * @param String
	 * @param replaceable chars
	 * @param replacing chars
	 *
	 * @return the string
	 */
	public static String replace(String inStr, String fromStr, String toStr) {
		if (null==inStr || null==fromStr || fromStr.length()==0)return inStr;
		
		char ch;
		int corr = (toStr!=null ? toStr.length() : 0) - fromStr.length() << 1;

		StringBuilder sb = new StringBuilder(inStr.length() + (corr>0 ? corr : 0));
		int eqPoz=0;
		for (int i = 0; i < inStr.length(); i++) {
			ch = inStr.charAt(i);

			if (ch==fromStr.charAt(eqPoz)) {
				if (eqPoz == fromStr.length()-1) {
					eqPoz = 0;
					sb.append(toStr);
				} else {
					eqPoz++;
					if (eqPoz>=fromStr.length()) eqPoz = 0;
				}

			} else if (eqPoz>0) {
				sb.append(fromStr.substring(0, eqPoz));
				i--;
				eqPoz =0;
			}else
			sb.append(ch);

		}
		if (eqPoz>0)
			sb.append(fromStr.substring(0, eqPoz));
		return sb.toString();
	}

	/**
	 * Replace all specified chars in the string to a new string
	 * 
	 * @param inStr the in str
	 * @param fromStr the from str
	 * @param toStr the to str
	 * 
	 * @return the string
	 */
	public final static String replace(String inStr, char fromStr, String toStr) {

		StringBuilder sb = new StringBuilder(inStr.length()+4);
		char ch;
		for (int i = 0; i < inStr.length(); i++) {
			ch = inStr.charAt(i);
			sb.append(ch!=fromStr ? ch : toStr);
		}
		return sb.toString();
	}

	/**
	 * Check mask.
	 * @param str the str
	 * @param wildc the wildc
	 * @return true, if successful
	 */
	public final static boolean checkMask(String str, String wildc) {

		StringBuilder sb = new StringBuilder(wildc.length()+4);
		char ch;
		for (int i = 0; i < wildc.length(); i++) {
			ch = wildc.charAt(i);

			if (ch=='*' || ch=='?')
				sb.append('.');
			else
			if (ch=='.' || ch=='^' || ch == '$' || ch=='@' || ch=='+' || ch=='(' || ch==')')
				sb.append(SPCHR_ID);

			sb.append(ch);
		}

		return Pattern.compile(sb.toString(), PATTERN_CMP).matcher(str).matches();
	}

    /**
     * Contains char.
     *
     * @param ch the char
     * @param chars the chars
     *
     * @return true, if successful
     */
    public final static boolean containsChar(final char ch, final char[] chars) {
        for (char element : chars)
			if (ch == element) return true;

        return false;
    }


    /**
     * Check if set of strings separated with "," or ";" contains given string value.
     *
     * @param strs the set of strings separated with "," or ";"
     * @param value the string value
     *
     * @return true, if array of string contains given string value.
     */
    public final static int containsValue(final String strs, String value) {
        return containsValue(getStringsArray(strs), value);
    }

    /**
     * Check if array of string contains given string value.
     *
     * @param strs the String[] array
     * @param value the string value
     *
     * @return true, if array of string contains given string value.
     */
    public final static int containsValue(final String[] strs, String value) {

        if (null != strs && null != value) {
            value	= value.trim();

            for (int i = strs.length-1; i >=0 ; i--)
				if (strs[i].equalsIgnoreCase(value)) return i;
        }

        return -1;
    }

    /**
     * Cut all spaces.
     * @param str the str
     * @return the string
     */
    public final static String cutAllSpaces(final String str) {
        return cutSpaces(str, Strings.STRING_QUOTES, true);
    }

    /**
     * Cut spaces.
     * @param str the str
     * @return the string
     */
    public final static String cutSpaces(final String str) {
        return cutSpaces(str, Strings.STRING_QUOTES);
    }

    /**
     * Cut spaces.
     *
     * @param str the str
     * @param strMarkers the str markers
     *
     * @return the string
     */
    public final static String cutSpaces(final String str, final char[] strMarkers) {
        return cutSpaces(str, strMarkers, false);
    }

    /**
     * Cut spaces
     * Replace sequences with 2 or more spaces to 1 space only
     *
     * @param str the input string
     * @param strMarkers the str markers
     * @param cutAll the cut all
     *
     * @return the string
     */
    public final static String cutSpaces(final String str, final char[] strMarkers, boolean cutAll) {
        if (null == str) return str;

        StringBuffer	sb	= new StringBuffer(str.length());
        char		ch,
		oldCh	= (char) 0;
        boolean		subStr	= false;

        for (int i = 0; i < str.length(); i++) {
            ch	= str.charAt(i);
            if (containsChar(ch, strMarkers)) subStr	= false == subStr;
            if (!subStr && ch == SPACE_CH && ch == oldCh) continue;

            if (ch != SPACE_CH ||!cutAll) sb.append(ch);

            oldCh	= ch;
        }

        return sb.toString().trim();
    }

    /**
     * Fill string with given char to given length.
     * Example:
     * a = fillString("123",6,'0');
     * result> a = 000123
     *
     * @param str the string
     * @param length the length
     * @param toFill the char to fill
     *
     * @return the string
     */
    public final static String fillString(final String str, final int length, final char toFill) {
        if (null == str) return str;
        StringBuffer sb	 = new StringBuffer(length);
        int	difference	= length - str.length();

        for (int i = difference; i > 0; i--) sb.append(toFill);
        sb.append(str);
        return sb.toString();
    }


    /**
     * Removes the spaces from String
     * @param inStr the input string
     * @return the string
     */
    public final static String removeSpaces(final String str) {
        if (null == str) return str;

        StringBuilder buf	= new StringBuilder(str.length());
        char ch;

        for (int i = 0, length = str.length(); i < length; i++) {
            ch	= str.charAt(i);
            if (ch != SPACE_CH) buf.append(ch);
        }

        return buf.toString();
    }

    /**
     * @see String shrinkStr(String str, int length, String suffix)
     * calls method shrinkStr(str, 64);
     */
    public static String shrinkStr(String str) {
        return shrinkStr(str, 64);
    }

    /**
     * @see String shrinkStr(String str, int length, String suffix)
     * calls method shrinkStr(str, length, "...");
     */
    public static String shrinkStr(String str, int length) {
        return shrinkStr(str, length, null);
    }

    /**
     * Shrink/Cut string to N chars and add given suffix to end of string
     * @param str the string
     *
     * @return cutted string
     */
    public static String shrinkStr(String str, int length, String suffix) {
        if (str == null) return null;
        return (str.length() > length) ? suffix != null ? str.substring(0, length) + suffix : str.substring(0, length) : str;
        //if (str.length() > length) return str.substring(0, length) + suffix;
        //return str;

    }

    /**
     * Converts string with values separated
     * with "," or ";" to String[] array.
     * @param str the string
     * @return the strings array
     */
    public final static String[] getStringsArray(String str) {
        int	cnt	= 0;

        if (empty(str)) return new String[cnt];

        str	= str + SEPARATOR_1;

        for (int i = 0; i < str.length(); i++) {
            char	ch	= str.charAt(i);
            if (ch == SEPARATOR_1 || ch == SEPARATOR_2) cnt++;
        }

        String[]strs = new String[cnt];
        StringBuilder sb = new StringBuilder();
        cnt	= 0;

        for (int i = 0; i < str.length(); i++) {
            char	ch	= str.charAt(i);

            if (ch == SEPARATOR_1 || ch == SEPARATOR_2) {
                strs[cnt]	= sb.toString().trim();
                sb.delete(0, sb.length());
                cnt++;
            } else
				sb.append(ch);
        }

        return strs;
    }

	/**
	 * argString(input, replacement) method, replaces ? with given string
	 * @param str the str
	 * @param toInsert the to insert
	 *
	 * @return the output string
	 */
	public final static String fill(String str, String toInsert) {
		return str!=null ? replace(str, '?', toInsert) : null;
	}
	
	public static final String fill(String text, Object... args) {
		if (text == null || args == null) return text; 
		StringBuilder sb = new StringBuilder(text.length()+16);
		char ch;
		int j = 0;
		for (int i = 0; i < text.length(); i++) {
			ch = text.charAt(i);
			if (ch!='?') sb.append(ch); 
			else {
				if (args.length > j && args[j] != null) 
					sb.append(args[j]);
				j++;
			}
			
		}
		return sb.toString();
	}
	
	/**
	 * Gets the First Not Null value
	 * @param array of objects
	 * @return the First not null value
	 */
	public final static String getFNN(Object[] obj) {
		if (obj!=null)
			for (Object object : obj) 
				if (object!=null) return object.toString();
		
		return EMPTY;
	}
	
    
	public static String concat(String divider, String... args) {
		
		if (args==null) return Strings.EMPTY;
		if (divider==null) divider = Strings.SPACE;
		int i = 0;
		int k = args.length - 1;
		int j = k*divider.length();
		for (i = k; i >= 0; i--) 
			j += args[i] != null ? args[i].length() : 0;
			
		StringBuilder sb = new StringBuilder(j);
		
		for (i = 0; i <= k; i++) 
			if (args[i] != null) {
				if (sb.length()>0) sb.append(divider);
				sb.append(args[i]);
			}
		
			
			
		return sb.toString();
	}
	
	public static final String NVL(String arg1, String arg2) {
		return emptyExt(arg1) ? arg2 : arg1;
	}
	
	private static boolean isLetterChar(char ch) {
		return (ch >= 'a' && ch <='z') || (ch >= 'A' && ch <='Z');
	}
	
	public static final String compact(String str) {
		if (str==null || str.length()==0) return str;
		StringBuilder sb = new StringBuilder(str.length());
		char ch;
		char toAdd=(char)0;
		char lastAdded = toAdd;
		
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch==(char)10 || ch==(char)13) 
				toAdd = ' ';
			else 
				toAdd = ch;
			
			boolean normalChar = (toAdd >= '0' && toAdd <='9') || isLetterChar(toAdd);
			
			if (normalChar || (toAdd != lastAdded)){
				if (sb.length()>0 || toAdd != ' ') {
					
					if (sb.length()>=2 && sb.charAt(sb.length()-1)==' ') {
						boolean lc1 = isLetterChar(sb.charAt(sb.length()-2));
						boolean lc2 = isLetterChar(toAdd);
						if (!lc1 || !lc2) sb.deleteCharAt(sb.length()-1);
					}
						
					
					sb.append(toAdd);
				}
				
				if (sb.length()>=255) {
					sb.append("...");
					break;
				}
				lastAdded = toAdd;
			}
		}
		
		return sb.toString();
	}
	
}

