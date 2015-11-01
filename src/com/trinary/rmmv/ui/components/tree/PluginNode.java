package com.trinary.rmmv.ui.components.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trinary.rpgmaker.ro.PluginRO;

public class PluginNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 1L;
	private PluginRO plugin;
	private Boolean isDownloadable = false;

	public PluginNode(PluginRO plugin,  boolean allowsChildren) {
		super(plugin.getName(), allowsChildren);
		this.plugin = plugin;
	}

	public PluginNode(PluginRO plugin) {
		super(plugin.getName());
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