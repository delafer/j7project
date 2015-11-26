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
package net.sf.jasperreports.charts.design;

import java.awt.Color;

import net.sf.jasperreports.charts.base.JRBaseScatterPlot;
import net.sf.jasperreports.charts.util.JRAxisFormat;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseFont;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRDesignScatterPlot.java 2693 2009-03-24 17:38:19Z teodord $
 */
public class JRDesignScatterPlot extends JRBaseScatterPlot {

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_X_AXIS_LABEL_COLOR = "xAxisLabelColor";
	
	public static final String PROPERTY_X_AXIS_LABEL_EXPRESSION = "xAxisLabelExpression";
	
	public static final String PROPERTY_X_AXIS_LABEL_FONT = "xAxisLabelFont";
	
	public static final String PROPERTY_X_AXIS_LINE_COLOR = "xAxisLineColor";
	
	public static final String PROPERTY_X_AXIS_TICK_LABEL_COLOR = "xAxisTickLabelColor";
	
	public static final String PROPERTY_X_AXIS_TICK_LABEL_FONT = "xAxisTickLabelFont";
	
	public static final String PROPERTY_X_AXIS_TICK_LABEL_MASK = "xAxisTickLabelMask";
	
	public static final String PROPERTY_Y_AXIS_LABEL_COLOR = "yAxisLabelColor";
	
	public static final String PROPERTY_Y_AXIS_LABEL_EXPRESSION = "yAxisLabelExpression";
	
	public static final String PROPERTY_Y_AXIS_LABEL_FONT = "yAxisLabelFont";
	
	public static final String PROPERTY_Y_AXIS_LINE_COLOR = "yAxisLineColor";
	
	public static final String PROPERTY_Y_AXIS_TICK_LABEL_COLOR = "yAxisTickLabelColor";
	
	public static final String PROPERTY_Y_AXIS_TICK_LABEL_FONT = "yAxisTickLabelFont";
	
	public static final String PROPERTY_Y_AXIS_TICK_LABEL_MASK = "yAxisTickLabelMask";

	public static final String PROPERTY_DOMAIN_AXIS_MINVALUE_EXPRESSION = "domainAxisMinValueExpression";
	
	public static final String PROPERTY_DOMAIN_AXIS_MAXVALUE_EXPRESSION = "domainAxisMaxValueExpression";
	
	public static final String PROPERTY_RANGE_AXIS_MINVALUE_EXPRESSION = "rangeAxisMinValueExpression";
	
	public static final String PROPERTY_RANGE_AXIS_MAXVALUE_EXPRESSION = "rangeAxisMaxValueExpression";
	

	public JRDesignScatterPlot(JRChartPlot scattedPlot, JRChart chart)
	{
		super(scattedPlot, chart);
	}


	/**
	 *
	 */
	public void setXAxisLabelExpression( JRExpression xAxisLabelExpression ){
		Object old = this.xAxisLabelExpression;
		this.xAxisLabelExpression = xAxisLabelExpression;
		getEventSupport().firePropertyChange(PROPERTY_X_AXIS_LABEL_EXPRESSION, old, this.xAxisLabelExpression);
	}

	/**
	 *
	 */
	public void setXAxisLabelFont(JRFont xAxisLabelFont)
	{
		Object old = this.xAxisLabelFont;
		this.xAxisLabelFont = new JRBaseFont(this.getChart(), xAxisLabelFont);
		getEventSupport().firePropertyChange(PROPERTY_X_AXIS_LABEL_FONT, old, this.xAxisLabelFont);
	}

	/**
	 *
	 */
	public void setXAxisLabelColor(Color xAxisLabelColor)
	{
		Object old = this.xAxisLabelColor;
		this.xAxisLabelColor = xAxisLabelColor;
		getEventSupport().firePropertyChange(PROPERTY_X_AXIS_LABEL_COLOR, old, this.xAxisLabelColor);
	}
	
	/**
	 *
	 */
	public void setXAxisTickLabelFont(JRFont xAxisTickLabelFont)
	{
		Object old = this.xAxisTickLabelFont;
		this.xAxisTickLabelFont = new JRBaseFont(this.getChart(), xAxisTickLabelFont);
		getEventSupport().firePropertyChange(PROPERTY_X_AXIS_TICK_LABEL_FONT, old, this.xAxisTickLabelFont);
	}

	/**
	 *
	 */
	public void setXAxisTickLabelColor(Color xAxisTickLabelColor)
	{
		Object old = this.xAxisTickLabelColor;
		this.xAxisTickLabelColor = xAxisTickLabelColor;
		getEventSupport().firePropertyChange(PROPERTY_X_AXIS_TICK_LABEL_COLOR, old, this.xAxisTickLabelColor);
	}

	/**
	 *
	 */
	public void setXAxisTickLabelMask(String xAxisTickLabelMask)
	{
		Object old = this.xAxisTickLabelMask;
		this.xAxisTickLabelMask = xAxisTickLabelMask;
		getEventSupport().firePropertyChange(PROPERTY_X_AXIS_TICK_LABEL_MASK, old, this.xAxisTickLabelMask);
	}

	/**
	 *
	 */
	public void setXAxisLineColor(Color xAxisLineColor)
	{
		Object old = this.xAxisLineColor;
		this.xAxisLineColor = xAxisLineColor;
		getEventSupport().firePropertyChange(PROPERTY_X_AXIS_LINE_COLOR, old, this.xAxisLineColor);
	}

	/**
	 *
	 */
	public void setYAxisLabelExpression( JRExpression yAxisLabelExpression ){
		Object old = this.yAxisLabelExpression;
		this.yAxisLabelExpression = yAxisLabelExpression;
		getEventSupport().firePropertyChange(PROPERTY_Y_AXIS_LABEL_EXPRESSION, old, this.yAxisLabelExpression);
	}

	/**
	 *
	 */
	public void setYAxisLabelFont(JRFont yAxisLabelFont)
	{
		Object old = this.yAxisLabelFont;
		this.yAxisLabelFont = new JRBaseFont(this.getChart(), yAxisLabelFont);
		getEventSupport().firePropertyChange(PROPERTY_Y_AXIS_LABEL_FONT, old, this.yAxisLabelFont);
	}

	/**
	 *
	 */
	public void setYAxisLabelColor(Color yAxisLabelColor)
	{
		Object old = this.yAxisLabelColor;
		this.yAxisLabelColor = yAxisLabelColor;
		getEventSupport().firePropertyChange(PROPERTY_Y_AXIS_LABEL_COLOR, old, this.yAxisLabelColor);
	}
	
	/**
	 *
	 */
	public void setYAxisTickLabelFont(JRFont yAxisTickLabelFont)
	{
		Object old = this.yAxisTickLabelFont;
		this.yAxisTickLabelFont = new JRBaseFont(this.getChart(), yAxisTickLabelFont);
		getEventSupport().firePropertyChange(PROPERTY_Y_AXIS_TICK_LABEL_FONT, old, this.yAxisTickLabelFont);
	}

	/**
	 *
	 */
	public void setYAxisTickLabelColor(Color yAxisTickLabelColor)
	{
		Object old = this.yAxisTickLabelColor;
		this.yAxisTickLabelColor = yAxisTickLabelColor;
		getEventSupport().firePropertyChange(PROPERTY_Y_AXIS_TICK_LABEL_COLOR, old, this.yAxisTickLabelColor);
	}

	/**
	 *
	 */
	public void setYAxisTickLabelMask(String yAxisTickLabelMask)
	{
		Object old = this.yAxisTickLabelMask;
		this.yAxisTickLabelMask = yAxisTickLabelMask;
		getEventSupport().firePropertyChange(PROPERTY_Y_AXIS_TICK_LABEL_MASK, old, this.yAxisTickLabelMask);
	}

	/**
	 *
	 */
	public void setYAxisLineColor(Color yAxisLineColor)
	{
		Object old = this.yAxisLineColor;
		this.yAxisLineColor = yAxisLineColor;
		getEventSupport().firePropertyChange(PROPERTY_Y_AXIS_LINE_COLOR, old, this.yAxisLineColor);
	}
	
	/**
	 * 
	 */
	public void setXAxisFormat(JRAxisFormat axisFormat)
	{
		setXAxisLabelColor(axisFormat.getLabelColor());
		setXAxisLabelFont(axisFormat.getLabelFont());
		setXAxisTickLabelFont(axisFormat.getTickLabelFont());
		setXAxisTickLabelColor(axisFormat.getTickLabelColor());
		setXAxisTickLabelMask(axisFormat.getTickLabelMask());
		setXAxisLineColor(axisFormat.getLineColor());
	}

	/**
	 * 
	 */
	public void setYAxisFormat(JRAxisFormat axisFormat)
	{
		setYAxisLabelColor(axisFormat.getLabelColor());
		setYAxisLabelFont(axisFormat.getLabelFont());
		setYAxisTickLabelFont(axisFormat.getTickLabelFont());
		setYAxisTickLabelColor(axisFormat.getTickLabelColor());
		setYAxisTickLabelMask(axisFormat.getTickLabelMask());
		setYAxisLineColor(axisFormat.getLineColor());
	}

	/**
	 *
	 */
	public void setRangeAxisMinValueExpression(JRExpression rangeAxisMinValueExpression)
	{
		Object old = this.rangeAxisMinValueExpression;
		this.rangeAxisMinValueExpression = rangeAxisMinValueExpression;
		getEventSupport().firePropertyChange(PROPERTY_RANGE_AXIS_MINVALUE_EXPRESSION, old, this.rangeAxisMinValueExpression);
	}

	/**
	 *
	 */
	public void setRangeAxisMaxValueExpression(JRExpression rangeAxisMaxValueExpression)
	{
		Object old = this.rangeAxisMaxValueExpression;
		this.rangeAxisMaxValueExpression = rangeAxisMaxValueExpression;
		getEventSupport().firePropertyChange(PROPERTY_RANGE_AXIS_MAXVALUE_EXPRESSION, old, this.rangeAxisMaxValueExpression);
	}

	/**
	 *
	 */
	public void setDomainAxisMinValueExpression(JRExpression domainAxisMinValueExpression)
	{
		Object old = this.domainAxisMinValueExpression;
		this.domainAxisMinValueExpression = domainAxisMinValueExpression;
		getEventSupport().firePropertyChange(PROPERTY_DOMAIN_AXIS_MINVALUE_EXPRESSION, old, this.domainAxisMinValueExpression);
	}

	/**
	 *
	 */
	public void setDomainAxisMaxValueExpression(JRExpression domainAxisMaxValueExpression)
	{
		Object old = this.domainAxisMaxValueExpression;
		this.domainAxisMaxValueExpression = domainAxisMaxValueExpression;
		getEventSupport().firePropertyChange(PROPERTY_DOMAIN_AXIS_MAXVALUE_EXPRESSION, old, this.domainAxisMaxValueExpression);
	}

}
