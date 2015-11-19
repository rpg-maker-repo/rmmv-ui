package com.trinary.rmmv.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.trinary.rmmv.ui.Application;
import com.trinary.rmmv.util.PluginIOHelper;
import com.trinary.rmmv.util.types.OutOfDatePluginRO;
import com.trinary.rmmv.util.types.ProjectRO;

public class UpdateAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	protected OutOfDatePluginRO plugin;
	protected PluginIOHelper io = new PluginIOHelper(Application.getRMMVClientConfig());
	
	public UpdateAction(OutOfDatePluginRO plugin) {
		super("Update");
		this.plugin = plugin;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ProjectRO currentProject = Application.getState().getLocalSelectedProject();
		if (currentProject == null) {
			Application.showDialog("You must select a local project or one of it's children.");
			return;
		}
		
		// Delete the old plugin version
		io.deletePlugin(plugin, currentProject.getPath() + "/js/plugins/");
		
		// Store the new plugin version
		io.storePlugin(plugin.getLatestVersion(), currentProject.getPath() + "/js/plugins/");
		io.storeDependencies(plugin.getLatestVersion(), currentProject.getPath() + "/js/plugins/");
		Application.showDialog(String.format("Updated %s (%s) to version %s", 
				plugin.getName(), 
				plugin.getVersion(), 
				plugin.getLatestVersion().getVersion()));
		
		// Refresh the local tree
		Application.getMainFrame().getPluginManagerPanel().getLocalTree().refreshTree();
	}
}