package com.trinary.rmmv.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.trinary.rmmv.client.PluginIOHelper;
import com.trinary.rmmv.client.PluginVersionClient;
import com.trinary.rmmv.ui.Application;
import com.trinary.rmmv.ui.ro.Project;
import com.trinary.rpgmaker.ro.PluginRO;

public class DownloadAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	protected PluginRO plugin;
	protected PluginVersionClient client = new PluginVersionClient();
	
	public DownloadAction(PluginRO plugin) {
		super("Download");
		this.plugin = plugin;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Project currentProject = Application.getState().getLocalSelectedProject();
		if (currentProject == null) {
			Application.showDialog("You must select a local project or one of it's children.");
			return;
		}
		
		PluginIOHelper.storePlugin(plugin, currentProject.getPath() + "/js/plugins/");
		PluginIOHelper.storeDependencies(plugin, currentProject.getPath() + "/js/plugins/");
		Application.showDialog(String.format("Downloaded %s (%s) and all dependencies!",
				plugin.getName(), plugin.getVersion()));
		
		// Refresh the local tree
		Application.getMainFrame().getPluginManagerPanel().getLocalTree().refreshTree();
	}
}