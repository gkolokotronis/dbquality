package com.dbquality.custom.checks.elements;

import org.apache.commons.lang3.StringUtils;

import com.dbquality.custom.checks.enums.ExpectedCountCheckEnum;

/**
 * Represents <code>custom/check</code> element of the xml containg the checks,
 * which describes all custom checks that are going to be runned
 * 
 * @author George Kolokotronis
 *
 */
public class CustomCheckElement {
	private String checkName;
	private String tableName;
	private String databaseName;
	private String whereClause;
	private ExpectedCountCheckEnum expectedCountCheck;
	private Integer expectedCount;

	/**
	 * @return the checkName
	 */
	public String getCheckName() {
		return checkName;
	}

	/**
	 * @param checkName
	 *            the checkName to set
	 */
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName
	 *            the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * @return the whereClause
	 */
	public String getWhereClause() {
		return whereClause;
	}

	/**
	 * @param whereClause
	 *            the whereClause to set
	 */
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	/**
	 * @return the expectedCount
	 */
	public Integer getExpectedCount() {
		return expectedCount;
	}

	/**
	 * @param expectedCount
	 *            the expectedCount to set
	 */
	public void setExpectedCount(Integer expectedCount) {
		this.expectedCount = expectedCount;
	}

	/**
	 * @return the expectedCountCheck
	 */
	public ExpectedCountCheckEnum getExpectedCountCheck() {
		return expectedCountCheck;
	}

	/**
	 * @param type
	 *            the expectedCountCheck type to set
	 */
	public void setExpectedCountCheck(String type) {
		if (StringUtils.equals(type, ExpectedCountCheckEnum.EQUAL_TO.toString())) {
			this.expectedCountCheck = ExpectedCountCheckEnum.EQUAL_TO;
		} else if (StringUtils.equals(type, ExpectedCountCheckEnum.GREATER_THAN.toString())) {
			this.expectedCountCheck = ExpectedCountCheckEnum.GREATER_THAN;
		} else if (StringUtils.equals(type, ExpectedCountCheckEnum.GREATER_THAN_OR_EQUAL_TO.toString())) {
			this.expectedCountCheck = ExpectedCountCheckEnum.GREATER_THAN_OR_EQUAL_TO;
		} else if (StringUtils.equals(type, ExpectedCountCheckEnum.LESS_THAN.toString())) {
			this.expectedCountCheck = ExpectedCountCheckEnum.LESS_THAN;
		} else if (StringUtils.equals(type, ExpectedCountCheckEnum.LESS_THAN_OR_EQUAL_TO.toString())) {
			this.expectedCountCheck = ExpectedCountCheckEnum.LESS_THAN_OR_EQUAL_TO;
		} else {
			throw new RuntimeException("Invalid column type " + type);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkName == null) ? 0 : checkName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomCheckElement other = (CustomCheckElement) obj;
		if (checkName == null) {
			if (other.checkName != null)
				return false;
		} else if (!checkName.equals(other.checkName))
			return false;
		return true;
	}

}
