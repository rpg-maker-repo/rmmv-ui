package com.trinary.rmmv.ui;

import com.trinary.rmmv.ui.ro.Project;

public class ApplicationState {
	protected Project localSelectedProject = null;

	/**
	 * @return the localSelectedProject
	 */
	public Project getLocalSelectedProject() {
		return localSelectedProject;
	}

	/**
	 * @param localSelectedProject the localSelectedProject to set
	 */
	public void setLocalSelectedProject(Project localSelectedProject) {
		this.localSelectedProject = localSelectedProject;
	}
}