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
package net.sf.jasperreports.engine.xml;

import net.sf.jasperreports.engine.design.JRDesignPropertyExpression;

import org.xml.sax.Attributes;

/**
 * {@link JRDesignPropertyExpression} factory.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRPropertyExpressionFactory.java 2697 2009-03-24 18:41:23Z teodord $
 */
public class JRPropertyExpressionFactory extends JRBaseFactory
{

	public Object createObject(Attributes attrs) throws Exception
	{
		JRDesignPropertyExpression propertyExpression = new JRDesignPropertyExpression();
		
		String name = attrs.getValue(JRXmlConstants.ATTRIBUTE_name);
		propertyExpression.setName(name);
		
		return propertyExpression;
	}

}
