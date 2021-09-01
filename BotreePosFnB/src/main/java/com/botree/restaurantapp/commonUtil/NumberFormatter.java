package com.botree.restaurantapp.commonUtil;

import java.text.NumberFormat;

public class NumberFormatter {
	private final static NumberFormat numFormatDec2Dig = NumberFormat.getInstance();
	private final static NumberFormat numFormatInt2Dig = NumberFormat.getIntegerInstance();

	static {
		numFormatDec2Dig.setMinimumFractionDigits(2);
		numFormatDec2Dig.setMaximumFractionDigits(2);

		numFormatInt2Dig.setMinimumIntegerDigits(2);
		numFormatInt2Dig.setMaximumIntegerDigits(2);
		numFormatInt2Dig.setParseIntegerOnly(true);
	}

	public static String getFormattedDecimal(double value) {
		return numFormatDec2Dig.format(value);
	}

	public static String getFormattedInteger(int i) {
		return numFormatInt2Dig.format(i);
	}
}
