package com.trinary.rmmv.ui.components.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.trinary.rpgmaker.ro.PluginRO;

public class DownloadAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	protected PluginRO plugin;
	
	public DownloadAction(PluginRO plugin) {
		super("Download");
		this.plugin = plugin;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Downloading " + plugin.getName());
	}
}