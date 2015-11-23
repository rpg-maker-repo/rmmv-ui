package com.trinary.rmmv.ui.components.menu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.trinary.rmmv.ui.actions.DisableAction;
import com.trinary.rmmv.ui.actions.EnableAction;
import com.trinary.rmmv.ui.actions.UpdateAction;
import com.trinary.rmmv.ui.components.tree.nodes.LocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.OutOfDatePluginLocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.PluginNode;
import com.trinary.rmmv.util.analysis.types.PluginDescriptor;
import com.trinary.rmmv.util.types.OutOfDatePluginRO;

public class LocalPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	private JMenuItem updateItem = new JMenuItem();
	private JMenuItem enableItem = new JMenuItem();
	private JMenuItem disableItem = new JMenuItem();

	public LocalPopupMenu(PluginNode pluginNode) {
		if (pluginNode instanceof OutOfDatePluginLocalNode) {
			updateItem.setAction(new UpdateAction(((OutOfDatePluginLocalNode)pluginNode).getProject(), (OutOfDatePluginRO)pluginNode.getPlugin()));
			this.add(updateItem);
		}
		
		if (pluginNode instanceof LocalNode) {
			PluginDescriptor descriptor = ((LocalNode) pluginNode)
					.getProject()
					.getPluginDescriptor(pluginNode.getPlugin());
			
			if (descriptor == null || !descriptor.getStatus()) {
				enableItem.setAction(new EnableAction(((LocalNode) pluginNode).getProject(), pluginNode.getPlugin()));
				this.add(enableItem);
			} else {
				disableItem.setAction(new DisableAction(((LocalNode) pluginNode).getProject(), pluginNode.getPlugin()));
				this.add(disableItem);
			}
		}
	}
}