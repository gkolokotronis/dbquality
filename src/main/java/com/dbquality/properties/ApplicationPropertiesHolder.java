package com.dbquality.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.consts.AppConsts;

public final class ApplicationPropertiesHolder {

	private static final Logger logger = LogManager.getLogger(ApplicationPropertiesHolder.class);

	private static final ApplicationPropertiesHolder SINGLETON = new ApplicationPropertiesHolder();

	private Properties properties = new Properties();

	private ApplicationPropertiesHolder() {
		// todo change this
		String propertiesPath = System.getProperty("user.dir") + System.getProperty("file.separator")
				+ AppConsts.PROPERTIES_FILE_NAME;
		load(propertiesPath);
	}

	public static ApplicationPropertiesHolder getInstance() {
		return SINGLETON;
	}

	/**
	 * Returns the XML file of distinct checks.
	 * 
	 * @return DistinctElement
	 */
	public Properties getProperties() {
		return this.properties;
	}

	private void load(String propertiesFilePath) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(propertiesFilePath);
		} catch (FileNotFoundException e) {
			logger.error("Property file '" + propertiesFilePath + "' not found");
			throw new RuntimeException("Property file '" + propertiesFilePath + "' not found", e);

		}

		try {
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error("Something went wrong while reading property file '" + propertiesFilePath + "' not found");
			throw new RuntimeException(
					"Somethinh went wrong while reading property file '" + propertiesFilePath + "' not found", e);

		}

	}

}
