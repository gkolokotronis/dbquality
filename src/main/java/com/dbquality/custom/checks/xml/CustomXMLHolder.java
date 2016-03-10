package com.dbquality.custom.checks.xml;

import org.apache.logging.log4j.Level;

import com.dbquality.checks.CheckTypeEnum;
import com.dbquality.checks.xml.ChecksCreatorUtils;
import com.dbquality.consts.AppConsts;
import com.dbquality.custom.checks.elements.CustomRootElement;
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
public final class CustomXMLHolder {

	private static final DQLogger logger = DQLogger.create(CustomXMLHolder.class);

	private static final CustomXMLHolder SINGLETON = new CustomXMLHolder();

	private CustomRootElement customElement;

	private CustomXMLHolder() {

		load();

		if (!validate()) {
			throw ExceptionFactory.createException(RuntimeException.class, MessageCodes.ERR_INVALID_CHECKS,
					null, logger, Level.ERROR, CheckTypeEnum.CUSTOM);

		}

	}

	/**
	 * Loads the xml files
	 */
	private void load() {

		String xmlDistinct = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_CHECKS_CUSTOM_XML_LOCATION);

		logger.info(
				ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.MSG_FILE_STARTED_LOADING, xmlDistinct));
		customElement = ChecksCreatorUtils.loadCustomChecks(xmlDistinct, AppConsts.CUSTOM_XSD_LOCATION);

	}

	/**
	 * Validates the xml file against some rules
	 * 
	 * @return true if the xml is valid and false otherwise
	 */
	private boolean validate() {
		CustomXMLValidator validator = new CustomXMLValidator(getCustomElement().getChecks());
		return validator.validate();

	}

	/**
	 * Returns a singleton instance of CustomXMLHolder.
	 * 
	 * @return CustomXMLHolder singleton instance
	 */

	public static CustomXMLHolder getInstance() {
		return SINGLETON;
	}

	/**
	 * Returns the XML file of custom checks.
	 * 
	 * @return CustomRootElement
	 */
	public CustomRootElement getCustomElement() {
		return this.customElement;
	}
}
