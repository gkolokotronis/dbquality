package com.dbquality;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.consts.AppConsts;
import com.dbquality.custom.checks.elements.CustomRootElement;
import com.dbquality.custom.checks.xml.CustomXMLHolder;
import com.dbquality.distinct.checks.elements.DistinctRootElement;
import com.dbquality.distinct.checks.sql.DistinctSQLExecutionHandler;
import com.dbquality.distinct.checks.xml.DistinctXMLHolder;

/**
 * This class is responsible for handling the execution of the application
 * 
 * @author George Kolokotronis
 *
 */
public class DBQualityExecutionHandler {

	private static final Logger logger = LogManager.getLogger(DBQualityExecutionHandler.class);

	public void execute() {

		System.out.println("Application Started");
		logger.info("Application Started");

		checkConfigProperties();
		runDistinctChecks();
		runCustomChecks();

		logger.info("Application Ended");
		System.out.println("Application Ended");

	}

	/**
	 * Checks if application properties file exists or not.
	 *
	 */
	protected void checkConfigProperties() throws IllegalArgumentException {
		logger.info("Checking if " + AppConsts.PROPERTIES_FILE_NAME + " exists");

		String propertiesPath = System.getProperty("user.dir") + System.getProperty("file.separator")
				+ AppConsts.PROPERTIES_FILE_NAME;
		File varTmpDir = new File(propertiesPath);
		boolean exists = varTmpDir.exists();

		if (!exists) {
			logger.error("Property file " + propertiesPath + " not found");
			throw new IllegalArgumentException("Property file " + propertiesPath + " not found");

		}
	}

	/**
	 * Initiates the object for creating and running the distinct checks
	 */
	protected void runDistinctChecks() {
		logger.info("Started working on distinct checks");

		DistinctRootElement distinctElement = DistinctXMLHolder.getInstance().getDistinctElement();

		DistinctSQLExecutionHandler distinctExec = new DistinctSQLExecutionHandler(distinctElement);

		distinctExec.execute();
	}

	/**
	 * Initiates the object for creating and running the distinct checks
	 */
	protected void runCustomChecks() {
		logger.info("Started working on custom checks");

		CustomRootElement customElement = CustomXMLHolder.getInstance().getCustomElement();

	}

}
