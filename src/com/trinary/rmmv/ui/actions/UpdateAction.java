package com.trinary.rmmv.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.trinary.rmmv.client.PluginIOHelper;
import com.trinary.rmmv.client.PluginVersionClient;
import com.trinary.rmmv.ui.Application;
import com.trinary.rmmv.ui.ro.OutOfDatePluginRO;
import com.trinary.rmmv.ui.ro.Project;

public class UpdateAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	protected OutOfDatePluginRO plugin;
	protected PluginVersionClient client = new PluginVersionClient();
	
	public UpdateAction(OutOfDatePluginRO plugin) {
		super("Update");
		this.plugin = plugin;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Project currentProject = Application.getState().getLocalSelectedProject();
		if (currentProject == null) {
			Application.showDialog("You must select a local project or one of it's children.");
			return;
		}
		
		// Delete the old plugin version
		PluginIOHelper.deletePlugin(plugin, currentProject.getPath() + "/js/plugins/");
		
		// Store the new plugin version
		PluginIOHelper.storePlugin(plugin.getLatestVersion(), currentProject.getPath() + "/js/plugins/");
		PluginIOHelper.storeDependencies(plugin.getLatestVersion(), currentProject.getPath() + "/js/plugins/");
		Application.showDialog(String.format("Updated %s (%s) to version %s", 
				plugin.getName(), 
				plugin.getVersion(), 
				plugin.getLatestVersion().getVersion()));
		
		// Refresh the local tree
		Application.getMainFrame().getPluginManagerPanel().getLocalTree().refreshTree();
	}
}