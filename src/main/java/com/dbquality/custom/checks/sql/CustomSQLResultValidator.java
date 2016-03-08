package com.dbquality.custom.checks.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.custom.checks.elements.CustomCheckElement;
import com.dbquality.properties.ApplicationMessagesHolder;
import com.dbquality.properties.MessageCodes;

public class CustomSQLResultValidator {

	private static final Logger logger = LogManager.getLogger(CustomSQLResultValidator.class);

	private ResultSet rs;
	private CustomCheckElement checkToValidate;

	public CustomSQLResultValidator(ResultSet rs, CustomCheckElement check) {
		this.rs = rs;
		this.checkToValidate = check;
	}

	public boolean validate() throws SQLException {

		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				Integer valueOfCount = rs.getInt(1);

				if (!validateResult(valueOfCount)) {
					logger.error(ApplicationMessagesHolder.getInstance().getMessage(
							MessageCodes.ERR_WRONG_EXPECTED_COUNT, checkToValidate.getCheckName(), valueOfCount,
							getCheckToValidate().getExpectedCountCheck(), getCheckToValidate().getExpectedCount()));

				}
			}

		} else {
			logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_WRONG_RESULT_CHECK,
					checkToValidate.getCheckName()));

		}

		return true;

	}

	/**
	 * 
	 * @param valueOfCount
	 * @return
	 */
	private boolean validateResult(Integer valueOfCount) {
		if (valueOfCount == null) {
			return false;
		}
		CustomCheckElement check = getCheckToValidate();

		switch (check.getExpectedCountCheck()) {
		case EQUAL_TO:
			if (Integer.compare(check.getExpectedCount(), valueOfCount) != 0) {
				return false;
			}

			break;
		case GREATER_THAN:
			if (Integer.compare(check.getExpectedCount(), valueOfCount) <= 0) {
				return false;
			}
			break;
		case GREATER_THAN_OR_EQUAL_TO:
			if (Integer.compare(check.getExpectedCount(), valueOfCount) < 0) {
				return false;
			}
			break;
		case LESS_THAN:
			if (Integer.compare(check.getExpectedCount(), valueOfCount) >= 0) {
				return false;
			}
			break;
		case LESS_THAN_OR_EQUAL_TO:
			if (Integer.compare(check.getExpectedCount(), valueOfCount) > 0) {
				return false;
			}
			break;

		}

		return true;
	}

	/**
	 * @return the checkToValidate
	 */
	public CustomCheckElement getCheckToValidate() {
		return checkToValidate;
	}

	/**
	 * @param checkToValidate
	 *            the checkToValidate to set
	 */
	public void setCheckToValidate(CustomCheckElement checkToValidate) {
		this.checkToValidate = checkToValidate;
	}

}
