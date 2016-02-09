package com.dbquality.distinct.checks.xml;

import org.apache.commons.digester3.binder.AbstractRulesModule;

import com.dbquality.distinct.checks.elements.ColumnDistinctElement;
import com.dbquality.distinct.checks.elements.DistinctRootElement;

/**
 * 
 * @author George Kolokotronis
 * 
 *         Class which contains all the patterns of the xml and the connecting
 *         Classes
 *
 */
public class DistinctModule extends AbstractRulesModule {

	@Override
	protected void configure() {

		forPattern("distinct").createObject().ofType(DistinctRootElement.class).then().setProperties();

		forPattern("distinct/columns/column").createObject().ofType(ColumnDistinctElement.class).then().setProperties()
				.then().setNext("addColumn");

		forPattern("distinct/columns/column/id").callMethod("setId").withParamCount(1).withParamTypes(Integer.class)
				.then().callParam();

		forPattern("distinct/columns/column/name").callMethod("setName").withParamCount(1).withParamTypes(String.class)
				.then().callParam();

		forPattern("distinct/columns/column/tableName").callMethod("setTableName").withParamCount(1)
				.withParamTypes(String.class).then().callParam();

		forPattern("distinct/columns/column/databaseName").callMethod("setDatabaseName").withParamCount(1)
				.withParamTypes(String.class).then().callParam();

		forPattern("distinct/columns/column/type").callMethod("setType").withParamCount(1).withParamTypes(String.class)
				.then().callParam();

		forPattern("distinct/columns/column/dateFormat").callMethod("setDateFormat").withParamCount(1)
				.withParamTypes(String.class).then().callParam();

		forPattern("distinct/columns/column/values/value").callMethod("addValue").withParamCount(1)
				.withParamTypes(String.class).then().callParam();

		forPattern("distinct/columns/column/whereClause").callMethod("setWhereClause").withParamCount(1)
				.withParamTypes(String.class).then().callParam();

		forPattern("distinct/columns/column/nullable").callMethod("setNullable").withParamCount(1)
				.withParamTypes(Boolean.class).then().callParam();

	}
}
