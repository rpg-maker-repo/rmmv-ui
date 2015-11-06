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
		DefaultMutableTreeNode root = null;
		try {
			root = createRemotePluginTree(null);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		DefaultTreeModel model = new DefaultTreeModel(root);
		this.setModel(model);
	}
	
	protected DefaultMutableTreeNode createRemotePluginTree(PluginBaseRO plugin) throws Exception {
		PluginClient client = new PluginClient();
		List<PluginBaseRO> plugins = null;
		List<PluginRO> versions = null;
		DefaultMutableTreeNode node = null;
		if (plugin == null) {
			node = new DefaultMutableTreeNode("Remote Plugins");
			plugins = client.getAll();
		} else {
			node = new PluginBaseNode(plugin);
			versions = client.getVersions(plugin.getId());
		}
		
		if (plugins != null) {
			for (PluginBaseRO pluginRO : plugins) {
				PluginBaseNode child;
				try {
					child = (PluginBaseNode)createRemotePluginTree(pluginRO);
				} catch (Exception e) {
					continue;
				}
				node.add(child);
			}
		} else if (versions != null) {
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
				node.add(child);
			}
		}
		
		return node;
	}
	
	protected PluginVersionNode createRemoteVersionTree(PluginRO plugin) throws Exception {
		PluginVersionClient client = new PluginVersionClient();
		List<PluginRO> dependencies = null;
		PluginVersionNode node = new PluginVersionNode(plugin);
		
		dependencies = client.getDependencies(plugin);
		for (PluginRO dependency : dependencies) {
			PluginDependencyNode child = (PluginDependencyNode)createRemoteDependencyTree(dependency);
			node.add(child);
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
