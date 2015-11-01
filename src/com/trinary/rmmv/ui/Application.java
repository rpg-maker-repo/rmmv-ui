package com.trinary.rmmv.ui;

import java.awt.EventQueue;

public class Application {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new RMMVFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
