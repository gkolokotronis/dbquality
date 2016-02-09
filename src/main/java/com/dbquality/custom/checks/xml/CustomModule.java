package com.dbquality.custom.checks.xml;

import org.apache.commons.digester3.binder.AbstractRulesModule;

import com.dbquality.custom.checks.elements.CustomCheckElement;
import com.dbquality.custom.checks.elements.CustomRootElement;

/**
 * Class that contains all the patterns of the custom xml and the connecting
 * Classes
 * 
 * @author George Kolokotronis
 * 
 *
 */
public class CustomModule extends AbstractRulesModule {

	@Override
	protected void configure() {

		forPattern("custom").createObject().ofType(CustomRootElement.class).then().setProperties();

		forPattern("custom/checks/check").createObject().ofType(CustomCheckElement.class).then().setProperties().then()
				.setNext("addCheck");

		forPattern("custom/checks/check/checkName").callMethod("setCheckName").withParamCount(1)
				.withParamTypes(String.class).then().callParam();

		forPattern("custom/checks/check/tableName").callMethod("setTableName").withParamCount(1)
				.withParamTypes(String.class).then().callParam();

		forPattern("custom/checks/check/databaseName").callMethod("setDatabaseName").withParamCount(1)
				.withParamTypes(String.class).then().callParam();

		forPattern("custom/checks/check/whereClause").callMethod("setWhereClause").withParamCount(1)
				.withParamTypes(String.class).then().callParam();

		forPattern("custom/checks/check/expectedCountCheck").callMethod("setExpectedCountCheck").withParamCount(1)
				.withParamTypes(String.class).then().callParam();

		forPattern("custom/checks/check/expectedCount").callMethod("setExpectedCount").withParamCount(1)
				.withParamTypes(Integer.class).then().callParam();

	}
}
