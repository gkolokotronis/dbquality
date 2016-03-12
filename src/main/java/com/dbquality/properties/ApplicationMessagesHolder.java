package com.dbquality.properties;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;

import com.dbquality.exceptions.ExceptionFactory;
import com.dbquality.logs.DQLogger;

/**
 * The singleton instance of ApplicationMessagesHolder
 * 
 * @author George Kolokotronis
 *
 */
public final class ApplicationMessagesHolder {

	private static final DQLogger logger = DQLogger.create(ApplicationMessagesHolder.class);

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
			throw ExceptionFactory.createException(RuntimeException.class, MessageCodes.ERR_MISSING_BUNDLE, e, logger,
					Level.ERROR, BUNDLE_NAME);
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
