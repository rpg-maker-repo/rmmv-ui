package com.trinary.rmmv.ui.components.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trinary.rmmv.ui.ro.Project;
import com.trinary.rpgmaker.ro.PluginRO;

public class OutOfDatePluginLocalNode extends DefaultMutableTreeNode implements LocalNode, PluginNode {
	private static final long serialVersionUID = 1L;
	protected PluginRO plugin;
	protected Project project;
	
	public OutOfDatePluginLocalNode(PluginRO plugin, Project project,  boolean allowsChildren) {
		super(String.format("%s (%s)", plugin.getName(), plugin.getVersion()), allowsChildren);
		this.plugin = plugin;
		this.project = project;
	}

	public OutOfDatePluginLocalNode(PluginRO plugin, Project project) {
		super(String.format("%s (%s)", plugin.getName(), plugin.getVersion()));
		this.plugin = plugin;
		this.project = project;
	}
	
	@Override
	public PluginRO getPlugin() {
		return plugin;
	}

	@Override
	public Project getProject() {
		return project;
	}
}