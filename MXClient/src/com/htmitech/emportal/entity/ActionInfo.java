package com.htmitech.emportal.entity;

import java.io.Serializable;

public class ActionInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	  private String actionID;
      private String actionName;
      
	  public String getActionID() {
		return actionID;
	}

	public void setActionID(String actionID) {
		this.actionID = actionID;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	

}