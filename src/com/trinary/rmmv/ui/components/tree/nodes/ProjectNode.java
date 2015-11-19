package com.trinary.rmmv.ui.components.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trinary.rmmv.util.types.ProjectRO;

public class ProjectNode extends DefaultMutableTreeNode implements LocalNode {
	private static final long serialVersionUID = 1L;
	protected ProjectRO project;
	
	public ProjectNode(ProjectRO project) {
		super(project.getName());
		this.project = project;
	}

	@Override
	public ProjectRO getProject() {
		// TODO Auto-generated method stub
		return project;
	}
}