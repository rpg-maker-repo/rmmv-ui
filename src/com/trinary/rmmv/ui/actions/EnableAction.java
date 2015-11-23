package com.trinary.rmmv.ui.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import com.trinary.rmmv.ui.Application;
import com.trinary.rmmv.util.PluginIOHelper;
import com.trinary.rmmv.util.types.ProjectRO;
import com.trinary.rpgmaker.ro.PluginRO;

public class EnableAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	protected PluginRO plugin;
	protected ProjectRO project;
	protected PluginIOHelper io = new PluginIOHelper(Application.getRMMVClientConfig());
	
	public EnableAction(ProjectRO project, PluginRO plugin) {
		super("Enable");
		this.plugin = plugin;
		this.project = project;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			io.enablePlugin(project, plugin);
		} catch (IOException e1) {
			Application.showDialog("Enable plugin failed.");
		}
		
		// Refresh the local tree
		Application.getMainFrame().getPluginManagerPanel().getLocalTree().refreshTree();
	}
}