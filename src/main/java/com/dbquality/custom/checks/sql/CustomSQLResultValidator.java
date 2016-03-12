package com.dbquality.custom.checks.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dbquality.checks.CheckTypeEnum;
import com.dbquality.custom.checks.elements.CustomCheckElement;
import com.dbquality.logs.DQLogger;
import com.dbquality.properties.ApplicationMessagesHolder;
import com.dbquality.properties.MessageCodes;

public class CustomSQLResultValidator {

	private static final DQLogger logger = DQLogger.create(CustomSQLResultValidator.class);

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
					logger.validationError(ApplicationMessagesHolder.getInstance().getMessage(
							MessageCodes.VAL_ERR_WRONG_EXPECTED_COUNT, CheckTypeEnum.CUSTOM, checkToValidate.getCheckName(),
							valueOfCount, getCheckToValidate().getExpectedCountCheck(),
							getCheckToValidate().getExpectedCount()));

				}
			}

		} else {
			logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_WRONG_RESULT_CHECK,
					CheckTypeEnum.CUSTOM, checkToValidate.getCheckName()));

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
