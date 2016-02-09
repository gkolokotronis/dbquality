package com.dbquality.custom.checks.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents <code>custom</code> element of the xml containg the checks, which
 * describes all checks that are going to be runned for custom values
 * 
 * @author George Kolokotronis
 *
 */
public class CustomRootElement {
	private List<CustomCheckElement> checks = new ArrayList<CustomCheckElement>();

	public List<CustomCheckElement> getChecks() {
		return Collections.unmodifiableList(checks);
	}

	public void setChecks(List<CustomCheckElement> columns) {
		this.checks = columns;
	}

	public void addCheck(CustomCheckElement column) {
		checks.add(column);
	}

	@Override
	public String toString() {
		return "CustomElement [\nchecks=" + checks + "\n]\n\n";
	}
}
