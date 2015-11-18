package com.trinary.rmmv.ui.components.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trinary.rmmv.ui.ro.Project;
import com.trinary.rmmv.ui.ro.UnknownPluginRO;
import com.trinary.rpgmaker.ro.PluginRO;

public class UnknownPluginVersionLocalNode extends DefaultMutableTreeNode implements LocalNode {
	private static final long serialVersionUID = 1L;
	private PluginRO plugin;
	private Project project;

	public UnknownPluginVersionLocalNode(UnknownPluginRO plugin, Project project,  boolean allowsChildren) {
		super(String.format("%s", plugin.getFilename()), allowsChildren);
		this.plugin = plugin;
		this.project = project;
	}

	public UnknownPluginVersionLocalNode(UnknownPluginRO plugin, Project project) {
		super(String.format("%s", plugin.getFilename()));
		this.project = project;
		this.plugin = plugin;
	}

	/**
	 * @return the plugin
	 */
	public PluginRO getPlugin() {
		return plugin;
	}

	/**
	 * @param plugin the plugin to set
	 */
	public void setPlugin(PluginRO plugin) {
		this.plugin = plugin;
	}

	@Override
	public Project getProject() {
		return project;
	}
}