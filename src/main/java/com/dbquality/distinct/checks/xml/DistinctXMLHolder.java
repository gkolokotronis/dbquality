package com.dbquality.distinct.checks.xml;

import org.apache.logging.log4j.Level;

import com.dbquality.checks.CheckTypeEnum;
import com.dbquality.checks.xml.ChecksCreatorUtils;
import com.dbquality.consts.AppConsts;
import com.dbquality.distinct.checks.elements.DistinctRootElement;
import com.dbquality.exceptions.ExceptionFactory;
import com.dbquality.logs.DQLogger;
import com.dbquality.properties.ApplicationMessagesHolder;
import com.dbquality.properties.ApplicationPropertiesHolder;
import com.dbquality.properties.MessageCodes;

/**
 * Singleton class which acts as a holder for the xml file of distinct checks
 * 
 * @author George Kolokotronis
 *
 */
public final class DistinctXMLHolder {

	private static final DQLogger logger = DQLogger.create(DistinctXMLHolder.class);

	private static final DistinctXMLHolder SINGLETON = new DistinctXMLHolder();

	private DistinctRootElement distinctElement;

	private DistinctXMLHolder() {

		load();

		if (!validate()) {
			throw ExceptionFactory.createException(RuntimeException.class, MessageCodes.ERR_INVALID_CHECKS,
					null, logger, Level.ERROR, CheckTypeEnum.DISTINCT);
		}

	}

	/**
	 * Loads the xml files
	 */
	private void load() {

		String xmlDistinct = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_CHECKS_DISTINCT_XML_LOCATION);

		logger.info(
				ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.MSG_FILE_STARTED_LOADING, xmlDistinct));
		distinctElement = ChecksCreatorUtils.loadDistinctChecks(xmlDistinct, AppConsts.DISTINCT_XSD_LOCATION);

	}

	/**
	 * Validates the xml file against some rules
	 * 
	 * @return true if the xml is valid and false otherwise
	 */
	private boolean validate() {
		DistinctXMLValidator validator = new DistinctXMLValidator(getDistinctElement().getColumns());
		return validator.validate();

	}

	/**
	 * Returns a singleton instance of DistinctXMLHolder.
	 * 
	 * @return DistinctXMLHolder singleton instance
	 */

	public static DistinctXMLHolder getInstance() {
		return SINGLETON;
	}

	/**
	 * Returns the XML file of distinct checks.
	 * 
	 * @return DistinctElement
	 */
	public DistinctRootElement getDistinctElement() {
		return this.distinctElement;
	}
}
