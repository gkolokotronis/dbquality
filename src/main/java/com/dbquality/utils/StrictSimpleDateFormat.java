package com.dbquality.utils;

import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Extension of SimpleDateFormat that implements strict matching. parse(text)
 * will only return a Date if text exactly matches the pattern.
 *
 */
public class StrictSimpleDateFormat extends SimpleDateFormat {

	private static final long serialVersionUID = 1L;

	protected boolean strict = true;

	public StrictSimpleDateFormat() {
		super();
		setStrict(true);
	}

	public StrictSimpleDateFormat(String pattern) {
		super(pattern);
		setStrict(true);
	}

	public StrictSimpleDateFormat(String pattern, DateFormatSymbols formatSymbols) {
		super(pattern, formatSymbols);
		setStrict(true);
	}

	public StrictSimpleDateFormat(String pattern, Locale locale) {
		super(pattern, locale);
		setStrict(true);
	}

	/**
	 * Set the strict setting. If strict == true (the default) then parsing
	 * requires an exact match to the pattern. Setting strict = false will
	 * tolerate text after the pattern match.
	 * 
	 * @param strict
	 */
	public void setStrict(boolean strict) {
		this.strict = strict;
		// strict with lenient does not make sense. Really lenient does
		// not make sense in any case.
		if (strict)
			setLenient(false);
	}

	public boolean getStrict() {
		return strict;
	}

	/**
	 * Parse text to a Date. Exact match of the pattern is required. Parse and
	 * format are now inverse functions, so this is required to be true for
	 * valid text date information: text.equals(format(parse(text))
	 * 
	 * @param text
	 * @param pos
	 * @return
	 */
	@Override
	public Date parse(String text, ParsePosition pos) {
		int posIndex = pos.getIndex();
		Date d = super.parse(text, pos);
		if (strict && d != null) {
			String format = this.format(d);
			if (posIndex + format.length() != text.length() || !text.endsWith(format)) {
				d = null; // Not exact match
			}
		}
		return d;
	}
}