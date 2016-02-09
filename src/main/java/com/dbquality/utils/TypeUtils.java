package com.dbquality.utils;

import java.text.DateFormat;
import java.text.ParsePosition;

/**
 * 
 * @author George Kolokotronis
 *
 */
public final class TypeUtils {

	/**
	 * to avoid instantiation
	 */
	private TypeUtils() {

	}

	/**
	 * 
	 * @param date
	 *            - value to be parsed as Date
	 * @param dateFormat
	 *            - the date format that the value should have
	 * @return - true if value represents a date in the specific format, false
	 *         in case value does not represents a date in the provided format
	 */
	public static boolean validateDate(String date, String dateFormat) {

		DateFormat format = new StrictSimpleDateFormat(dateFormat);
		ParsePosition p = new ParsePosition(0);

		format.parse(date, p);

		if (p.getIndex() < date.length()) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param value
	 *            - value to be parsed as Integer
	 * @return - true in case value represents an integer, false in case value
	 *         does not represent an integer
	 */
	public static boolean validateInteger(String value) {

		try {
			Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param value
	 *            - value to be parsed as Double
	 * @return - true in case value represents a double, false in case value
	 *         does not represent a double
	 */
	public static boolean validateDecimal(String value) {

		try {
			Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
