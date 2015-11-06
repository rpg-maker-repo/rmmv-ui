package com.trinary.rmmv.ui.components.menu;

import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.AbstractAction;

import com.trinary.rmmv.client.PluginVersionClient;
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
		storePlugin(plugin, "/Users/mmain/");
		storeDependencies(plugin, "/Users/mmain/");
	}
	
	protected void storePlugin(PluginRO plugin, String location) {
		System.out.println("Downloading " + plugin.getName() + " (" + plugin.getVersion() + ")");
		
		String script = null;
		try {
			script = client.getScript(plugin);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
	
		try {
			FileWriter writer = new FileWriter(location + plugin.getName() + ".js");
			writer.write(script);
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
	}
	
	protected void storeDependencies(PluginRO plugin, String location) {
		List<PluginRO> dependencies;
		try {
			dependencies = client.getDependencies(plugin);
		} catch (Exception e2) {
			e2.printStackTrace();
			return;
		}
		
		for (PluginRO dependency : dependencies) {
			storePlugin(dependency, location);
			storeDependencies(dependency, location);
		}
	}
}