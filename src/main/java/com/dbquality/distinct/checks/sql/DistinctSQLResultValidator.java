package com.dbquality.distinct.checks.sql;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.distinct.checks.elements.ColumnDistinctElement;
import com.dbquality.properties.ApplicationMessagesHolder;
import com.dbquality.properties.MessageCodes;
import com.dbquality.utils.TypeUtils;

public class DistinctSQLResultValidator {

	private static final Logger logger = LogManager.getLogger(DistinctSQLResultValidator.class);

	private ResultSet rs;
	private ColumnDistinctElement columnToValidate;

	public DistinctSQLResultValidator(ResultSet rs, ColumnDistinctElement columnToValidate) {
		this.rs = rs;
		this.columnToValidate = columnToValidate;
	}

	public boolean validate() throws SQLException {

		ColumnDistinctElement column = getColumnToValidate();

		List<String> distinctValues = column.getValues();

		if (rs.isBeforeFirst()) {

			while (rs.next()) {

				String valueOfColumn = rs.getString(1);

				boolean found = validateValidValues(column, valueOfColumn);

				if (!found && StringUtils.isNotEmpty(valueOfColumn)) {
					logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_WRONG_VALID_VALUE,
							column.getId(), valueOfColumn, distinctValues.toString()));

				} else if (!found && !column.isNullable() && StringUtils.isEmpty(valueOfColumn)) {
					logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_NULL_VALUE_FOUND,
							column.getId(), distinctValues.toString()));
				}

			}

		} else {
			logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_NO_ROWS_FOUND,
					column.getDatabaseName(), column.getTableName()));

		}

		return true;
	}

	/**
	 * Checks if the value of the column is part of the valid values of the
	 * column. In addition if the value of the column is not of the type
	 * mentioned in the xml then it is written in the log.
	 * 
	 * @param column
	 *            - column to be checked
	 * @param valueOfColumn
	 *            - value of the column in the database
	 * @return true if the valueOfColumn is part of the valid values, false in
	 *         any other case
	 */
	private boolean validateValidValues(ColumnDistinctElement column, String valueOfColumn) {

		if (StringUtils.isEmpty(valueOfColumn)) {
			return false;
		}

		for (String validValue : column.getValues()) {

			switch (column.getType()) {
			case DATE:
				if (!TypeUtils.validateDate(valueOfColumn, column.getDateFormat())) {
					logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_NOT_VALID_DATE,
							valueOfColumn, column.getDateFormat(), column.getName(), column.getTableName(),
							column.getDatabaseName()));
				} else {
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

					try {
						Date dateOfColumn = format.parse(valueOfColumn);
						Date dateOfValidValue = format.parse(validValue);
						if (DateUtils.isSameDay(dateOfColumn, dateOfValidValue)) {
							return true;
						}
					} catch (ParseException e) {
						// the code will never reach here since both dates were
						// validated. XML valid value were validated on the
						// loading of the xml and value of the column was
						// validated on the previous step
						logger.error(ApplicationMessagesHolder.getInstance()
								.getMessage(MessageCodes.ERR_VALIDATING_BOTH_DATES));
						return false;
					}
				}

				break;
			case DECIMAL:
				try {
					BigDecimal valueColumn = BigDecimal.valueOf(Double.parseDouble(valueOfColumn));

					if (valueColumn.compareTo(BigDecimal.valueOf(Double.parseDouble(validValue))) == 0) {
						return true;
					}
				} catch (NumberFormatException e) {
					logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_NOT_VALID_DECIMAL,
							valueOfColumn, column.getName(), column.getTableName(), column.getDatabaseName()));
					return false;

				}
				break;
			case INTEGER:
				try {

					if (Integer.compare(Integer.parseInt(valueOfColumn), Integer.parseInt(validValue)) == 0) {
						return true;
					}
				} catch (NumberFormatException e) {
					logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_NOT_VALID_INTEGER,
							valueOfColumn, column.getName(), column.getTableName(), column.getDatabaseName()));
					return false;

				}
				break;
			case VARCHAR:
				if (StringUtils.equals(validValue, valueOfColumn)) {
					return true;
				}
				break;

			}

		}

		return false;

	}

	/**
	 * @return the rs
	 */
	public ResultSet getRs() {
		return rs;
	}

	/**
	 * @param rs
	 *            the rs to set
	 */
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	/**
	 * @return the columnToValidate
	 */
	public ColumnDistinctElement getColumnToValidate() {
		return columnToValidate;
	}

	/**
	 * @param columnToValidate
	 *            the columnToValidate to set
	 */
	public void setColumnToValidate(ColumnDistinctElement columnToValidate) {
		this.columnToValidate = columnToValidate;
	}

}
