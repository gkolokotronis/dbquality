package com.dbquality.properties;

/**
 * This class is used to store all resources codes in the application
 * 
 * @author Georgios Kolokotronis
 */
public class MessageCodes {

	// ###########################################################
	// GENERAL SYSTEM ERROR MESSAGES range 1-999)
	// Note: All constants should start with 'MSG_'
	// ###########################################################
	/**
	 * Application Started
	 */
	public static final int MSG_APPLICATION_STARTED = -1;

	/**
	 * Application Ended
	 */
	public static final int MSG_APPLICATION_ENDED = -2;

	/**
	 * Checking if file {0} exists
	 */
	public static final int MSG_FILE_EXISTS = -3;

	/**
	 * Started working on distinct checks
	 */
	public static final int MSG_DISTINCT_CHECKS_START = -4;

	/**
	 * Started working on custom checks
	 */
	public static final int MSG_CUSTOM_CHECKS_START = -5;

	/**
	 * Started population of SQLs for custom checks
	 */
	public static final int MSG_CUSTOM_CHECKS_START_POPULATION_SQL = -6;

	/**
	 * Started execution of the custom checks SQLs and their validation
	 */
	public static final int MSG_CUSTOM_CHECKS_START_EXECUTION_VALIDATION_SQL = -7;

	/**
	 * Started loading file {0}
	 */
	public static final int MSG_FILE_STARTED_LOADING = -8;

	/**
	 * Started population of SQLs for distinct checks
	 */
	public static final int MSG_DISTINCT_CHECKS_START_POPULATION_SQL = -9;

	/**
	 * Started execution of the distinct checks SQLs and their validation
	 */
	public static final int MSG_DISTINCT_CHECKS_START_EXECUTION_VALIDATION_SQL = -10;

	/**
	 * Connecting to database {0}
	 */
	public static final int MSG_DB_CONNECTING_TO_DATABASE = -11;

	// ###########################################################
	// GENERAL SYSTEM ERROR MESSAGES range 1000-1999)
	// Note: All constants should start with 'ERR_'
	// ###########################################################
	/**
	 * Property file {0} not found
	 */
	public static final int ERR_PROPERTY_FILE_NOT_FOUND = -1000;

	/**
	 * Exception while creating an instance of: {0}
	 */
	public static final int ERR_WHILE_CREATING_INSTANCE_OF = -1001;

	/**
	 * Cannot connect to the database: {0}
	 */
	public static final int ERR_DB_CANNOT_CONNECT_TO_DATABASE = -1002;

	/**
	 * Cannot find driver {0} in the classpath
	 */
	public static final int ERR_DB_CANNOT_FIND_DRIVER_IN_CLASSPATH = -1003;

}
