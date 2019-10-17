package com.htmitech.emportal.entity;

import java.io.Serializable;
import java.util.List;

public class StepDesList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8711836557603244444L;
	public List<Stepdes> stepdes;
	
	public List<Stepdes> getStepdes() {
		return stepdes;
	}

	public void setStepdes(List<Stepdes> stepdes) {
		this.stepdes = stepdes;
	}

}
