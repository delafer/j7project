/*
 * @File: Args.java
 *
 *
 * @Version $Revision: #1 $Date: $
 *
 */
package net.j7.system.utils;


/**
 * Class <b>Args.fill(String template, Object... args)</b> <br><br>
 * This is really easy and lightweight <b>(only about 50 lines of code)</b><br>
 * alternative to a build-in java String.format function, with some extra<br>
 * functionality.<br><br>
 * <b>with Java:</b><br>
 * [String.format("if this %s is %s, than other %s is also %s", "statement", true, "statement", true)]<br>
 * <b>with Args:</b><br>
 * Args.fill("if this %1 is %2, than other %1 is also %2", "statement", true) = <br>
 * "if this statement is true, than other statement is also true"<br>
 *
 * @author  <a href="TavrovsA@gmail.com">Alexander Tawrowski</a>
 * @version $Revision: #1 $
 * @category Parametric string functionality
 * @see java alternative - Strings.format(" %s... ", 0001);
 */
public final class Args {

public static void main(String[] args) {
	System.out.println(1);
}


   /**
    * Fills string with arguments use "\" (Escape character) for special chars eG.
    * Arguments.string("Name: %1, Date: %3, Price: %2","Router",150d,new Date()); will return:
    * "Name: Router, Date <date>, Price: 150,00"
    *
    * @param source the source/template string
    * @param args the list of arguments to fill a template string
    * @return filled string
    */
   public final static String fill(String template, Object... args) {

      if (template == null ||
    		  args == null ||
    		  template.isEmpty() ||
    		  args.length == 0) {
    	  return template;
      }

      StringBuilder sb = new StringBuilder(template.length() + (args.length * 6));

      char ch;
      boolean esc = false, num = false;

      int no = 0;
      for (int len = template.length(), i = 0; i < len; i++) {
         ch = template.charAt(i);

         if (esc) {
            sb.append(ch);
            esc = false;
         } else {
            if (ch == '\\')
               esc = true;
            else if (ch == '%') {
               flush(sb, num, no, args);
               num = true;
               no = 0;
            } else if (num) {
               if (ch >= ZERO && ch <= NINE) {
                  no = no * 10 + numeric(ch);
               } else {
                  flush(sb, num, no, args);
                  sb.append(ch);
                  num = false;
               }

            } else
               sb.append(ch);
         }
      }

      flush(sb, num, no, args);


      return sb.toString();
   }

   /**
    * @param sb
    * @param num
    * @param no
    * @param args
    */
   private static void flush(StringBuilder sb, boolean num, int no, Object[] args) {

      if (num)
         if (no > 0)
            sb.append(get(args, no));
         else
            sb.append('%');
   }

   private static String get(Object[] args, int no) {

      if (no > args.length) return "";
      final Object r = args[no - 1];
      return r != null ? (r instanceof Class) ? ((Class<?>)r).getName() : r.toString() : null;

   }

   private static final char NINE = '9';

   private static final char ZERO = '0';

   private static final int numeric(char ch) {

      return (byte) ch - (byte) ZERO;
   }

   // public static void main(String[] args) {
   //
   // System.out.println(Args.fill("%3 %2 = %1, %3 %4 = %1", "false", "isCompany", "boolean", "readOnly"));
   // System.out.println(fill("%11%1 %2 %3 %4 %5 %6%7%8%9%10%10%10", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
   // System.out.println(fill("%1x%5", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
   // System.out.println(fill("%1\\1", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
   // System.out.println(fill("%1%%%3%", 1, 2, "alexander", 4, 5));
   // System.out.println(fill("%%%1%%", 1, 2, "alexander", 4, 5));
   // System.out.println(fill("hight!", 1, 2, "alexander", 4, 5));
   // System.out.println(fill("%1 %2 %3", null, null));
   // System.out.println(fill("privet(%1) masha - %2,%3", "Sasha", 12, 99));
   // }
}
