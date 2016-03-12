package com.dbquality.distinct.checks.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

import com.dbquality.checks.CheckTypeEnum;
import com.dbquality.distinct.checks.elements.ColumnDistinctElement;
import com.dbquality.distinct.checks.elements.DistinctRootElement;
import com.dbquality.logs.DQLogger;
import com.dbquality.properties.ApplicationMessagesHolder;
import com.dbquality.properties.MessageCodes;
import com.dbquality.sql.SQLExecutionHandler;

/**
 * This class is implementation of predefined methods of
 * {@link SQLExecutionHandler} abstract class. It holds the populate and
 * execution logic of the sql distinct checks
 * 
 * @author George Kolokotronis
 *
 */
public class DistinctSQLExecutionHandler extends SQLExecutionHandler {

	private static final DQLogger logger = DQLogger.create(DistinctSQLExecutionHandler.class);

	private DistinctRootElement rootElement;

	private Map<Integer, ColumnDistinctElement> keyToColumnElements;

	private Map<Integer, String> keyToSQL;

	private ArrayList<Integer> keyList;

	public DistinctSQLExecutionHandler(DistinctRootElement rootElement) {
		this.rootElement = rootElement;

	}

	@Override
	protected void sqlsPopulation() {
		logger.info(ApplicationMessagesHolder.getInstance()
				.getMessage(MessageCodes.MSG_DISTINCT_CHECKS_START_POPULATION_SQL));

		ArrayList<Integer> keyList = new ArrayList<Integer>();
		Map<Integer, ColumnDistinctElement> columnElements = new HashMap<Integer, ColumnDistinctElement>();
		Map<Integer, String> columnsSQL = new HashMap<Integer, String>();

		for (ColumnDistinctElement column : getRootElement().getColumns()) {

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT DISTINCT ");
			sb.append(column.getName());
			sb.append(" FROM " + column.getDatabaseName() + "." + column.getTableName() + ";");
			if (StringUtils.isNotEmpty(column.getWhereClause())) {
				sb.append(column.getWhereClause());
			}

			String sql = sb.toString();
			Integer key = column.getId();

			columnElements.put(key, column);
			columnsSQL.put(key, sql);
			keyList.add(key);
		}

		setKeyToColumnElements(columnElements);
		setKeyToSQL(columnsSQL);
		setKeyList(keyList);

	}

	@Override
	protected void sqlExecutionAndValidation() {

		logger.info(ApplicationMessagesHolder.getInstance()
				.getMessage(MessageCodes.MSG_DISTINCT_CHECKS_START_EXECUTION_VALIDATION_SQL));

		Connection conn = getConnection();

		for (Integer key : getKeyList()) {

			String sqlToExecute = getKeyToSQL().get(key);
			Statement runCheck = null;
			ResultSet rs = null;

			try {

				runCheck = conn.createStatement();
				rs = runCheck.executeQuery(sqlToExecute);
				validateResult(rs, key);

			} catch (SQLException e) {
				logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_WRONG_EXECUTING_SQL,
						sqlToExecute, e.getMessage()));
			} finally {
				DbUtils.closeQuietly(rs);
				DbUtils.closeQuietly(runCheck);
			}

		}

		DbUtils.closeQuietly(conn);

	}

	/**
	 * Validates the result if it is contained in the valid values of the column
	 * based on the xml
	 * 
	 * @param rs
	 *            - the result set to be validated
	 * @param key
	 *            - the key of the column
	 */
	private void validateResult(ResultSet rs, Integer key) {

		logger.debug("Validating results for key " + key);

		ColumnDistinctElement column = getKeyToColumnElements().get(key);

		DistinctSQLResultValidator resultValidator = new DistinctSQLResultValidator(rs, column);

		try {
			resultValidator.validate();
		} catch (SQLException e) {
			logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_WHILE_VALIDATING_RESULTS,
					CheckTypeEnum.DISTINCT, key, e.getMessage()));
		}

	}

	/**
	 * @return the keyList
	 */
	public ArrayList<Integer> getKeyList() {
		return keyList;
	}

	/**
	 * @param keyList
	 *            the keyList to set
	 */
	public void setKeyList(ArrayList<Integer> keyList) {
		this.keyList = keyList;
	}

	/**
	 * @return the rootElement
	 */
	public DistinctRootElement getRootElement() {
		return rootElement;
	}

	/**
	 * @param rootElement
	 *            the rootElement to set
	 */
	public void setRootElement(DistinctRootElement rootElement) {
		this.rootElement = rootElement;
	}

	/**
	 * @return the keyToColumnElements
	 */
	public Map<Integer, ColumnDistinctElement> getKeyToColumnElements() {
		return keyToColumnElements;
	}

	/**
	 * @param keyToColumnElements
	 *            the keyToColumnElements to set
	 */
	public void setKeyToColumnElements(Map<Integer, ColumnDistinctElement> keyToColumnElements) {
		this.keyToColumnElements = keyToColumnElements;
	}

	/**
	 * @return the keyToSQL
	 */
	public Map<Integer, String> getKeyToSQL() {
		return keyToSQL;
	}

	/**
	 * @param keyToSQL
	 *            the keyToSQL to set
	 */
	public void setKeyToSQL(Map<Integer, String> keyToSQL) {
		this.keyToSQL = keyToSQL;
	}

}
