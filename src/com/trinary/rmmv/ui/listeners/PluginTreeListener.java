package com.trinary.rmmv.ui.listeners;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.trinary.rmmv.ui.Application;
import com.trinary.rmmv.ui.components.menu.LocalPopupMenu;
import com.trinary.rmmv.ui.components.menu.RemotePopupMenu;
import com.trinary.rmmv.ui.components.tree.LocalPluginTree;
import com.trinary.rmmv.ui.components.tree.RemotePluginTree;
import com.trinary.rmmv.ui.components.tree.nodes.LocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.PluginNode;
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
		if (tree.getLastSelectedPathComponent() instanceof LocalNode) {
			LocalNode node = (LocalNode)tree.getLastSelectedPathComponent();
			Application.getState().setLocalSelectedProject(node.getProject());
		}
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
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
		if (SwingUtilities.isRightMouseButton(e)) {
			TreePath path = tree.getPathForLocation (e.getX (), e.getY ());
            Rectangle pathBounds = tree.getUI ().getPathBounds ( tree, path );
            if ( pathBounds != null && pathBounds.contains ( e.getX (), e.getY () ) )
            {
            	PluginNode node = (PluginNode)path.getLastPathComponent();

            	JPopupMenu menu = null;
            	if (tree instanceof RemotePluginTree) {
	                menu = new RemotePopupMenu(node);
	                
            	} else if (tree instanceof LocalPluginTree) {
            		menu = new LocalPopupMenu(node);
            	}
            	
            	if (menu != null) {
            		menu.show(tree, pathBounds.x, pathBounds.y + pathBounds.height);
            	}
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