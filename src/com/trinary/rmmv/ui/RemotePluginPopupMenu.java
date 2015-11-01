package com.trinary.rmmv.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.trinary.rpgmaker.ro.PluginRO;

public class RemotePluginPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	private JMenuItem downloadItem;
	
	public class DownloadAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		protected PluginRO plugin;
		
		public DownloadAction(PluginRO plugin) {
			super("Download");
			this.plugin = plugin;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Downloading " + plugin.getName());
		}
	}

	public RemotePluginPopupMenu(PluginNode pluginNode) {
		if (pluginNode.getIsDownloadable()) {
			downloadItem = new JMenuItem();
			downloadItem.setAction(new DownloadAction(pluginNode.getPlugin()));
			this.add(downloadItem);
		}
	}
}