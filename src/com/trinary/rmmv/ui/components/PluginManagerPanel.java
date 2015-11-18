package com.trinary.rmmv.ui.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreeSelectionModel;

import com.trinary.rmmv.ui.components.tree.LocalPluginTree;
import com.trinary.rmmv.ui.components.tree.RemotePluginTree;
import com.trinary.rmmv.ui.listeners.PluginTreeListener;

public class PluginManagerPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	protected JTextPane statusTable;
	protected LocalPluginTree local;
	protected RemotePluginTree remote;

	public PluginManagerPanel() {
		super();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusTable = new JTextPane();
		statusTable.setEditable(false);
		statusTable.setEnabled(false);
		statusPanel.add(statusTable, BorderLayout.CENTER);
		this.add(statusPanel, BorderLayout.SOUTH);
		
		JSplitPane splitPane = new JSplitPane();
		this.add(splitPane, BorderLayout.CENTER);

		JScrollPane leftScrollPane, rightScrollPane;
		leftScrollPane = new JScrollPane();
		rightScrollPane = new JScrollPane();
		
		local = new LocalPluginTree();
		local.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		local.addMouseListener(new PluginTreeListener(local, statusTable));
		local.addTreeSelectionListener(new PluginTreeListener(local, statusTable));
		leftScrollPane.setViewportView(local);
		splitPane.setLeftComponent(leftScrollPane);
		
		remote = new RemotePluginTree();
		remote.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		remote.addMouseListener(new PluginTreeListener(remote, statusTable));
		remote.addTreeSelectionListener(new PluginTreeListener(remote, statusTable));
		rightScrollPane.setViewportView(remote);
		splitPane.setRightComponent(rightScrollPane);
		
		this.setVisible(true);
		
		splitPane.setDividerLocation(0.5);
		splitPane.setResizeWeight(0.5);
	}

	/**
	 * @return the statusTable
	 */
	public JTextPane getStatusTable() {
		return statusTable;
	}

	/**
	 * @return the local
	 */
	public LocalPluginTree getLocalTree() {
		return local;
	}

	/**
	 * @return the remote
	 */
	public RemotePluginTree getRemoteTree() {
		return remote;
	}
	
	
}