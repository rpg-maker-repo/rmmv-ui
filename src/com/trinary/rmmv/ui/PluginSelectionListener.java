package com.trinary.rmmv.ui;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class PluginSelectionListener implements TreeSelectionListener, MouseListener {
	protected JTree tree;
	
	public PluginSelectionListener(JTree tree) {
		this.tree = tree;
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if (!(tree.getLastSelectedPathComponent() instanceof PluginNode)) {
			return;
		}
		PluginNode node = (PluginNode)tree.getLastSelectedPathComponent();
		System.out.println("NODE SELECTED");
		System.out.println(node.getPlugin().getName() + "(" + node.getPlugin().getVersion() + ")");
		
		// Show plugin details below split pane.
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!(tree.getLastSelectedPathComponent() instanceof PluginNode)) {
			return;
		}
		
		if (SwingUtilities.isRightMouseButton(e)) {
			TreePath path = tree.getPathForLocation (e.getX (), e.getY ());
            Rectangle pathBounds = tree.getUI ().getPathBounds ( tree, path );
            if ( pathBounds != null && pathBounds.contains ( e.getX (), e.getY () ) )
            {
        		if (!(path.getLastPathComponent() instanceof PluginNode)) {
        			return;
        		}
            	PluginNode node = (PluginNode) path.getLastPathComponent();

                RemotePluginPopupMenu menu = new RemotePluginPopupMenu(node);
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