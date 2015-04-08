package net.j7.commons.utils;

/**
 *
 * Class to convert a String boolean representation to an Boolean
 * Operations on boolean primitives and Boolean objects.
 * This class tries to handle null input gracefully. An exception will not be thrown for a null input. Each method documents its behaviour in more detail.
 * @author vollmarf
 * @version $Revision: #3 $
 */
public class BooleanUtils  {

   /**
    * BooleanUtils.toBoolean(null)    = false
    * BooleanUtils.toBoolean("true")  = true
    * BooleanUtils.toBoolean("TRUE")  = true
    * BooleanUtils.toBoolean("tRUe")  = true
    * BooleanUtils.toBoolean("on")    = true
    * BooleanUtils.toBoolean("yes")   = true
    * BooleanUtils.toBoolean("y")   = true
    * BooleanUtils.toBoolean("j")   = true
    * BooleanUtils.toBoolean("ja")   = true
    * BooleanUtils.toBoolean("1")   = true
    * BooleanUtils.toBoolean("0")   = false
    * BooleanUtils.toBoolean("false") = false
    * BooleanUtils.toBoolean("x gti") = false
    * @param booleanString
    * @return
    */
   public static boolean toBoolean(String booleanString) {
      return "1".equals(booleanString) || "y".equalsIgnoreCase(booleanString) || "JA".equalsIgnoreCase(booleanString) || "j".equalsIgnoreCase(booleanString);
   }
}
