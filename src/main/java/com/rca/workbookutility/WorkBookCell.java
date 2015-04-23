/**
 * 
 */
package com.rca.workbookutility;

import java.awt.Color;

/**
 * @author govind.gupta
 *
 */
public class WorkBookCell {
	private String value;
	private boolean formula;
	private String name;
	private Color color;
	private String columnHeader;
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * @return the columnHeader
	 */
	public String getColumnHeader() {
		return columnHeader;
	}
	/**
	 * @param columnHeader the columnHeader to set
	 */
	public void setColumnHeader(String columnHeader) {
		this.columnHeader = columnHeader;
	}
	/**
	 * @return the formula
	 */
	public boolean isFormula() {
		return formula;
	}
	/**
	 * @param formula the formula to set
	 */
	public void setFormula(boolean formula) {
		this.formula = formula;
	}
}
