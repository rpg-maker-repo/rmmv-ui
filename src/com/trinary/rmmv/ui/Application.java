package com.trinary.rmmv.ui;

import java.awt.EventQueue;

import com.trinary.rmmv.ui.components.MainFrame;

public class Application {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
