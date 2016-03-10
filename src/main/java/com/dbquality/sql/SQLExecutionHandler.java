package com.dbquality.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.Level;

import com.dbquality.consts.AppConsts;
import com.dbquality.exceptions.ExceptionFactory;
import com.dbquality.logs.DQLogger;
import com.dbquality.properties.ApplicationMessagesHolder;
import com.dbquality.properties.ApplicationPropertiesHolder;
import com.dbquality.properties.MessageCodes;

/**
 * 
 * This class defines a generic template method use for the execution flow of
 * sql checks. It should be extended by a handler which needs to run various sql
 * checks.
 * 
 * @author George Kolokotronis *
 */
public abstract class SQLExecutionHandler {

	private static final DQLogger logger = DQLogger.create(SQLExecutionHandler.class);

	public final void execute() {

		this.sqlsPopulation();

		this.sqlExecutionAndValidation();

	}

	/**
	 * populating the sqls in order to execute them
	 */
	protected abstract void sqlsPopulation();

	/**
	 * executing and validating the results of the sql checks
	 */
	protected abstract void sqlExecutionAndValidation();

	/**
	 * 
	 * @param database
	 *            - the database type to connect to (Teradata, mysql)
	 * @return returns the connections to the database
	 */
	protected Connection getConnection() {

		String databaseType = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_DATABASE_TYPE);

		DatabaseEnum database = DatabaseEnum.toEnum(databaseType);

		switch (database) {
		case TERADATA:
			return getTeradataConection();

		case MYSQL:
			return getMySQLConnection();
		default:
			return null;
		}
	}

	/**
	 * Returns {@link Connection} to Teradata
	 * 
	 * @return {@link Connection} object of teradata
	 */
	private Connection getTeradataConection() {

		String userName = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_TERADATA_USERNAME);
		String password = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_TERADATA_PASSWORD);
		String url = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_TERADATA_DATABASE_URL);

		logger.info(
				ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.MSG_DB_CONNECTING_TO_DATABASE, url));

		Connection conn = null;

		try {
			Class.forName(AppConsts.TERADATA_JDBC_DRIVER);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			throw ExceptionFactory.createException(IllegalStateException.class,
					MessageCodes.ERR_DB_CANNOT_CONNECT_TO_DATABASE, e, logger, Level.ERROR, url);

		} catch (ClassNotFoundException e) {
			throw ExceptionFactory.createException(IllegalStateException.class,
					MessageCodes.ERR_DB_CANNOT_FIND_DRIVER_IN_CLASSPATH, e, logger, Level.ERROR,
					AppConsts.TERADATA_JDBC_DRIVER);
		}

		return conn;
	}

	/**
	 * Returns {@link Connection} to MySQL
	 * 
	 * @return {@link Connection} object of MySQL
	 */
	private Connection getMySQLConnection() {

		String userName = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_MYSQL_USERNAME);
		String password = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_MYSQL_PASSWORD);
		String url = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_MYSQL_DATABASE_URL);

		logger.info(
				ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.MSG_DB_CONNECTING_TO_DATABASE, url));

		Connection conn = null;

		try {
			Class.forName(AppConsts.MYSQL_JDBC_DRIVER);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			throw ExceptionFactory.createException(IllegalStateException.class,
					MessageCodes.ERR_DB_CANNOT_CONNECT_TO_DATABASE, e, logger, Level.ERROR, url);
		} catch (ClassNotFoundException e) {
			throw ExceptionFactory.createException(IllegalStateException.class,
					MessageCodes.ERR_DB_CANNOT_FIND_DRIVER_IN_CLASSPATH, e, logger, Level.ERROR,
					AppConsts.TERADATA_JDBC_DRIVER);
		}

		return conn;
	}

}
