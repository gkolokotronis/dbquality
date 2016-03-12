package com.dbquality.distinct.checks.elements;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import com.dbquality.checks.ColumnTypeEnum;
import com.dbquality.exceptions.ExceptionFactory;
import com.dbquality.logs.DQLogger;
import com.dbquality.properties.MessageCodes;

/**
 * Represents <code>distinct/column</code> element of the xml containg the
 * checks, which describes all checks that are going to be runned for distinct
 * values
 * 
 * @author George Kolokotronis
 *
 */
public class ColumnDistinctElement {

	private static final DQLogger logger = DQLogger.create(ColumnDistinctElement.class);

	private Integer id;
	private String name;
	private String tableName;
	private String databaseName;
	private ColumnTypeEnum type;
	private List<String> values = new ArrayList<String>();
	private String whereClause;
	private Boolean nullable;
	private String dateFormat;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the values
	 */
	public List<String> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}

	public void addValue(String value) {
		values.add(value);
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	/**
	 * @return the nullable
	 */
	public Boolean isNullable() {
		return nullable;
	}

	/**
	 * @param nullable
	 *            the nullable to set
	 */
	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * @return the type
	 */
	public ColumnTypeEnum getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		if (StringUtils.equals(type, ColumnTypeEnum.INTEGER.toString())) {
			this.type = ColumnTypeEnum.INTEGER;
		} else if (StringUtils.equals(type, ColumnTypeEnum.DECIMAL.toString())) {
			this.type = ColumnTypeEnum.DECIMAL;
		} else if (StringUtils.equals(type, ColumnTypeEnum.VARCHAR.toString())) {
			this.type = ColumnTypeEnum.VARCHAR;
		} else if (StringUtils.equals(type, ColumnTypeEnum.DATE.toString())) {
			this.type = ColumnTypeEnum.DATE;
		} else {
			throw ExceptionFactory.createException(RuntimeException.class, MessageCodes.ERR_INVALID_COLUMN_TYPE, null,
					logger, Level.ERROR, type);
		}
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
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ColumnDistinctElement other = (ColumnDistinctElement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
