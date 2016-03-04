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

	// ###########################################################
	// GENERAL SYSTEM ERROR MESSAGES range 1000-1999)
	// Note: All constants should start with 'SYS_'
	// ###########################################################
	/**
	 * Property file {0} not found
	 */
	public static final int SYS_PROPERTY_FILE_NOT_FOUND = -1000;
}
