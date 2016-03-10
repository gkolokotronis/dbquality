package com.dbquality.custom.checks.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbquality.checks.CheckTypeEnum;
import com.dbquality.consts.AppConsts;
import com.dbquality.custom.checks.elements.CustomCheckElement;
import com.dbquality.logs.DQLogger;
import com.dbquality.properties.ApplicationMessagesHolder;
import com.dbquality.properties.MessageCodes;

/**
 * Validator class holding validations related to custom checks
 * 
 * @author George Kolokotronis
 *
 */
public class CustomXMLValidator {

	private static final DQLogger logger = DQLogger.create(CustomXMLValidator.class);

	private List<CustomCheckElement> checks = new ArrayList<CustomCheckElement>();

	private Map<String, CustomCheckElement> checkElements;

	public CustomXMLValidator(List<CustomCheckElement> checks) {
		this.checks = checks;
		this.checkElements = new HashMap<String, CustomCheckElement>();
	}

	/**
	 * runs validations on each custom check of the xml
	 * 
	 * @return true if all the elements were validated. false if something went
	 *         wrong with an element
	 */
	public boolean validate() {
		for (CustomCheckElement check : checks) {
			if (!validateDuplicateCheckName(check)) {
				return false;
			}
		}
		return true;

	}

	/**
	 * 
	 * @param check
	 * @return
	 */
	private boolean validateDuplicateCheckName(CustomCheckElement check) {
		String name = check.getCheckName();

		CustomCheckElement returnCheck = getCheckElements().put(name, check);

		if (returnCheck != null) {
			logger.error(ApplicationMessagesHolder.getInstance().getMessage(MessageCodes.ERR_DUPLICATE_CHECKS,
					CheckTypeEnum.CUSTOM, AppConsts.NAME, name));
			return false;
		}
		return true;
	}

	/**
	 * @return the checks
	 */
	public List<CustomCheckElement> getChecks() {
		return checks;
	}

	/**
	 * @param checks
	 *            the checks to set
	 */
	public void setChecks(List<CustomCheckElement> checks) {
		this.checks = checks;
	}

	/**
	 * @return the checkElements
	 */
	public Map<String, CustomCheckElement> getCheckElements() {
		return checkElements;
	}

	/**
	 * @param checkElements
	 *            the checkElements to set
	 */
	public void setCheckElements(Map<String, CustomCheckElement> checkElements) {
		this.checkElements = checkElements;
	}

}
