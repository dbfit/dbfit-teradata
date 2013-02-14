
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;

import java.util.*;
public class TypeNormaliserFactory {
	private static Map<Class,TypeNormaliser> normalisers= new HashMap<Class,TypeNormaliser>();
	public static void setNormaliser(Class targetClass,TypeNormaliser normaliser){
		normalisers.put(targetClass, normaliser);
	}
	public static TypeNormaliser getNormaliser(Class targetClass){
		return normalisers.get(targetClass);
	}
}
