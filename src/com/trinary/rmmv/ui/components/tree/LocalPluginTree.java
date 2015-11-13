package com.trinary.rmmv.ui.components.tree;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.codec.binary.Base64;

import com.trinary.rmmv.client.PluginVersionClient;
import com.trinary.rmmv.ui.components.tree.nodes.AmbiguousPluginVersionLocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.PluginVersionLocalNode;
import com.trinary.rmmv.ui.components.tree.nodes.UnknownPluginVersionLocalNode;
import com.trinary.rmmv.ui.ro.AmbiguousPluginRO;
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
		        }

		        return this;
			}
			
		});
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Local Plugins");
		Map<String, PluginRO> map = analyzeProject("/Users/mmain/Documents/Games/Test Game");
		
		for (PluginRO plugin : map.values()) {
			 if (plugin instanceof UnknownPluginRO) {
				root.add(new UnknownPluginVersionLocalNode((UnknownPluginRO)plugin));
			} else if (plugin instanceof AmbiguousPluginRO) {
				AmbiguousPluginVersionLocalNode node = new AmbiguousPluginVersionLocalNode((AmbiguousPluginRO)plugin);
				AmbiguousPluginRO ambiguousPlugin = (AmbiguousPluginRO)plugin;
				for (PluginRO match : ambiguousPlugin.getPlugins()) {
					node.add(new PluginVersionLocalNode(match));
				}
				root.add(node);
			} else if (plugin instanceof PluginRO) {
				root.add(new PluginVersionLocalNode(plugin));
			}
		}
		
		DefaultTreeModel model = new DefaultTreeModel(root);
		this.setModel(model);
	}
	
	public Map<String, PluginRO> analyzeProject(String projectDirectory) {
		File dir = new File(projectDirectory + "/js/plugins");
		
		if (!dir.isDirectory()) {
			return null;
		}
		
		File[] files = dir.listFiles();
		Map<String, PluginRO> plugins = new HashMap<String, PluginRO>();
		
		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".js")) {
				PluginRO plugin = null;
				plugin = identifyFile(file);
				
				if (plugin != null) {
					plugins.put(file.getAbsolutePath(), plugin);
				}
			}
		}
		
		return plugins;
	}
	
	public PluginRO identifyFile(File file) {
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
		
		return plugins.get(0);
	}
	
	public String getFileHash(File file) {
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
