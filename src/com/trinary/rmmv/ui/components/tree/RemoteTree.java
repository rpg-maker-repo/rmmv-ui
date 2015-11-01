package com.trinary.rmmv.ui.components.tree;

import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.trinary.rmmv.client.PluginClient;
import com.trinary.rpgmaker.ro.PluginRO;

public class RemoteTree extends JTree {
	private static final long serialVersionUID = 1L;

	public RemoteTree() {
		DefaultMutableTreeNode root = null;
		try {
			root = createRemoteTree(null);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		DefaultTreeModel model = new DefaultTreeModel(root);
		this.setModel(model);
	}
	
	protected DefaultMutableTreeNode createRemoteTree(PluginRO plugin) throws Exception {
		PluginClient client = new PluginClient();
		List<PluginRO> dependencies = null;
		DefaultMutableTreeNode node = null;
		if (plugin == null) {
			node = new DefaultMutableTreeNode("Remote Plugins");
			dependencies = client.getAllPlugins();
		} else {
			node = new PluginNode(plugin);
			dependencies = client.getDependencies(plugin);
		}
		
		for (PluginRO dependency : dependencies) {
			PluginNode child = (PluginNode)createRemoteTree(dependency);
			// If not a dependency
			if (plugin == null) {
				child.setIsDownloadable(true);
			}
			node.add(child);
		}
		
		return node;
	}
}
