package com.trinary.rmmv.ui.components.menu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.trinary.rmmv.ui.actions.UpdateAction;
import com.trinary.rmmv.ui.components.tree.nodes.OutOfDatePluginLocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.PluginNode;
import com.trinary.rmmv.util.types.OutOfDatePluginRO;

public class LocalPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	private JMenuItem updateItem;

	public LocalPopupMenu(PluginNode pluginNode) {
		if (pluginNode instanceof OutOfDatePluginLocalNode) {
			updateItem = new JMenuItem();
			updateItem.setAction(new UpdateAction((OutOfDatePluginRO)pluginNode.getPlugin()));
			this.add(updateItem);
		}
	}
}