package net.j7.commons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.j7.commons.base.Equals;
import net.j7.commons.strings.StringUtils;

/**
 * The Class Obj.
 */
@SuppressWarnings({"unchecked","rawtypes"})
public final class Obj {

   /** The Constant EMPTY_OBJECT_ARRAY. */
   private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0]; // Used to call constructor

   /** The Constant QuotationMarks. */
   private static final String QT = "()";

   /** The Constant Dot. */
   private static final String DT = ".";

   /** The cached methods. */
   private static transient Map<String, Method> cachedMtds = new HashMap<String, Method>(128);

   /** The cached fields. */
   private static transient Map<String, Field> cachedFlds = new HashMap<String, Field>(128);

   /**
    * returns generated "Key".
    *
    * @param classArg the input class
    * @param name the name
    * @return the string
    */
   private final static String key(final Class<?> classArg, final String name) {
      return new StringBuilder(70).append(name).append('@').append(
            classArg != null ? classArg.getName() : null).toString();
   }

   /**
    * Gets the result value.
    *
    * @param obj the obj
    * @param str the str
    * @return the object
    */
   public static Object get(final Object obj, final String str) {
      return interate(obj, new StringBuilder(str));
   }

   /**
    * Gets the result (with generics).
    *
    * @param <E> the element type
    * @param obj the obj
    * @param str the str
    * @return the instanceof E<E> object
    */
   public static <E>E getA(final Object obj, final String str) {
      return (E)interate(obj, new StringBuilder(str));
   }

   /**
    * Gets the string value.
    *
    * @param obj the obj
    * @param str the str
    * @return the string
    */
   public static String getString(final Object obj, final String str) {
      final Object ret = get(obj, str);
      return ret != null  ? ret.toString() : StringUtils.EMPTY;
   }

   /**
    * Gets the integer value.
    *
    * @param obj the obj
    * @param str the str
    * @return the integer
    */
   public static int getInteger(final Object obj, final String str) {
      final Number ret = (Number) get(obj, str);
      return ret != null  ? ret.intValue() : 0;
   }

   /**
    * Gets the double value.
    *
    * @param obj the obj
    * @param str the str
    * @return the double
    */
   public static double getDouble(final Object obj, final String str) {
      final Number ret = (Number) get(obj, str);
      return ret != null  ? ret.doubleValue() : 0d;
   }


   /**
    * Interate method.
    *
    * @param obj the obj
    * @param str the str
    * @return the object
    */
   private static Object interate(final Object obj, final StringBuilder str) {

      final int idx = str.indexOf(DT);
      String subS;
      if (idx < 0) {
         subS = str.toString();
         str.delete(0, str.length());
      } else {
         subS = str.substring(0, idx);
         str.delete(0, idx + 1);
      }

      final boolean isMethod = subS.endsWith(QT);

      final Object res = !isMethod ?
            getValueAny		(obj, subS) :
            getMethodValue	(obj, subS.substring(0, subS.length() - 2));

      if (res != null && str.length() > 0) return interate(res, str);

      return res;
   }

   /**
    * Gets the value any.
    *
    * @param o the o
    * @param key the key
    * @return the value any
    */
   private final static Object getValueAny(final Object o, final String key) {
      return
           o instanceof Map ? ((Map) o).get(key)
         : getFieldValue(o, key);
   }

   /**
    * Assigned cached method.
    *
    * @param cls the cls
    * @param name the name
    * @return the method
    */
   private final static synchronized Method assignedCachedMethod(final Class<?> cls, final String name) {

      final String key = key(cls, name);
      Method ret = cachedMtds.get(key);

      if (null == ret) {
         ret = assignedMethod(cls, name);

         try {
         if (ret != null && !ret.isAccessible()) ret.setAccessible(true);
         } catch (final Exception e) {};

         cachedMtds.put(key, ret);
      }

      return ret;
   }

   /**
    * Assigned cached field.
    *
    * @param cls the cls
    * @param name the name
    * @return the field
    */
   private static synchronized Field assignedCachedField(final Class<?> cls, final String name) {
      final String key = key(cls, name);
      Field ret = cachedFlds.get(key);

      if (null == ret) {
         ret = assignedField(cls, name);

         try {
         if (ret != null && !ret.isAccessible()) ret.setAccessible(true);
         } catch (final Exception e) {};

         cachedFlds.put(key, ret);
      }

      return ret;
   }

   /**
    * Assigned method.
    *
    * @param cls the cls
    * @param name the name
    * @return the method
    */
   private final static Method assignedMethod(final Class<?> cls, final String name) {
      final Method[] methods = cls.getMethods();
      for (int i = 0; i < methods.length; i++)
         if (Equals.equalIgnoreCase(name, methods[i].getName())) return methods[i];
      return null;
   }

   /**
    * Gets the field by name.
    *
    * @param cls  the cls
    * @param name the name
    * @return the field by name
    */
   private static Field assignedField(final Class<?> cls, final String name) {

      if (cls == null || cls.isPrimitive())
         return null;

      Field field = null;
      try {
         field = cls.getDeclaredField(name);
      } catch (final Exception e) {
      }

      return field != null ? field : assignedField(cls.getSuperclass(), name);
   }

   /**
    * Gets the field value.
    *
    * @param obj the obj
    * @param name the name
    * @return the field value
    */
   private final static Object getFieldValue(final Object obj, final String name) {
      final Field field = assignedCachedField(obj.getClass(), name);
      if (field != null && !field.isAccessible())
         field.setAccessible(true);
      Object value = null;
      if (field != null)
         try {
            value = field.get(obj);
         } catch (final Exception e) {
            e.printStackTrace();
         }
      return value;

   }

   /**
    * Gets the method value.
    *
    * @param obj the obj
    * @param name the name
    * @return the method value
    */
   private final static Object getMethodValue(final Object obj,
         final String name) {
      final Method method = assignedCachedMethod(obj.getClass(), name);

      if (method != null)
         try {
            return method.invoke(obj, EMPTY_OBJECT_ARRAY);
         } catch (final Exception e) {
            e.printStackTrace();
         }
      return null;
   }

}
