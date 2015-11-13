package com.trinary.rmmv.ui.components.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trinary.rmmv.ui.ro.UnknownPluginRO;
import com.trinary.rpgmaker.ro.PluginRO;

public class UnknownPluginVersionLocalNode extends DefaultMutableTreeNode implements PluginNode {
	private static final long serialVersionUID = 1L;
	private PluginRO plugin;

	public UnknownPluginVersionLocalNode(UnknownPluginRO plugin,  boolean allowsChildren) {
		super(String.format("%s", plugin.getFilename()), allowsChildren);
		this.plugin = plugin;
	}

	public UnknownPluginVersionLocalNode(UnknownPluginRO plugin) {
		super(String.format("%s", plugin.getFilename()));
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
}