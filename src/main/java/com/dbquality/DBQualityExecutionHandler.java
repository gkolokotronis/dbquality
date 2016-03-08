package com.dbquality;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.consts.AppConsts;
import com.dbquality.custom.checks.elements.CustomRootElement;
import com.dbquality.custom.checks.sql.CustomSQLExecutionHandler;
import com.dbquality.custom.checks.xml.CustomXMLHolder;
import com.dbquality.distinct.checks.elements.DistinctRootElement;
import com.dbquality.distinct.checks.sql.DistinctSQLExecutionHandler;
import com.dbquality.distinct.checks.xml.DistinctXMLHolder;
import com.dbquality.exceptions.ExceptionFactory;
import com.dbquality.properties.ApplicationMessagesHolder;
import com.dbquality.properties.ApplicationPropertiesHolder;
import com.dbquality.properties.MessageCodes;

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

		logger.info(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.MSG_APPLICATION_STARTED));

		checkConfigProperties();
		setProperties();
		if (isRunDistinctChecks()) {
			runDistinctChecks();
		}
		if (isRunCustomChecks()) {
			runCustomChecks();
		}

		logger.info(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.MSG_APPLICATION_ENDED));

	}

	/**
	 * Checks if application properties file exists or not.
	 *
	 */
	private void checkConfigProperties() throws IllegalArgumentException {
		logger.info(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.MSG_FILE_EXISTS,
				AppConsts.PROPERTIES_FILE_NAME));

		String propertiesPath = System.getProperty("user.dir") + System.getProperty("file.separator")
				+ AppConsts.PROPERTIES_FILE_NAME;
		File varTmpDir = new File(propertiesPath);
		boolean exists = varTmpDir.exists();

		if (!exists) {
			throw ExceptionFactory.createException(IllegalArgumentException.class,
					MessageCodes.ERR_PROPERTY_FILE_NOT_FOUND, null, logger, Level.ERROR, propertiesPath);

		}
	}

	/**
	 * Initiates the object for creating and running the distinct checks
	 */
	private void runDistinctChecks() {
		logger.info(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.MSG_DISTINCT_CHECKS_START));

		DistinctRootElement distinctElement = DistinctXMLHolder.getInstance().getDistinctElement();

		DistinctSQLExecutionHandler distinctExec = new DistinctSQLExecutionHandler(distinctElement);

		distinctExec.execute();
	}

	/**
	 * Initiates the object for creating and running the distinct checks
	 */
	private void runCustomChecks() {
		logger.info(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.MSG_CUSTOM_CHECKS_START));

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
