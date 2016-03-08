package com.dbquality.properties;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The singleton instance of ApplicationMessagesHolder
 * 
 * @author George Kolokotronis
 *
 */
public final class ApplicationMessagesHolder {

	private final static Logger logger = LogManager.getLogger(ApplicationMessagesHolder.class);

	private final static ApplicationMessagesHolder SINGLETON = new ApplicationMessagesHolder();

	private final static String BUNDLE_NAME = "ApplicationMessages";

	private final static String MESSAGE_CODE_PREFIX = "DBQ";

	/**
	 * Resource bundle, contains all the messages from
	 * ApplicationMessages.properties
	 */
	private ResourceBundle resourceMessages;

	/**
	 * Private default constructor
	 */
	private ApplicationMessagesHolder() {
		try {
			this.resourceMessages = getResourceBundle(BUNDLE_NAME);
		} catch (MissingResourceException e) {
			String msg = "Resource Bundle - ApplicationMessages is missing";
			logger.error(msg);
			throw new RuntimeException(msg, e);
		}

	}

	/**
	 * Returns a singleton instance of ApplicationMessagesHolder.
	 * 
	 * @return ApplicationMessagesHolder singleton instance
	 */
	public static ApplicationMessagesHolder getInstance() {
		return SINGLETON;
	}

	public String getMessage(int messageCode, Object... args) {
		String message = null;

		message = this.resourceMessages.getString(MESSAGE_CODE_PREFIX + messageCode);

		message = MessageFormat.format(message, args);

		return message;
	}

	/**
	 * Returns resource bundle based on default locale defined in
	 * aprm-portal-config.xml file
	 * 
	 * @param bundleName
	 *            - the name of the resource bundle.
	 * 
	 * @return Resource Bundle
	 */
	private static ResourceBundle getResourceBundle(String bundleName) {

		return ResourceBundle.getBundle(bundleName);
	}
}
