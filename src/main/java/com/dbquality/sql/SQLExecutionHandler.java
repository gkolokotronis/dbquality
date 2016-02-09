package com.dbquality.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.consts.AppConsts;
import com.dbquality.properties.ApplicationPropertiesHolder;

/**
 * 
 * This class defines a generic template method use for the execution flow of
 * sql checks. It should be extended by a handler which needs to run various sql
 * checks.
 * 
 * @author George Kolokotronis *
 */
public abstract class SQLExecutionHandler {

	private static final Logger logger = LogManager.getLogger(SQLExecutionHandler.class);

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

		logger.info("Connecting to database " + url);

		Connection conn = null;

		try {
			Class.forName(AppConsts.TERADATA_JDBC_DRIVER);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			logger.error("Cannot connect to the database: " + url);
			throw new IllegalStateException("Cannot connect to the database", e);
		} catch (ClassNotFoundException e) {
			logger.error("Cannot find driver " + AppConsts.TERADATA_JDBC_DRIVER + " in the classpath");
			throw new IllegalStateException("Cannot find the driver in the classpath!", e);
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

		logger.info("Connecting to database " + url);

		Connection conn = null;

		try {
			Class.forName(AppConsts.MYSQL_JDBC_DRIVER);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			logger.error("Cannot connect to the database: " + url);
			throw new IllegalStateException("Cannot connect to the database", e);
		} catch (ClassNotFoundException e) {
			logger.error("Cannot find driver " + AppConsts.MYSQL_JDBC_DRIVER + " in the classpath");
			throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}

		return conn;
	}

}
