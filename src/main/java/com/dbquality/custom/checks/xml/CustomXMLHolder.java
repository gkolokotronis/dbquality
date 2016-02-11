package com.dbquality.custom.checks.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.checks.xml.ChecksCreatorUtils;
import com.dbquality.consts.AppConsts;
import com.dbquality.custom.checks.elements.CustomRootElement;
import com.dbquality.properties.ApplicationPropertiesHolder;

/**
 * Singleton class which acts as a holder for the xml file of distinct checks
 * 
 * @author George Kolokotronis
 *
 */
public final class CustomXMLHolder {

	private static final Logger logger = LogManager.getLogger(CustomXMLHolder.class);

	private static final CustomXMLHolder SINGLETON = new CustomXMLHolder();

	private CustomRootElement customElement;

	private CustomXMLHolder() {

		load();

		if (!validate()) {
			throw new RuntimeException(
					"Something went wrong while validating custom checks. Please check the log for more info.");
		}

	}

	/**
	 * Loads the xml files
	 */
	private void load() {

		String xmlDistinct = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_CHECKS_CUSTOM_XML_LOCATION);

		System.out.println("Started loading " + xmlDistinct);
		logger.info("Started loading " + xmlDistinct);
		customElement = ChecksCreatorUtils.loadCustomChecks(xmlDistinct, AppConsts.CUSTOM_XSD_LOCATION);

	}

	/**
	 * Validates the xml file against some rules
	 * 
	 * @return true if the xml is valid and false otherwise
	 */
	private boolean validate() {
		CustomXMLValidator validator = new CustomXMLValidator();
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
