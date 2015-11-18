package com.trinary.rmmv.ui.components.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trinary.rmmv.ui.ro.Project;

public class ProjectNode extends DefaultMutableTreeNode implements LocalNode {
	private static final long serialVersionUID = 1L;
	protected Project project;
	
	public ProjectNode(Project project) {
		super(project.getName());
		this.project = project;
	}

	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return project;
	}
}