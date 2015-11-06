package com.trinary.rmmv.ui.components.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trinary.rpgmaker.ro.PluginBaseRO;


public class PluginBaseNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 1L;

	private PluginBaseRO plugin;
	private Boolean isDownloadable = false;

	public PluginBaseNode(PluginBaseRO plugin, boolean allowsChildren) {
		super(plugin.getName(), allowsChildren);
		this.plugin = plugin;
	}
	
	public PluginBaseNode(PluginBaseRO plugin) {
		super(plugin.getName());
		this.plugin = plugin;
	}

	/**
	 * @return the plugin
	 */
	public PluginBaseRO getPlugin() {
		return plugin;
	}

	/**
	 * @param plugin the plugin to set
	 */
	public void setPlugin(PluginBaseRO plugin) {
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