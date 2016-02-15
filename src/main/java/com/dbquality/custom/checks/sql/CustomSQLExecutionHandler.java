package com.dbquality.custom.checks.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbquality.custom.checks.elements.CustomCheckElement;
import com.dbquality.custom.checks.elements.CustomRootElement;
import com.dbquality.sql.SQLExecutionHandler;

/**
 * This class is implementation of predefined methods of
 * {@link SQLExecutionHandler} abstract class. It holds the populate and
 * execution logic of the sql distinct checks
 * 
 * @author George Kolokotronis
 *
 */
public class CustomSQLExecutionHandler extends SQLExecutionHandler {

	private static final Logger logger = LogManager.getLogger(CustomSQLExecutionHandler.class);

	private CustomRootElement rootElement;

	private Map<String, CustomCheckElement> keyToCheckElements;

	private Map<String, String> keyToSQL;

	private ArrayList<String> checkNameList;

	public CustomSQLExecutionHandler(CustomRootElement rootElement) {
		this.rootElement = rootElement;

	}

	@Override
	protected void sqlsPopulation() {
		logger.info("Started populating sqls for custom checks");
		System.out.println("Started populating sqls for custom checks");

		ArrayList<String> checkNameList = new ArrayList<String>();
		Map<String, CustomCheckElement> checkElements = new HashMap<String, CustomCheckElement>();
		Map<String, String> checksSQL = new HashMap<String, String>();

		for (CustomCheckElement check : getRootElement().getChecks()) {

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT COUNT(*) ");
			sb.append(" FROM " + check.getDatabaseName() + "." + check.getTableName() + ";");
			if (StringUtils.isNotEmpty(check.getWhereClause())) {
				sb.append(check.getWhereClause());
			}

			String sql = sb.toString();
			String key = check.getCheckName();

			checkElements.put(key, check);
			checksSQL.put(key, sql);
			checkNameList.add(key);
		}

		setKeyToCheckElements(checkElements);
		setKeyToSQL(checksSQL);
		setCheckNameList(checkNameList);

	}

	@Override
	protected void sqlExecutionAndValidation() {

		logger.info("Started executing the sqls and validating the results for custom checks");
		System.out.println("Started executing the sqls and validating the results for custom checks");

		Connection conn = getConnection();

		for (String key : getCheckNameList()) {

			String sqlToExecute = getKeyToSQL().get(key);
			Statement runCheck = null;
			ResultSet rs = null;

			try {

				runCheck = conn.createStatement();
				rs = runCheck.executeQuery(sqlToExecute);
				validateResult(rs, key);

			} catch (SQLException e) {
				logger.error("Something went wrong while executing " + sqlToExecute + e.getMessage());
			} finally {
				DbUtils.closeQuietly(rs);
				DbUtils.closeQuietly(runCheck);
			}

		}

		DbUtils.closeQuietly(conn);

	}

	/**
	 * Validates the result if it is has the correct results
	 * 
	 * @param rs
	 *            - the result set to be validated
	 * @param checkName
	 *            - the key of the check
	 */
	private void validateResult(ResultSet rs, String checkName) {

		logger.debug("Validating results for check " + checkName);

		CustomCheckElement check = getKeyToCheckElements().get(checkName);

		CustomSQLResultValidator resultValidator = new CustomSQLResultValidator(rs, check);

		try {
			resultValidator.validate();
		} catch (SQLException e) {
			logger.error("A database error has occurred while validating results for " + checkName + e.getMessage());
		}

	}

	/**
	 * @return the keyList
	 */
	public ArrayList<String> getCheckNameList() {
		return checkNameList;
	}

	/**
	 * @param keyList
	 *            the keyList to set
	 */
	public void setCheckNameList(ArrayList<String> keyList) {
		this.checkNameList = keyList;
	}

	/**
	 * @return the rootElement
	 */
	public CustomRootElement getRootElement() {
		return rootElement;
	}

	/**
	 * @param rootElement
	 *            the rootElement to set
	 */
	public void setRootElement(CustomRootElement rootElement) {
		this.rootElement = rootElement;
	}

	/**
	 * @return the keyToColumnElements
	 */
	public Map<String, CustomCheckElement> getKeyToCheckElements() {
		return keyToCheckElements;
	}

	/**
	 * @param keyToCheckElements
	 *            the keyToColumnElements to set
	 */
	public void setKeyToCheckElements(Map<String, CustomCheckElement> keyToCheckElements) {
		this.keyToCheckElements = keyToCheckElements;
	}

	/**
	 * @return the keyToSQL
	 */
	public Map<String, String> getKeyToSQL() {
		return keyToSQL;
	}

	/**
	 * @param keyToSQL
	 *            the keyToSQL to set
	 */
	public void setKeyToSQL(Map<String, String> keyToSQL) {
		this.keyToSQL = keyToSQL;
	}

}
