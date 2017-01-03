package com.designers.kuwo.sqlite;

import java.io.IOException;
import java.util.Properties;

public class DBConfig {

	private static final Properties PROPERTIES = new Properties();

	static {
		try {
			PROPERTIES.load(DBConfig.class
					.getResourceAsStream("database_config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String DBNAME = PROPERTIES.getProperty("DBNAME");
	public static final int VERSION = Integer.parseInt(PROPERTIES.getProperty(
			"VERSION").toString());

}
