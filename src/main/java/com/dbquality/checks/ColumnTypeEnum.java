package com.dbquality.checks;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.dbquality.exceptions.ExceptionFactory;
import com.dbquality.logs.DQLogger;
import com.dbquality.properties.MessageCodes;

/**
 * Defines type of the column
 * 
 * @author George Kolokotronis
 *
 */

public enum ColumnTypeEnum {

	INTEGER("INTEGER"), DECIMAL("DECIMAL"), VARCHAR("VARCHAR"), DATE("DATE");

	private final String type;
	private static final DQLogger logger = DQLogger.create(ColumnTypeEnum.class);

	/**
	 * Mapping between mode (e.g. "VARCHAR") (key) and literal mode value (e.g.
	 * STRING) (value)
	 */
	private static final Map<String, String> types = new HashMap<String, String>();

	static {
		for (ColumnTypeEnum mode : ColumnTypeEnum.values()) {
			types.put(mode.getMode(), mode.name());
		}
	}

	private ColumnTypeEnum(String mode) {
		this.type = mode;
	}

	public static ColumnTypeEnum toEnum(String mode) {
		if (!types.containsKey(mode)) {
			throw ExceptionFactory.createException(IllegalArgumentException.class, MessageCodes.ERR_INVALID_COLUMN_TYPE,
					null, logger, Level.ERROR, mode);
		}

		return ColumnTypeEnum.valueOf(types.get(mode));
	}

	/**
	 * @return mode
	 */
	public String getMode() {
		return this.type;
	}
}
