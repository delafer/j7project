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
package net.sf.jasperreports.engine.design.events;

import java.beans.PropertyChangeEvent;

import net.sf.jasperreports.engine.JRConstants;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: CollectionElementAddedEvent.java 2694 2009-03-24 18:11:24Z teodord $
 */
public class CollectionElementAddedEvent extends PropertyChangeEvent
{

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	private final int addedIndex;
	
	public CollectionElementAddedEvent(Object source, String propertyName,  
			Object addedValue, int addedIndex)
	{
		super(source, propertyName, null, addedValue);
		
		this.addedIndex = addedIndex;
	}

	public Object getAddedValue()
	{
		return getNewValue();
	}
	
	public int getAddedIndex()
	{
		return addedIndex;
	}
	
}
