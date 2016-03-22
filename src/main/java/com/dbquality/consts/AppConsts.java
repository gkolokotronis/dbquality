package com.dbquality.consts;

public final class AppConsts {

	/**
	 * to avoid instantiation
	 */
	private AppConsts() {

	}

	/**
	 * Log Constants
	 */
	public static String LOG_LOCATION = "/home/g0d4/workspace/dbquality/target/errors.txt";

	public static String DISTINCT_XSD_LOCATION = "distinctchecks.xsd";

	public static String CUSTOM_XSD_LOCATION = "customchecks.xsd";

	/**
	 * Properties file Constants
	 */
	public static String PROPERTIES_FILE_NAME = "application.properties";
	/**
	 * General Constants
	 */
	public static String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";

	public static String TERADATA_JDBC_DRIVER = "com.teradata.jdbc.TeraDriver";

	public static String NAME = "name";

	public static String ID = "id";

	/**
	 * PROPERTIES CONSTANSTS
	 */
	public static String PROPS_DATABASE_TYPE = "database.type";

	public static String PROPS_TERADATA_USERNAME = "teradata.username";

	public static String PROPS_TERADATA_PASSWORD = "teradata.password";

	public static String PROPS_TERADATA_DATABASE_URL = "teradata.database.url";

	public static String PROPS_MYSQL_USERNAME = "mysql.username";

	public static String PROPS_MYSQL_PASSWORD = "mysql.password";

	public static String PROPS_MYSQL_DATABASE_URL = "mysql.database.url";

	public static String PROPS_CHECKS_DISTINCT_XML_LOCATION = "checks.distinct.xml.location";

	public static String PROPS_CHECKS_CUSTOM_XML_LOCATION = "checks.custom.xml.location";

	public static String PROPS_CHECKS_DISTINCT_RUN = "checks.distinct.run";

	public static String PROPS_CHECK_CUSTOM_RUN = "checks.custom.run";

	public static String PROPS_LOCALE_COUNTRY = "locale.country";

	public static String PROPS_LOCALE_LANGUAGE = "locale.language";

}
