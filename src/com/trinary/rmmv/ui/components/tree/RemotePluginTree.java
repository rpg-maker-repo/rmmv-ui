package com.trinary.rmmv.ui.components.tree;

import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.trinary.rmmv.client.PluginClient;
import com.trinary.rmmv.client.PluginVersionClient;
import com.trinary.rmmv.ui.components.tree.nodes.PluginBaseNode;
import com.trinary.rmmv.ui.components.tree.nodes.PluginDependencyNode;
import com.trinary.rmmv.ui.components.tree.nodes.PluginVersionNode;
import com.trinary.rpgmaker.ro.PluginBaseRO;
import com.trinary.rpgmaker.ro.PluginRO;

public class RemotePluginTree extends JTree {
	private static final long serialVersionUID = 1L;

	public RemotePluginTree() {
		refreshTree();
	}
	
	public void refreshTree() {
		DefaultMutableTreeNode pluginRootNode = null;
		DefaultMutableTreeNode tilesetRootNode = null;
		try {
			pluginRootNode = createRemotePluginTree();
			tilesetRootNode = createRemoteTilesetTree();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Remote Assets");
		root.add(pluginRootNode);
		root.add(tilesetRootNode);
		
		DefaultTreeModel model = new DefaultTreeModel(root);
		this.setModel(model);
	}
	
	protected DefaultMutableTreeNode createRemoteTilesetTree() throws Exception {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("tilesets");
		
		return node;
	}
	
	protected DefaultMutableTreeNode createRemotePluginTree() throws Exception {
		PluginClient client = new PluginClient();
		List<PluginBaseRO> plugins = null;
		DefaultMutableTreeNode node = null;

		node = new DefaultMutableTreeNode("plugins");
		plugins = client.getAll();
		
		if (plugins != null) {
			for (PluginBaseRO pluginRO : plugins) {
				PluginBaseNode child;
				try {
					child = (PluginBaseNode)createRemotePluginBaseTree(pluginRO);
				} catch (Exception e) {
					continue;
				}
				node.add(child);
			}
		}
		
		return node;
	}
	
	protected PluginBaseNode createRemotePluginBaseTree(PluginBaseRO plugin) throws Exception {
		PluginClient client = new PluginClient();
		List<PluginRO> versions = null;
		PluginBaseNode node = new PluginBaseNode(plugin);
		versions = client.getVersions(plugin.getId());
		
		DefaultMutableTreeNode versionsRootNode = new DefaultMutableTreeNode("versions");
		
		if (versions != null) {
			for (PluginRO version : versions) {
				PluginVersionNode child;
				try {
					child = (PluginVersionNode)createRemoteVersionTree(version);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				
				// If not a dependency
				if (plugin == null) {
					child.setIsDownloadable(true);
				}
				versionsRootNode.add(child);
			}
		}
		node.add(versionsRootNode);
		
		return node;
	}
	
	protected PluginVersionNode createRemoteVersionTree(PluginRO plugin) throws Exception {
		PluginVersionClient client = new PluginVersionClient();
		List<PluginRO> dependencies = null;
		PluginVersionNode node = new PluginVersionNode(plugin);
		
		dependencies = client.getDependencies(plugin);
		DefaultMutableTreeNode dependenciesNode = new DefaultMutableTreeNode("dependencies");
		for (PluginRO dependency : dependencies) {
			PluginDependencyNode child = (PluginDependencyNode)createRemoteDependencyTree(dependency);
			dependenciesNode.add(child);
		}
		
		if (!dependencies.isEmpty()) {
			node.add(dependenciesNode);
		}
		
		return node;
	}
	
	protected PluginDependencyNode createRemoteDependencyTree(PluginRO plugin) throws Exception {
		PluginVersionClient client = new PluginVersionClient();
		List<PluginRO> dependencies = null;
		PluginDependencyNode node = new PluginDependencyNode(plugin);
		
		dependencies = client.getDependencies(plugin);
		
		for (PluginRO dependency : dependencies) {
			PluginDependencyNode child = (PluginDependencyNode)createRemoteDependencyTree(dependency);
			node.add(child);
		}
		
		return node;
	}
}
