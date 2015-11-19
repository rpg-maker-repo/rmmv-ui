package com.trinary.rmmv.ui.components.tree;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import com.trinary.rmmv.ui.Application;
import com.trinary.rmmv.ui.ProjectManagerConfig;
import com.trinary.rmmv.ui.components.tree.nodes.AmbiguousPluginVersionLocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.OutOfDatePluginLocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.PluginVersionLocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.ProjectNode;
import com.trinary.rmmv.ui.components.tree.nodes.UnknownPluginVersionLocalNode;
import com.trinary.rmmv.util.RMMVProjectAnalyzer;
import com.trinary.rmmv.util.types.AmbiguousPluginRO;
import com.trinary.rmmv.util.types.OutOfDatePluginRO;
import com.trinary.rmmv.util.types.ProjectRO;
import com.trinary.rmmv.util.types.UnknownPluginRO;
import com.trinary.rpgmaker.ro.PluginRO;

public class LocalPluginTree extends JTree {
	private static final long serialVersionUID = 1L;
	
	protected RMMVProjectAnalyzer analyzer = new RMMVProjectAnalyzer(Application.getRMMVClientConfig());

	public LocalPluginTree() {
		this.setCellRenderer(new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
			 */
			@Override
			public Component getTreeCellRendererComponent(JTree tree,
					Object value, boolean sel, boolean expanded, boolean leaf,
					int row, boolean hasFocus) {
				super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		        // Assuming you have a tree of Strings
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;

		        if (node instanceof UnknownPluginVersionLocalNode) {
		            setForeground(Color.RED);
		        } else if (node instanceof AmbiguousPluginVersionLocalNode) {
		            setForeground(Color.BLUE);
		        } else if (node instanceof OutOfDatePluginLocalNode) {
		        	setForeground(Color.GRAY);
		        } else if (node instanceof PluginVersionLocalNode) {
		        	setForeground(Color.GREEN);
		        }

		        return this;
			}
			
		});
		
		refreshTree();
	}
	
	public void refreshTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Local Projects");
		List<ProjectRO> projects = analyzer.analyzeWorkspace(ProjectManagerConfig.projectsRoot);
		
		for (ProjectRO project : projects) {
			ProjectNode projectRoot = new ProjectNode(project);
			DefaultMutableTreeNode pluginsNode = new DefaultMutableTreeNode("plugins");
			for (PluginRO plugin : project.getPlugins()) {
				if (plugin instanceof OutOfDatePluginRO) {
					pluginsNode.add(new OutOfDatePluginLocalNode(plugin, project));
				} else if (plugin instanceof UnknownPluginRO) {
					 pluginsNode.add(new UnknownPluginVersionLocalNode((UnknownPluginRO)plugin, project));
				} else if (plugin instanceof AmbiguousPluginRO) {
					AmbiguousPluginVersionLocalNode node = new AmbiguousPluginVersionLocalNode((AmbiguousPluginRO)plugin, project);
					AmbiguousPluginRO ambiguousPlugin = (AmbiguousPluginRO)plugin;
					for (PluginRO match : ambiguousPlugin.getPlugins()) {
						node.add(new PluginVersionLocalNode(match, project));
					}
					pluginsNode.add(node);
				} else if (plugin instanceof PluginRO) {
					pluginsNode.add(new PluginVersionLocalNode(plugin, project));
				}
			}
			projectRoot.add(pluginsNode);
			root.add(projectRoot);
		}
		
		DefaultTreeModel model = new DefaultTreeModel(root);
		this.setModel(model);
	}
}