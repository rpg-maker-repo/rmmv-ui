package com.trinary.rmmv.ui;

import com.trinary.rmmv.util.types.ProjectRO;

public class ApplicationState {
	protected ProjectRO localSelectedProject = null;

	/**
	 * @return the localSelectedProject
	 */
	public ProjectRO getLocalSelectedProject() {
		return localSelectedProject;
	}

	/**
	 * @param localSelectedProject the localSelectedProject to set
	 */
	public void setLocalSelectedProject(ProjectRO localSelectedProject) {
		this.localSelectedProject = localSelectedProject;
	}
}