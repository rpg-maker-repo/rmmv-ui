package com.trinary.rmmv.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ProjectManagerConfig {
	public static String projectsRoot = null;
	public static Boolean propertiesFileMissing = false;
	
	public static final String DEFAULT_PROJECTS_ROOT = "~/Documents/Games";
	
	protected static Boolean configUpdated = false;
	
	static {
		Properties properties = new Properties();
		try {
			InputStream in = new FileInputStream("config.properties");
			properties.load(in);
			
			// Load config elements into this file.
			projectsRoot = properties.getProperty("rmmv.projects.root");
		} catch (FileNotFoundException e) {
			propertiesFileMissing = true;
			e.printStackTrace();
		} catch (IOException e) {
			propertiesFileMissing = true;
			e.printStackTrace();
		}
		
		if (projectsRoot == null) {
			projectsRoot = DEFAULT_PROJECTS_ROOT;
			properties.put("rmmv.projects.root", DEFAULT_PROJECTS_ROOT);
			configUpdated = true;
		}
		
		if (configUpdated) {
			storeConfig("config.properties", properties);
		}
	}
	
	protected static void storeConfig(String filename, Properties properties) {
		try {
			OutputStream out = new FileOutputStream(filename);
			properties.store(out, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}