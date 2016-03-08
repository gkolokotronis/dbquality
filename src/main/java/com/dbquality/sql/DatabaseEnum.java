package com.dbquality.sql;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.consts.AppConsts;
import com.dbquality.distinct.checks.sql.DistinctSQLExecutionHandler;
import com.dbquality.exceptions.ExceptionFactory;
import com.dbquality.properties.MessageCodes;

public enum DatabaseEnum {
	TERADATA("teradata"), MYSQL("mysql");

	private static final Logger logger = LogManager.getLogger(DistinctSQLExecutionHandler.class);

	private final String database;

	/**
	 * Mapping between mode (e.g. "TERADATA") (key) and literal mode value (e.g.
	 * STRING) (teradata)
	 */
	private static final Map<String, String> databases = new HashMap<String, String>();

	static {
		for (DatabaseEnum database : DatabaseEnum.values()) {
			databases.put(database.getDatabase(), database.name());
		}
	}

	private DatabaseEnum(String database) {
		this.database = database;
	}

	public static DatabaseEnum toEnum(String database) {
		if (!databases.containsKey(database)) {
			throw ExceptionFactory.createException(IllegalArgumentException.class,
					MessageCodes.ERR_WRONG_DATABASE_IN_PROPERTIES_FILE, null, logger, Level.ERROR,
					AppConsts.PROPERTIES_FILE_NAME);
		}

		return DatabaseEnum.valueOf(databases.get(database));
	}

	/**
	 * @return mode
	 */
	public String getDatabase() {
		return this.database;
	}
}
