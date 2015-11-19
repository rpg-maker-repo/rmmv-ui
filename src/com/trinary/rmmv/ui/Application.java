package com.trinary.rmmv.ui;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import com.trinary.rmmv.client.RMMVClientConfig;
import com.trinary.rmmv.ui.components.MainFrame;

public class Application {
	protected static MainFrame mainFrame;
	protected static ApplicationState state = new ApplicationState();
	protected static RMMVClientConfig config = new RMMVClientConfig("client.properties");
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame = new MainFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static MainFrame getMainFrame() {
		return mainFrame;
	}
	
	public static void showDialog(String message) {
		JOptionPane.showMessageDialog(mainFrame, message);
	}
	
	public static ApplicationState getState() {
		return state;
	}

	public static RMMVClientConfig getRMMVClientConfig() {
		return config;
	}
}
