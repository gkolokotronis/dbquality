package com.dbquality;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.consts.AppConsts;
import com.dbquality.custom.checks.elements.CustomRootElement;
import com.dbquality.custom.checks.sql.CustomSQLExecutionHandler;
import com.dbquality.custom.checks.xml.CustomXMLHolder;
import com.dbquality.distinct.checks.elements.DistinctRootElement;
import com.dbquality.distinct.checks.sql.DistinctSQLExecutionHandler;
import com.dbquality.distinct.checks.xml.DistinctXMLHolder;
import com.dbquality.properties.ApplicationPropertiesHolder;

/**
 * This class is responsible for handling the execution of the application
 * 
 * @author George Kolokotronis
 *
 */
public class DBQualityExecutionHandler {

	private static final Logger logger = LogManager.getLogger(DBQualityExecutionHandler.class);

	public boolean runDistinctChecks;

	public boolean runCustomChecks;

	public DBQualityExecutionHandler() {
		this.runCustomChecks = false;
		this.runDistinctChecks = false;
	}

	public void execute() {

		System.out.println("Application Started");
		logger.info("Application Started");

		checkConfigProperties();
		setProperties();
		if (isRunDistinctChecks()) {
			runDistinctChecks();
		}
		if (isRunCustomChecks()) {
			runCustomChecks();
		}

		logger.info("Application Ended");
		System.out.println("Application Ended");

	}

	/**
	 * Checks if application properties file exists or not.
	 *
	 */
	private void checkConfigProperties() throws IllegalArgumentException {
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
	private void runDistinctChecks() {
		logger.info("Started working on distinct checks");

		DistinctRootElement distinctElement = DistinctXMLHolder.getInstance().getDistinctElement();

		DistinctSQLExecutionHandler distinctExec = new DistinctSQLExecutionHandler(distinctElement);

		distinctExec.execute();
	}

	/**
	 * Initiates the object for creating and running the distinct checks
	 */
	private void runCustomChecks() {
		logger.info("Started working on custom checks");

		CustomRootElement customElement = CustomXMLHolder.getInstance().getCustomElement();

		CustomSQLExecutionHandler customExec = new CustomSQLExecutionHandler(customElement);

		customExec.execute();

	}

	/**
	 * Sets the properties according to the properties file
	 */
	private void setProperties() {
		String runDistinctChecks;
		String runCustomChecks;

		runDistinctChecks = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_CHECKS_DISTINCT_RUN);
		runCustomChecks = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_CHECKS_DISTINCT_RUN);

		setRunDistinctChecks(Boolean.parseBoolean(runDistinctChecks));
		setRunCustomChecks(Boolean.parseBoolean(runCustomChecks));
	}

	/**
	 * @return the runDistinctChecks
	 */
	public boolean isRunDistinctChecks() {
		return runDistinctChecks;
	}

	/**
	 * @param runDistinctChecks
	 *            the runDistinctChecks to set
	 */
	public void setRunDistinctChecks(boolean runDistinctChecks) {
		this.runDistinctChecks = runDistinctChecks;
	}

	/**
	 * @return the runCustomChecks
	 */
	public boolean isRunCustomChecks() {
		return runCustomChecks;
	}

	/**
	 * @param runCustomChecks
	 *            the runCustomChecks to set
	 */
	public void setRunCustomChecks(boolean runCustomChecks) {
		this.runCustomChecks = runCustomChecks;
	}

}
