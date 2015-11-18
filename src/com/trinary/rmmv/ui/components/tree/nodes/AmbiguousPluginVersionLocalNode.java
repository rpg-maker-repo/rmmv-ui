package com.trinary.rmmv.ui.components.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trinary.rmmv.ui.ro.AmbiguousPluginRO;
import com.trinary.rmmv.ui.ro.Project;
import com.trinary.rpgmaker.ro.PluginRO;

public class AmbiguousPluginVersionLocalNode extends DefaultMutableTreeNode implements PluginNode, LocalNode {
	private static final long serialVersionUID = 1L;
	private PluginRO plugin;
	private Project project;

	public AmbiguousPluginVersionLocalNode(AmbiguousPluginRO plugin, Project project,  boolean allowsChildren) {
		super(String.format("AMBIGUOUS PLUGIN (%d matches)", plugin.getPlugins().size()), allowsChildren);
		this.project = project;
		this.plugin = plugin;
	}

	public AmbiguousPluginVersionLocalNode(AmbiguousPluginRO plugin, Project project) {
		super(String.format("AMBIGUOUS PLUGIN (%d matches)", plugin.getPlugins().size()));
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