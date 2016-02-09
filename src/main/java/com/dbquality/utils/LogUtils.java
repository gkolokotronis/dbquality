package com.dbquality.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

// TODO implement this class to write in one file all the errors found
import com.dbquality.consts.AppConsts;

public final class LogUtils {

	/**
	 * To avoid instantiation
	 */
	private LogUtils() {

	}

	public static void logError(List<String> errors) throws IOException {

		Files.write(Paths.get(AppConsts.LOG_LOCATION), errors, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
				StandardOpenOption.APPEND);

	}
}
