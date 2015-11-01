package com.trinary.rmmv.ui.components;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.trinary.rmmv.ui.components.panel.PluginManagerPanel;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel pluginManagerPanel;

	public MainFrame() {
		Icon closed = (Icon) UIManager.get("Tree.leafIcon");
	    Icon opened = (Icon) UIManager.get("Tree.leafIcon");
		UIManager.put("Tree.closedIcon", closed);
		UIManager.put("Tree.openIcon",  opened);
		UIManager.put("Tree.leafIcon", opened);
		
		this.setTitle("RPG Maker Project Manager");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 597, 513);
		
		pluginManagerPanel = new PluginManagerPanel();

		this.setContentPane(pluginManagerPanel);
		this.setVisible(true);
	}
}