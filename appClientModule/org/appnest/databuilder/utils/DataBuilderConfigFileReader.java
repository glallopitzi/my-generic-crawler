package org.appnest.databuilder.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class DataBuilderConfigFileReader {

	public static String readStringFromFile(String filePath) throws IOException {
		File file = new File(filePath);
		String content = FileUtils.readFileToString(file);
		return content;
	}

	/**
	 * This method read a file in a list of String, skip comment (lines beginning with #)
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static List<String> readListFromFile(String filePath)
			throws IOException {
		List<String> resultAux = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		File file = new File(filePath);
		resultAux = FileUtils.readLines(file);
		for (String string : resultAux) {
			if (StringUtils.startsWith(string, "#"))
				continue;
			result.add(string);
		}
		return result;
	}

}
