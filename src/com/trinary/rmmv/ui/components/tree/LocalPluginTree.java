package com.trinary.rmmv.ui.components.tree;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.codec.binary.Base64;

import com.trinary.rmmv.client.PluginVersionClient;
import com.trinary.rmmv.ui.ProjectManagerConfig;
import com.trinary.rmmv.ui.components.tree.nodes.AmbiguousPluginVersionLocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.OutOfDatePluginLocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.PluginVersionLocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.ProjectNode;
import com.trinary.rmmv.ui.components.tree.nodes.UnknownPluginVersionLocalNode;
import com.trinary.rmmv.ui.ro.AmbiguousPluginRO;
import com.trinary.rmmv.ui.ro.OutOfDatePluginRO;
import com.trinary.rmmv.ui.ro.Project;
import com.trinary.rmmv.ui.ro.UnknownPluginRO;
import com.trinary.rpgmaker.ro.PluginRO;

public class LocalPluginTree extends JTree {
	private static final long serialVersionUID = 1L;

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
		List<Project> projects = analyzeProjects(ProjectManagerConfig.projectsRoot);
		
		for (Project project : projects) {
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
	
	protected List<Project> analyzeProjects(String rootDirectory) {
		File dir = new File(rootDirectory);
		
		if (!dir.isDirectory()) {
			return null;
		}
		
		File[] files = dir.listFiles();
		List<Project> projects = new ArrayList<Project>();
		
		for (File file : files) {
			if (file.isDirectory()) {
				Project project = analyzeProject(file.getAbsolutePath());
				
				if (project != null) {
					projects.add(project);
				}
			}
		}
		
		return projects;
	}
	
	protected Project analyzeProject(String projectDirectory) {
		File rootDir = new File(projectDirectory);
		File dir = new File(projectDirectory + "/js/plugins");
		
		if (!dir.isDirectory()) {
			return null;
		}
		
		File[] files = dir.listFiles();
		List<PluginRO> plugins = new ArrayList<PluginRO>();
		
		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".js")) {
				PluginRO plugin = null;
				plugin = identifyFile(file);
				
				if (plugin != null) {
					plugins.add(plugin);
				}
			}
		}
		
		Project project = new Project();
		project.setName(rootDir.getName());
		project.setPath(rootDir.getAbsolutePath());
		project.setPlugins(plugins);
		
		return project;
	}
	
	protected PluginRO identifyFile(File file) {
		PluginVersionClient client = new PluginVersionClient();
		
		String hash = getFileHash(file);
		
		List<PluginRO> plugins;
		try {
			plugins = client.getPluginByHash(hash);
		} catch (Exception e) {
			return null;
		}
		
		if (plugins == null || plugins.isEmpty()) {
			UnknownPluginRO unknownPlugin = new UnknownPluginRO();
			unknownPlugin.setFilename(file.getName());
			return unknownPlugin;
		}
		
		if (plugins.size() > 1) {
			AmbiguousPluginRO ambiguousPlugin = new AmbiguousPluginRO();
			ambiguousPlugin.setPlugins(plugins);
			return ambiguousPlugin;
		}
		
		try {
			List<PluginRO> latestVersion = client.getLatestVersion(plugins.get(0));
			if (latestVersion.get(0).getId() != plugins.get(0).getId()) {
				OutOfDatePluginRO outOfDatePlugin = new OutOfDatePluginRO(plugins.get(0));
				outOfDatePlugin.setLatestVersion(latestVersion.get(0));
				outOfDatePlugin.setFilename(file.getName());
				return outOfDatePlugin;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		// Set current plugin to local name
		plugins.get(0).setFilename(file.getName());
		
		return plugins.get(0);
	}
	
	protected String getFileHash(File file) {
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
        FileInputStream fis;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        
        byte[] dataBytes = new byte[1024];
     
        int nread = 0; 
        try {
			while ((nread = fis.read(dataBytes)) != -1) {
			  md.update(dataBytes, 0, nread);
			}
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		};
        
        return Base64.encodeBase64String(md.digest());
	}
}
