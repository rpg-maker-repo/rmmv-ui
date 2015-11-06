package com.trinary.rmmv.ui.listeners;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.trinary.rmmv.ui.components.menu.RemotePopupMenu;
import com.trinary.rmmv.ui.components.tree.nodes.PluginNode;
import com.trinary.rmmv.ui.components.tree.nodes.PluginVersionNode;
import com.trinary.rpgmaker.ro.PluginRO;

public class PluginTreeListener implements TreeSelectionListener, MouseListener {
	protected JTree tree;
	protected JComponent statusPanel;
	
	public PluginTreeListener(JTree tree, JComponent component) {
		this.tree = tree;
		this.statusPanel = component;
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if (tree.getLastSelectedPathComponent() instanceof PluginNode) {
			PluginNode node = (PluginNode)tree.getLastSelectedPathComponent();
			PluginRO plugin = node.getPlugin();
			
			String pluginDescription = String.format(""
					+ "Name:        %s (%s)\n"
					+ "Description: %s\n"
					+ "RM Version:  %s", 
					plugin.getName(), plugin.getVersion(),
					plugin.getDescription(),
					plugin.getCompatibleRMVersion());
			
			// Show plugin details below split pane.
			JTextPane text = (JTextPane)statusPanel;
			text.setText(pluginDescription);
			return;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!(tree.getLastSelectedPathComponent() instanceof PluginVersionNode)) {
			return;
		}
		
		if (SwingUtilities.isRightMouseButton(e)) {
			TreePath path = tree.getPathForLocation (e.getX (), e.getY ());
            Rectangle pathBounds = tree.getUI ().getPathBounds ( tree, path );
            if ( pathBounds != null && pathBounds.contains ( e.getX (), e.getY () ) )
            {
        		if (!(path.getLastPathComponent() instanceof PluginVersionNode)) {
        			return;
        		}
            	PluginVersionNode node = (PluginVersionNode) path.getLastPathComponent();

                RemotePopupMenu menu = new RemotePopupMenu(node);
                menu.show(tree, pathBounds.x, pathBounds.y + pathBounds.height);
            }
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}