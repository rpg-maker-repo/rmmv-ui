package com.trinary.rmmv.ui.components.tree;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class LocalPluginTree extends JTree {
	private static final long serialVersionUID = 1L;

	public LocalPluginTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Local Plugins");
		DefaultTreeModel model = new DefaultTreeModel(root);
		this.setModel(model);
	}
}
