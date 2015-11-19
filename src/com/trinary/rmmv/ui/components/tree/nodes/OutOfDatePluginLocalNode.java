package com.trinary.rmmv.ui.components.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trinary.rmmv.util.types.ProjectRO;
import com.trinary.rpgmaker.ro.PluginRO;

public class OutOfDatePluginLocalNode extends DefaultMutableTreeNode implements LocalNode, PluginNode {
	private static final long serialVersionUID = 1L;
	protected PluginRO plugin;
	protected ProjectRO project;
	
	public OutOfDatePluginLocalNode(PluginRO plugin, ProjectRO project,  boolean allowsChildren) {
		super(String.format("%s (%s)", plugin.getName(), plugin.getVersion()), allowsChildren);
		this.plugin = plugin;
		this.project = project;
	}

	public OutOfDatePluginLocalNode(PluginRO plugin, ProjectRO project) {
		super(String.format("%s (%s)", plugin.getName(), plugin.getVersion()));
		this.plugin = plugin;
		this.project = project;
	}
	
	@Override
	public PluginRO getPlugin() {
		return plugin;
	}

	@Override
	public ProjectRO getProject() {
		return project;
	}
}