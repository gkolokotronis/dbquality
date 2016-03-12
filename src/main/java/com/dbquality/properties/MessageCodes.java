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

	/**
	 * Cannot find file {0}
	 */
	public static final int ERR_CANNOT_FIND_FILE = -1004;

	/**
	 * Input/outpur error while parsing xml file {0}
	 */
	public static final int ERR_INP_OUT_WHILE_PARSING_XML = -1005;

	/**
	 * Error while parsing xml file {0}
	 */
	public static final int ERR_WHILE_PARSING_XML = -1006;

	/**
	 * Something went wrong while loading xsd file {0} from the root of the jar
	 * file
	 */
	public static final int ERR_WHILE_LOADING_XSD = -1007;

	/**
	 * Something went wrong while parsing XML file {0} to DOM object
	 */
	public static final int ERR_WHILE_LOADING_PARSING_XML_TO_DOM = -1008;

	/**
	 * Something went wrong while parsing XSD file {0}
	 */
	public static final int ERR_WHILE_LOADING_PARSING_XSD = -1009;

	/**
	 * Error while validating file: {0} with {1}
	 */
	public static final int ERR_WHILE_VALIDATING_XML_WITH_XSD = -1010;

	/**
	 * Something went wrong while executing {0}. Error: {1}
	 */
	public static final int ERR_WHILE_EXECUTING_SQL = -1011;

	/**
	 * The database provided in the {0} file is wrong
	 */
	public static final int ERR_WRONG_DATABASE_IN_PROPERTIES_FILE = -1012;

	/**
	 * Something went wrong while reading property file {0}
	 */
	public static final int ERR_WHILE_READING_PROPERTY_FILE = -1013;

	/**
	 * A database error has occurred while validating results for {0} check {1}.
	 * Error: {2}
	 */
	public static final int ERR_WHILE_VALIDATING_RESULTS = -1014;

	/**
	 * Error on {0} check {1}, the value: {2} needs to be {3} {4}
	 */
	public static final int ERR_WRONG_EXPECTED_COUNT = -1015;

	/**
	 * Error on {0} check: {1}. Result of COUNT(*) statement can have only one
	 * row
	 */
	public static final int ERR_WRONG_RESULT_CHECK = -1016;

	/**
	 * Something went wrong while executing {0}. Error: {1}
	 */
	public static final int ERR_WRONG_EXECUTING_SQL = -1017;

	/**
	 * Error on {0} check {1} Value found: {2} but expecting one of {3}
	 */
	public static final int ERR_WRONG_VALID_VALUE = -1018;

	/**
	 * Error on {0} check {1}. Null value found. Expecting one of: {2}
	 */
	public static final int ERR_NULL_VALUE_FOUND = -1019;

	/**
	 * Error on {0} check {1}. No rows found in {2}.{3}
	 */
	public static final int ERR_NO_ROWS_FOUND = -1020;

	/**
	 * Error on {0} check {1}. Cannot parse value {2} as DATE with dateformat:
	 * {3} Found in file: {4}
	 */
	public static final int ERR_NOT_VALID_DATE = -1021;

	/**
	 * Error on {0} check {1}. Something went wrong while validating if both
	 * dates have the same value
	 */
	public static final int ERR_VALIDATING_BOTH_DATES = -1022;

	/**
	 * Error on {0} check {1}. Cannot parse value {1} as DECIMAL. Found in file:
	 * {2}
	 */
	public static final int ERR_NOT_VALID_DECIMAL = -1023;

	/**
	 * Error on {0} check {1}. Cannot parse value {2} as INTEGER. Found in file:
	 * {3}
	 */
	public static final int ERR_NOT_VALID_INTEGER = -1024;

	/**
	 * Error on {0} check {1}. Column {2} is of type DATE but has no value for
	 * element <dateFormat>.
	 */
	public static final int ERR_DATE_WITHOUT_DATE_FORMAT = -1025;

	/**
	 * Error on {0} check {1}. Unexpected value for type of column
	 */
	public static final int ERR_UNEXPECTED_VALUE_OF_TYPE = -1026;

	/**
	 * {0} is not a valid check type. Available check types are {1}
	 */
	public static final int ERR_WRONG_CHECK_TYPE_PROVIDED = -1027;

	/**
	 * Found two {0} checks with the same {1}: {2}. Please check file: {3}
	 */
	public static final int ERR_DUPLICATE_CHECKS = -1028;

	/**
	 * Resource Bundle: {0} is missing
	 */
	public static final int ERR_MISSING_BUNDLE = -1029;

	/**
	 * The column type: {0} is invalid
	 */
	public static final int ERR_INVALID_COLUMN_TYPE = -1030;

	/**
	 * Something went wrong while validating {0} checks. Please check the log
	 * before this error for more info
	 */
	public static final int ERR_INVALID_CHECKS = -1031;

	/**
	 * The value {0} for expectedCountCheck is not valid. Valid values are: {1}
	 */
	public static final int ERR_INVALID_EXPECTED_COUNT_CHECK = -1032;

	/**
	 * Error on {0} check {1}. Cannot parse value {2} as DATE with dateformat:
	 * {3} Found in column: {4} Table: {5} Database: {6}
	 */
	public static final int ERR_RESULT_NOT_VALID_DATE = -1033;

	/**
	 * Error on {0} check {1}. Cannot parse value {1} as DECIMAL. Found in
	 * column: {2} Table: {3} Database: {4}
	 */
	public static final int ERR_RESULT_NOT_VALID_DECIMAL = -1034;

	/**
	 * Error on {0} check {1}. Cannot parse value {0} as INTEGER. Found in
	 * column: {1} Table: {2} Database: {4}
	 */
	public static final int ERR_RESULT_NOT_VALID_INTEGER = -1035;

	/**
	 * Error on {0} check {1}. The value {2} for expectedCountCheck is not
	 * valid. Valid values are: {3}
	 */
	public static final int ERR_INVALID_EXPECTED_COUNT_CHECK_FOR_CHECK = -1036;
}
