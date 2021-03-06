package com.trinary.rmmv.ui.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import com.trinary.rmmv.ui.Application;
import com.trinary.rmmv.util.PluginIOHelper;
import com.trinary.rmmv.util.types.OutOfDatePluginRO;
import com.trinary.rmmv.util.types.ProjectRO;

public class UpdateAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	protected OutOfDatePluginRO plugin;
	protected ProjectRO project;
	protected PluginIOHelper io = new PluginIOHelper(Application.getRMMVClientConfig());
	
	public UpdateAction(ProjectRO project, OutOfDatePluginRO plugin) {
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
		io.deletePlugin(project, plugin);
		
		// Store the new plugin version
		try {
			io.storePlugin(project, plugin.getLatestVersion(), true);
			io.storeDependencies(project, plugin.getLatestVersion(), true);
		} catch (IOException e1) {
			e1.printStackTrace();
			Application.showDialog("Update failed");
			return;
		}
		
		Application.showDialog(String.format("Updated %s (%s) to version %s", 
				plugin.getName(), 
				plugin.getVersion(), 
				plugin.getLatestVersion().getVersion()));
		
		// Refresh the local tree
		Application.getMainFrame().getPluginManagerPanel().getLocalTree().refreshTree();
	}
}