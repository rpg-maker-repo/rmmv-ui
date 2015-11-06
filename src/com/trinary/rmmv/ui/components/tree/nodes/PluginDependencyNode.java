package com.trinary.rmmv.ui.components.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trinary.rpgmaker.ro.PluginRO;

public class PluginDependencyNode extends DefaultMutableTreeNode implements PluginNode {
	private static final long serialVersionUID = 1L;
	private PluginRO plugin;
	private Boolean isDownloadable = false;

	public PluginDependencyNode(PluginRO plugin,  boolean allowsChildren) {
		super(String.format("%s (%s)", plugin.getName(), plugin.getVersion()), allowsChildren);
		this.plugin = plugin;
	}

	public PluginDependencyNode(PluginRO plugin) {
		super(String.format("%s (%s)", plugin.getName(), plugin.getVersion()));
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

	/**
	 * @return the isDownloadable
	 */
	public Boolean getIsDownloadable() {
		return isDownloadable;
	}

	/**
	 * @param isDownloadable the isDownloadable to set
	 */
	public void setIsDownloadable(Boolean isDownloadable) {
		this.isDownloadable = isDownloadable;
	}
}