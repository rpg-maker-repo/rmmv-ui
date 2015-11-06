package com.trinary.rmmv.ui.components.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreeSelectionModel;

import com.trinary.rmmv.ui.components.tree.LocalPluginTree;
import com.trinary.rmmv.ui.components.tree.RemotePluginTree;
import com.trinary.rmmv.ui.listeners.PluginTreeListener;

public class PluginManagerPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public PluginManagerPanel() {
		super();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		this.add(splitPane, BorderLayout.CENTER);
		
		JTree local = new LocalPluginTree();
		splitPane.setLeftComponent(local);
		
		JTree remote = new RemotePluginTree();
		remote.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		remote.addMouseListener(new PluginTreeListener(remote));
		remote.addTreeSelectionListener(new PluginTreeListener(remote));
		splitPane.setRightComponent(remote);
		
		this.setVisible(true);
		
		splitPane.setDividerLocation(0.5);
		splitPane.setResizeWeight(0.5);
	}
}
