package com.dbquality.distinct.checks.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents <code>distinct</code> element of the xml containg the checks,
 * which describes all checks that are going to be runned for distinct values
 * 
 * @author George Kolokotronis
 *
 */
public class DistinctRootElement {
	private List<ColumnDistinctElement> columns = new ArrayList<ColumnDistinctElement>();

	public List<ColumnDistinctElement> getColumns() {
		return Collections.unmodifiableList(columns);
	}

	public void setColumns(List<ColumnDistinctElement> columns) {
		this.columns = columns;
	}

	public void addColumn(ColumnDistinctElement column) {
		columns.add(column);
	}

	@Override
	public String toString() {
		return "DistinctElement [\ncolumns=" + columns + "\n]\n\n";
	}
}
