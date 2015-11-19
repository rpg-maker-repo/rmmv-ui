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
	protected static Boolean configUpdated = false;
	
	// Constants
	public static final String DEFAULT_PROJECTS_ROOT = "~/Documents/Games";
	public static final String WORKSPACE_PROPERTIES_FILE = "workspace.properties";
	
	// Config key values
	public static final String PROJECTS_ROOT_KEY = "rmmv.projects.root";
	
	static {
		Properties properties = new Properties();
		try {
			InputStream in = new FileInputStream(WORKSPACE_PROPERTIES_FILE);
			properties.load(in);
			
			// Load config elements into this file.
			projectsRoot = properties.getProperty(PROJECTS_ROOT_KEY);
		} catch (FileNotFoundException e) {
			propertiesFileMissing = true;
			e.printStackTrace();
		} catch (IOException e) {
			propertiesFileMissing = true;
			e.printStackTrace();
		}
		
		if (projectsRoot == null) {
			projectsRoot = DEFAULT_PROJECTS_ROOT;
			properties.put(PROJECTS_ROOT_KEY, DEFAULT_PROJECTS_ROOT);
			configUpdated = true;
		}
		
		if (configUpdated) {
			storeConfig(WORKSPACE_PROPERTIES_FILE, properties);
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