package com.trinary.rmmv.ui.components.menu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.trinary.rmmv.ui.components.tree.PluginNode;

public class RemotePopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	private JMenuItem downloadItem;

	public RemotePopupMenu(PluginNode pluginNode) {
		if (pluginNode.getIsDownloadable()) {
			downloadItem = new JMenuItem();
			downloadItem.setAction(new DownloadAction(pluginNode.getPlugin()));
			this.add(downloadItem);
		}
	}
}