package com.dbquality.checks;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.dbquality.exceptions.ExceptionFactory;
import com.dbquality.logs.DQLogger;
import com.dbquality.properties.MessageCodes;

public enum CheckTypeEnum {
	DISTINCT("distinct"), CUSTOM("custom");

	private static final DQLogger logger = DQLogger.create(CheckTypeEnum.class);

	private final String checkType;

	/**
	 * Mapping between mode (e.g. "DISTINCT") (key) and literal mode value (e.g.
	 * STRING) (distinct)
	 */
	private static final Map<String, String> checkTypes = new HashMap<String, String>();

	static {
		for (CheckTypeEnum checkType : CheckTypeEnum.values()) {
			checkTypes.put(checkType.getCheckType(), checkType.name());
		}
	}

	private CheckTypeEnum(String checkType) {
		this.checkType = checkType;
	}

	public static CheckTypeEnum toEnum(String checkType) {
		if (!checkTypes.containsKey(checkType)) {
			throw ExceptionFactory.createException(IllegalArgumentException.class,
					MessageCodes.ERR_WRONG_CHECK_TYPE_PROVIDED, null, logger, Level.ERROR, checkType,
					checkTypes.toString());
		}

		return CheckTypeEnum.valueOf(checkTypes.get(checkType));
	}

	/**
	 * @return mode
	 */
	public String getCheckType() {
		return this.checkType;
	}
}
