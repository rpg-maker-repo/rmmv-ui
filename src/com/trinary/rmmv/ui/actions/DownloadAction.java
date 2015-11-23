package com.trinary.rmmv.ui.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import com.trinary.rmmv.ui.Application;
import com.trinary.rmmv.util.PluginIOHelper;
import com.trinary.rmmv.util.types.ProjectRO;
import com.trinary.rpgmaker.ro.PluginRO;

public class DownloadAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	protected PluginRO plugin;
	protected PluginIOHelper io = new PluginIOHelper(Application.getRMMVClientConfig());
	
	public DownloadAction(PluginRO plugin) {
		super("Download");
		this.plugin = plugin;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ProjectRO currentProject = Application.getState().getLocalSelectedProject();
		if (currentProject == null) {
			Application.showDialog("You must select a local project or one of it's children.");
			return;
		}
		
		// Store plugin and dependencies.
		try {
			io.storePlugin(currentProject, plugin, true);
			io.storeDependencies(currentProject, plugin, true);
		} catch (IOException e1) {
			e1.printStackTrace();
			Application.showDialog("Download failed!");
			return;
		}
	
		Application.showDialog(String.format("Downloaded %s (%s) and all dependencies!",
				plugin.getName(), plugin.getVersion()));
		
		// Refresh the local tree
		Application.getMainFrame().getPluginManagerPanel().getLocalTree().refreshTree();
	}
}