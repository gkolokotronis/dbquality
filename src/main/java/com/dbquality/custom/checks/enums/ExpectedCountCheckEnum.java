package com.dbquality.custom.checks.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.dbquality.exceptions.ExceptionFactory;
import com.dbquality.logs.DQLogger;
import com.dbquality.properties.MessageCodes;

/**
 * Defines type of the expectedCountCheck element of customchecks.xsd
 * 
 * @author George Kolokotronis
 *
 */

public enum ExpectedCountCheckEnum {

	GREATER_THAN_OR_EQUAL_TO("GREATER_THAN_OR_EQUAL_TO"), GREATER_THAN("GREATER_THAN"), LESS_THAN_OR_EQUAL_TO(
			"LESS_THAN_OR_EQUAL_TO"), LESS_THAN("LESS_THAN"), EQUAL_TO("EQUAL_TO");

	private static final DQLogger logger = DQLogger.create(ExpectedCountCheckEnum.class);

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
			throw ExceptionFactory.createException(IllegalArgumentException.class,
					MessageCodes.ERR_INVALID_EXPECTED_COUNT_CHECK, null, logger, Level.ERROR, type,
					types.values().toString());
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
