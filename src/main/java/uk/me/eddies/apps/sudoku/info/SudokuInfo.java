/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.info;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class SudokuInfo {

	private static final URL BUILD_PROPERTIES_RESOURCE =
			SudokuInfo.class.getResource("build.properties");
	private static final String VERSION_PROPERTY =
			"uk.me.eddies.apps.sudoku.info.version";

	private Properties buildProperties;

	public SudokuInfo() throws IOException {
		buildProperties = loadProperties(BUILD_PROPERTIES_RESOURCE);
	}

	public String getVersionNumber() {
		return buildProperties.getProperty(VERSION_PROPERTY);
	}

	private static Properties loadProperties(URL resource) throws IOException {
		try (InputStream resourceStream = resource.openStream()) {
			Properties properties = new Properties();
			properties.load(resourceStream);
			return properties;
		}
	}
}
