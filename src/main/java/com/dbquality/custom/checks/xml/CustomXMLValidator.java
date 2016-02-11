package com.dbquality.custom.checks.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Validator class holding validations related to custom checks
 * 
 * @author George Kolokotronis
 *
 */
public class CustomXMLValidator {

	private static final Logger logger = LogManager.getLogger(CustomXMLValidator.class);

	public CustomXMLValidator() {

	}

	/**
	 * runs validations on each custom check of the xml
	 * 
	 * @return true if all the elements were validated. false if something went
	 *         wrong with an element
	 */
	public boolean validate() {

		return true;

	}

}
