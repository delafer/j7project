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
package net.sf.jasperreports.renderers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import net.sf.jasperreports.charts.util.HighLowChartHyperlinkProvider;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRPrintHyperlink;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;


/**
 * Image map renderer used for charts with high low datasets.
 * 
 * @deprecated
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRHighLowChartImageMapRenderer.java 2699 2009-03-24 18:57:29Z teodord $
 */
public class JRHighLowChartImageMapRenderer extends JRAbstractChartImageMapRenderer
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	private HighLowChartHyperlinkProvider highLowChartHyperlinkProvider = null;
	
	public JRHighLowChartImageMapRenderer(JFreeChart chart, List itemHyperlinks)
	{
		super(chart);
		
		highLowChartHyperlinkProvider = new HighLowChartHyperlinkProvider(itemHyperlinks);
	}


	public JRPrintHyperlink getEntityHyperlink(ChartEntity entity)
	{
		return highLowChartHyperlinkProvider.getEntityHyperlink(entity);
	}

	public boolean hasHyperlinks()
	{
		return highLowChartHyperlinkProvider.hasHyperlinks();
	}

	/**
	 * These fields are only for serialization backward compatibility.
	 */
	private List itemHyperlinks;

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		
		if (highLowChartHyperlinkProvider == null)
		{
			this.highLowChartHyperlinkProvider = new HighLowChartHyperlinkProvider(itemHyperlinks);
			itemHyperlinks = null;
		}
	}
}
