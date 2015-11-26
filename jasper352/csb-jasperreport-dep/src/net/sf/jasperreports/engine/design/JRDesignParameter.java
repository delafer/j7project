/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2009 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 539 Bryant Street, Suite 100
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
package net.sf.jasperreports.engine.design;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.base.JRBaseParameter;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRDesignParameter.java 2694 2009-03-24 18:11:24Z teodord $
 */
public class JRDesignParameter extends JRBaseParameter
{
	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_DEFAULT_VALUE_EXPRESSION = "defaultValueExpression";
	
	public static final String PROPERTY_FOR_PROMPTING = "forPrompting";
	
	public static final String PROPERTY_NAME = "name";
	
	public static final String PROPERTY_SYSTEM_DEFINED = "systemDefined";
	
	public static final String PROPERTY_VALUE_CLASS_NAME = "valueClassName";
	
	public static final String PROPERTY_NESTED_TYPE_NAME = "nestedTypeName";


	/**
	 *
	 */
	public void setName(String name)
	{
		Object old = this.name;
		this.name = name;
		getEventSupport().firePropertyChange(PROPERTY_NAME, old, this.name);
	}
	
	/**
	 *
	 */
	public void setValueClass(Class clazz)
	{
		setValueClassName(clazz.getName());
	}

	/**
	 *
	 */
	public void setValueClassName(String className)
	{
		Object old = this.valueClassName;
		valueClassName = className;
		valueClass = null;
		valueClassRealName = null;
		getEventSupport().firePropertyChange(PROPERTY_VALUE_CLASS_NAME, old, this.valueClassName);
	}

	/**
	 * Sets the parameter nested value type.
	 * 
	 * @param type the nester value type
	 * @see #getNestedType()
	 */
	public void setNestedType(Class type)
	{
		setNestedTypeName(type == null ? null : type.getName());
	}
	
	/**
	 * Sets the parameter nested value type.
	 * 
	 * @param typeName the name of the nested value type
	 * @see #getNestedType()
	 */
	public void setNestedTypeName(String typeName)
	{
		Object old = this.nestedTypeName;
		nestedTypeName = typeName;
		nestedType = null;
		getEventSupport().firePropertyChange(PROPERTY_NESTED_TYPE_NAME, old, this.nestedTypeName);
	}
	
	/**
	 *
	 */
	public void setSystemDefined(boolean isSystemDefined)
	{
		boolean old = this.isSystemDefined;
		this.isSystemDefined = isSystemDefined;
		getEventSupport().firePropertyChange(PROPERTY_SYSTEM_DEFINED, old, this.isSystemDefined);
	}

	/**
	 *
	 */
	public void setForPrompting(boolean isForPrompting)
	{
		boolean old = this.isForPrompting;
		this.isForPrompting = isForPrompting;
		getEventSupport().firePropertyChange(PROPERTY_FOR_PROMPTING, old, this.isForPrompting);
	}

	/**
	 *
	 */
	public void setDefaultValueExpression(JRExpression expression)
	{
		Object old = this.defaultValueExpression;
		this.defaultValueExpression = expression;
		getEventSupport().firePropertyChange(PROPERTY_DEFAULT_VALUE_EXPRESSION, old, this.defaultValueExpression);
	}

}
