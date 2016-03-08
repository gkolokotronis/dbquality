package com.dbquality.exceptions;

import java.lang.reflect.Constructor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.dbquality.properties.ApplicationMessagesHolder;
import com.dbquality.properties.MessageCodes;

/**
 * This class defines exception factory which is used to create exceptions.
 * 
 * @author George Kolokotronis
 *
 */
public final class ExceptionFactory {

	/**
	 * private constructor to deny creating instances of this exception.
	 */
	private ExceptionFactory() {
		// Empty
	}

	/**
	 * Constructs a new Exception with the given error code or error message,
	 * cause of the exception, logger and list of error arguments in case error
	 * code is provided.
	 * 
	 * @param clazz
	 *            - type of the exception to be returned
	 * @param errorCode
	 *            - error code
	 * @param cause
	 *            - cause of the exception
	 * @param logger
	 *            - logger for logging the error
	 * @param level
	 *            - the priority of the logger
	 * @param args
	 *            - arguments
	 */
	public static <T> T createException(Class<T> clazz, int errorCode, Throwable cause, Logger logger, Level level,
			Object... args) {
		T ex = null;
		String msg = null;

		msg = ApplicationMessagesHolder.getInstance().getMessage(errorCode, args);

		if (logger != null && msg != null) {
			if (level == null) {
				logger.error(msg, cause);
			} else {
				logger.log(level, msg, cause);
			}
		}
		try {
			Constructor<T> constr;
			constr = clazz.getConstructor(String.class, Throwable.class);
			ex = constr.newInstance(msg, cause);

		} catch (Exception e) {
			msg = ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_WHILE_CREATING_INSTANCE_OF,
					clazz);

			throw new RuntimeException(msg, e);
		}
		return ex;
	}
}