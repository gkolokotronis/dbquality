package com.dbquality.distinct.checks.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.checks.ColumnTypeEnum;
import com.dbquality.distinct.checks.elements.ColumnDistinctElement;
import com.dbquality.properties.ApplicationMessagesHolder;
import com.dbquality.properties.MessageCodes;
import com.dbquality.utils.TypeUtils;

/**
 * Validator class holding validations related to distinct checks
 * 
 * @author George Kolokotronis
 *
 */
public class DistinctXMLValidator {

	private static final Logger logger = LogManager.getLogger(DistinctXMLValidator.class);

	private List<ColumnDistinctElement> columns;

	private Map<Integer, ColumnDistinctElement> columnElements;

	public DistinctXMLValidator(List<ColumnDistinctElement> columns) {
		this.columns = columns;
		this.columnElements = new HashMap<Integer, ColumnDistinctElement>();
	}

	/**
	 * runs validations on each distinct column element of the xml
	 * 
	 * @return true if all the elements were validated. false if something went
	 *         wrong with an element
	 */
	public boolean validate() {

		for (ColumnDistinctElement column : getColumns()) {

			if (!validateDuplicateId(column) || !validateDateFormat(column) || !validateColumnType(column)) {
				return false;
			}

		}
		return true;

	}

	/**
	 * Validates if the column has type date then it needs to have a date format
	 * as well
	 * 
	 * @param column
	 *            - column element to be validated
	 * @return - true if it has a date format and column has type Date, false if
	 *         column is of type Date and it does not have a dateFormat. True in
	 *         any other case
	 */
	private boolean validateDateFormat(ColumnDistinctElement column) {
		if (ColumnTypeEnum.DATE.equals(column.getType())) {
			if (StringUtils.isNotEmpty(column.getDateFormat())) {
				return true;
			}
			logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_DATE_WITHOUT_DATE_FORMAT,
					column.getId(), column.getType()));

			return false;
		} else {
			return true;
		}
	}

	/**
	 * Validates that the column has a unique id
	 * 
	 * @param column
	 *            column to be checked if it has duplicate id
	 * @return - true if the is not another column with the same id, false it
	 *         another column with the same id is found
	 */
	private boolean validateDuplicateId(ColumnDistinctElement column) {

		Integer key = column.getId();

		ColumnDistinctElement returnColumn = columnElements.put(key, column);

		if (returnColumn != null) {
			logger.error(
					ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_DISTINCT_CHECKS_SAME_ID, key));
			return false;
		}
		return true;

	}

	/**
	 * Validates the valid values of the column depending on its type
	 * 
	 * @param column
	 *            column to be validated
	 * @return true if all the valid values of the column match its type, false
	 *         if one of the valid values does not match the column's type
	 */
	private boolean validateColumnType(ColumnDistinctElement column) {

		switch (column.getType()) {
		case DATE:
			if (!validateDateValues(column)) {
				return false;
			}

			break;

		case INTEGER:
			if (!validateIntegerValues(column)) {
				return false;
			}

			break;

		case DECIMAL:
			if (!validateDecimalValues(column)) {
				return false;
			}
			break;
		case VARCHAR:
			break;
		default:
			logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_UNEXPECTED_VALUE_OF_TYPE));
			return false;

		}

		return true;
	}

	/**
	 * validates if date has the correct date format
	 * 
	 * @param column
	 *            - column element to check its valid values
	 * @return true if column valid values are all dates, otherwise false
	 */
	private boolean validateDateValues(ColumnDistinctElement column) {
		for (String value : column.getValues()) {

			if (!TypeUtils.validateDate(value, column.getDateFormat())) {

				logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_NOT_VALID_DATE, value,
						column.getDateFormat(), column.getName(), column.getTableName(), column.getDatabaseName()));

				return false;
			}
		}
		return true;
	}

	/**
	 * validates if the value provided can be parsed to Integer
	 * 
	 * @param column
	 *            - column element to check its valid values
	 * @return - true if column valid values are all integers, otherwise false
	 */
	private boolean validateIntegerValues(ColumnDistinctElement column) {
		for (String value : column.getValues()) {

			if (!TypeUtils.validateInteger(value)) {

				logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_NOT_VALID_INTEGER,
						value, column.getName(), column.getTableName(), column.getDatabaseName()));
				return false;
			}
		}
		return true;
	}

	/**
	 * validates if the value provided can be parsed to Decimal
	 * 
	 * @param column
	 *            - column element to check its valid values
	 * @return - true if column valid values are all decimals, otherwise false
	 */
	private boolean validateDecimalValues(ColumnDistinctElement column) {
		for (String value : column.getValues()) {

			if (!TypeUtils.validateDecimal(value)) {
				logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_NOT_VALID_DECIMAL,
						value, column.getName(), column.getTableName(), column.getDatabaseName()));
				return false;
			}
		}
		return true;
	}

	/**
	 * @return the columns
	 */
	public List<ColumnDistinctElement> getColumns() {
		return columns;
	}

	/**
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(List<ColumnDistinctElement> columns) {
		this.columns = columns;
	}
}
