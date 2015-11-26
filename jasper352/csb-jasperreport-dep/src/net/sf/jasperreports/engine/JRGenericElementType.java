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
package net.sf.jasperreports.engine;

import java.io.Serializable;

/**
 * A key identifying a generic element type.
 * 
 * <p>
 * This key is used to resolve registered export handlers for generic print
 * elements.
 * 
 * <p>
 * A type key is composed of a namespace (which is usually a URI associated with
 * the organization that implemented the generic element type) and a type name.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRGenericElementType.java 2698 2009-03-24 18:46:27Z teodord $
 */
public class JRGenericElementType implements Serializable
{

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	private final String namespace;
	private final String name;

	/**
	 * Creates a generic element type key.
	 * 
	 * @param namespace the type namespace
	 * @param name the type name
	 */
	public JRGenericElementType(String namespace, String name)
	{
		this.name = name;
		this.namespace = namespace;
	}
	
	/**
	 * Returns the type namespace.
	 * 
	 * @return the type namespace
	 */
	public String getNamespace()
	{
		return namespace;
	}

	/**
	 * Returns the type name.
	 * 
	 * @return the type name
	 */
	public String getName()
	{
		return name;
	}
	
	public int hashCode()
	{
		int hash = 17;
		hash = 37 * hash + namespace.hashCode();
		hash = 37 * hash + name.hashCode();
		return hash;
	}
	
	public boolean equals(Object o)
	{
		if (o == this)
		{
			return true;
		}

		if (!(o instanceof JRGenericElementType))
		{
			return false;
		}
		
		JRGenericElementType key = (JRGenericElementType) o;
		return namespace.equals(key.namespace)
				&& name.equals(key.name);
	}

	public String toString()
	{
		return namespace + "#" + name;
	}
	
}
