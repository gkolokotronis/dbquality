package com.dbquality.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.Level;

import com.dbquality.consts.AppConsts;
import com.dbquality.exceptions.ExceptionFactory;
import com.dbquality.logs.DQLogger;

public final class ApplicationPropertiesHolder {

	private static final DQLogger logger = DQLogger.create(ApplicationPropertiesHolder.class);

	private static final ApplicationPropertiesHolder SINGLETON = new ApplicationPropertiesHolder();

	private Properties properties = new Properties();

	private ApplicationPropertiesHolder() {

		String propertiesPath = System.getProperty("user.dir") + System.getProperty("file.separator")
				+ AppConsts.PROPERTIES_FILE_NAME;
		load(propertiesPath);
	}

	public static ApplicationPropertiesHolder getInstance() {
		return SINGLETON;
	}

	/**
	 * Returns the properties file.
	 * 
	 * @return DistinctElement
	 */
	public Properties getProperties() {
		return this.properties;
	}

	/**
	 * It loads the properties path
	 * 
	 * @param propertiesFilePath
	 *            - the file path of the properties file
	 */
	private void load(String propertiesFilePath) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(propertiesFilePath);
		} catch (FileNotFoundException e) {
			throw ExceptionFactory.createException(RuntimeException.class, "Property file %s not found", e, logger,
					Level.ERROR, propertiesFilePath);

		}

		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw ExceptionFactory.createException(RuntimeException.class,
					"Something went wrong while reading property file %s", e, logger, Level.ERROR, propertiesFilePath);
		}

	}

}
