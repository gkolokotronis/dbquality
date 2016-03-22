package com.dbquality;

import com.dbquality.consts.AppConsts;
import com.dbquality.custom.checks.elements.CustomRootElement;
import com.dbquality.custom.checks.sql.CustomSQLExecutionHandler;
import com.dbquality.custom.checks.xml.CustomXMLHolder;
import com.dbquality.distinct.checks.elements.DistinctRootElement;
import com.dbquality.distinct.checks.sql.DistinctSQLExecutionHandler;
import com.dbquality.distinct.checks.xml.DistinctXMLHolder;
import com.dbquality.logs.DQLogger;
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

	private static final DQLogger logger = DQLogger.create(DBQualityExecutionHandler.class);

	public boolean runDistinctChecks;

	public boolean runCustomChecks;

	public DBQualityExecutionHandler() {
		this.runCustomChecks = false;
		this.runDistinctChecks = false;
	}

	public void execute() {

		logger.info(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.MSG_APPLICATION_STARTED));

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
