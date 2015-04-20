/**
 * 
 */
package com.rca.workbookutility;

import java.util.List;

/**
 * @author govind.gupta
 *
 */
public class WorkBookRow {
	private List<WorkBookCell> rowCells;
	private boolean isHeaderRow;
	private String clientName;
	
	/**
	 * @return the rowCells
	 */
	public List<WorkBookCell> getRowCells() {
		return rowCells;
	}
	/**
	 * @param rowCells the rowCells to set
	 */
	public void setRowCells(List<WorkBookCell> rowCells) {
		this.rowCells = rowCells;
	}
	/**
	 * @return the isHeaderRow
	 */
	public boolean isHeaderRow() {
		return isHeaderRow;
	}
	/**
	 * @param isHeaderRow the isHeaderRow to set
	 */
	public void setHeaderRow(boolean isHeaderRow) {
		this.isHeaderRow = isHeaderRow;
	}
	/**
	 * @return the rowName
	 */
	public String getRowName() {
		return clientName;
	}
	/**
	 * @param rowName the rowName to set
	 */
	public void setRowName(String rowName) {
		this.clientName = rowName;
	}
}
