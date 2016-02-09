package com.dbquality.distinct.checks.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.consts.AppConsts;
import com.dbquality.distinct.checks.elements.DistinctRootElement;
import com.dbquality.properties.ApplicationPropertiesHolder;

/**
 * Singleton class which acts as a holder for the xml file of distinct checks
 * 
 * @author George Kolokotronis
 *
 */
public final class DistinctXMLHolder {

	private static final Logger logger = LogManager.getLogger(DistinctXMLHolder.class);

	private static final DistinctXMLHolder SINGLETON = new DistinctXMLHolder();

	private DistinctRootElement distinctElement;

	private DistinctXMLHolder() {

		load();

		if (!validate()) {
			throw new RuntimeException(
					"Something went wrong while validating distinct checks. Please check the log for more info.");
		}

	}

	/**
	 * Loads the xml files
	 */
	private void load() {

		String xmlDistinct = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_CHECKS_DISTINCT_XML_LOCATION);

		System.out.println("Started loading " + xmlDistinct);
		logger.info("Started loading " + xmlDistinct);
		distinctElement = new DistinctChecksCreator().loadDQChecks(xmlDistinct);

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
