package com.dbquality.custom.checks.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines type of the expectedCountCheck element of customchecks.xsd
 * 
 * @author George Kolokotronis
 *
 */

public enum ExpectedCountCheckEnum {

	GREATER_THAN_OR_EQUAL_TO("GREATER_THAN_OR_EQUAL_TO"), GREATER_THAN("GREATER_THAN"), LESS_THAN_OR_EQUAL_TO(
			"LESS_THAN_OR_EQUAL_TO"), LESS_THAN("LESS_THAN"), EQUAL_TO("EQUAL_TO");

	private final String type;

	/**
	 * Mapping between mode (e.g. "VARCHAR") (key) and literal mode value (e.g.
	 * STRING) (value)
	 */
	private static final Map<String, String> types = new HashMap<String, String>();

	static {
		for (ExpectedCountCheckEnum type : ExpectedCountCheckEnum.values()) {
			types.put(type.getType(), type.name());
		}
	}

	private ExpectedCountCheckEnum(String type) {
		this.type = type;
	}

	public static ExpectedCountCheckEnum toEnum(String type) {
		if (!types.containsKey(type)) {
			throw new IllegalArgumentException("The expected count check enum type: " + type + " is not valid");
		}

		return ExpectedCountCheckEnum.valueOf(types.get(type));
	}

	/**
	 * @return mode
	 */
	public String getType() {
		return this.type;
	}
}
