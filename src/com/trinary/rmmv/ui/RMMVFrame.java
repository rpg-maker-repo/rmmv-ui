package com.trinary.rmmv.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import com.trinary.rmmv.client.PluginClient;
import com.trinary.rpgmaker.ro.PluginRO;

public class RMMVFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public RMMVFrame() {
		Icon closed = (Icon) UIManager.get("Tree.leafIcon");
	    Icon opened = (Icon) UIManager.get("Tree.leafIcon");
		UIManager.put("Tree.closedIcon", closed);
		UIManager.put("Tree.openIcon",  opened);
		UIManager.put("Tree.leafIcon", opened);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 597, 513);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.NORTH);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JTree local = new JTree();
		splitPane.setLeftComponent(local);
		
		JTree remote = new JTree();
		remote.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		remote.addMouseListener(new PluginSelectionListener(remote));
		remote.addTreeSelectionListener(new PluginSelectionListener(remote));
		splitPane.setRightComponent(remote);
		
		this.setVisible(true);
		
		splitPane.setDividerLocation(0.5);
		splitPane.setResizeWeight(0.5);
		
		DefaultMutableTreeNode root = null;
		try {
			root = createRemoteTree(null);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		DefaultTreeModel model = new DefaultTreeModel(root);
		remote.setModel(model);
		
		root = new DefaultMutableTreeNode("Local Plugins");
		model = new DefaultTreeModel(root);
		local.setModel(model);
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