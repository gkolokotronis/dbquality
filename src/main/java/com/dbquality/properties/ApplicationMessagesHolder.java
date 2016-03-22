package com.dbquality.properties;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import com.dbquality.consts.AppConsts;
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

	private final static String BUNDLE_NAME = "applicationmessages";

	private final static String MESSAGE_CODE_PREFIX = "DBQ";

	private static Locale locale;

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
			this.load();
		} catch (MissingResourceException e) {
			throw ExceptionFactory.createException(RuntimeException.class, MessageCodes.ERR_MISSING_BUNDLE, e, logger,
					Level.ERROR, BUNDLE_NAME);
		}

	}

	/**
	 * Loads the applicationmessages.properties file.
	 * 
	 */
	private void load() {
		Boolean wrongLocale = false;
		String country = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_LOCALE_COUNTRY);
		String language = ApplicationPropertiesHolder.getInstance().getProperties()
				.getProperty(AppConsts.PROPS_LOCALE_LANGUAGE);

		Locale newLocale = null;
		try {
			newLocale = Locale.ENGLISH;
			if (StringUtils.isNotEmpty(language) && StringUtils.isEmpty(country)) {
				newLocale = LocaleUtils.toLocale(language);
			} else if (StringUtils.isNotEmpty(language) && StringUtils.isNotEmpty(country)) {
				newLocale = LocaleUtils.toLocale(language + "_" + country);
			}

		} catch (IllegalArgumentException e) {

			wrongLocale = true;
		}

		locale = newLocale;
		this.resourceMessages = getResourceBundle(BUNDLE_NAME);
		if (wrongLocale) {
			logger.warn(
					getMessage(MessageCodes.ERR_INVALID_COUNTRY_OR_LANGUAGE, AppConsts.PROPERTIES_FILE_NAME, locale));
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
	 * application.properties file.
	 * 
	 * @param bundleName
	 *            - the name of the resource bundle.
	 * 
	 * @return Resource Bundle
	 */
	private static ResourceBundle getResourceBundle(String bundleName) {
		return ResourceBundle.getBundle(bundleName, locale);
	}

}
