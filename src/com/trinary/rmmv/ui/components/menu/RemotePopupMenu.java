package com.trinary.rmmv.ui.components.menu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.trinary.rmmv.ui.actions.DownloadAction;
import com.trinary.rmmv.ui.components.tree.nodes.PluginNode;
import com.trinary.rmmv.ui.components.tree.nodes.PluginVersionNode;

public class RemotePopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	private JMenuItem downloadItem;

	public RemotePopupMenu(PluginNode pluginNode) {
		if (pluginNode instanceof PluginVersionNode) {
			downloadItem = new JMenuItem();
			downloadItem.setAction(new DownloadAction(pluginNode.getPlugin()));
			this.add(downloadItem);
		}
	}
}