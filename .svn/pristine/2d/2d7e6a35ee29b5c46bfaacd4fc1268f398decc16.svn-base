package com.htmitech.photoselector.model;

import com.htmitech.photoselector.model.workflow.FieldItem;

import java.io.Serializable;

/**
 * 
 * @author Aizaz
 *
 */


public class PhotoModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String originalPath;
	private boolean isChecked;
	private Fielditems item_common;
	private FieldItem item_workflow;
	private boolean isNet;
	private String file_id;

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public PhotoModel(String originalPath, boolean isChecked) {
		super();
		this.originalPath = originalPath;
		this.isChecked = isChecked;
	}

	public PhotoModel(String originalPath) {
		this.originalPath = originalPath;
	}

	public PhotoModel() {
	}


	public Fielditems getItem_common() {
		return item_common;
	}

	public void setItem_common(Fielditems item_common) {
		this.item_common = item_common;
	}

	public String getOriginalPath() {
		return originalPath;
	}

	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public FieldItem getItem_workflow() {
		return item_workflow;
	}

	public void setItem_workflow(FieldItem item_workflow) {
		this.item_workflow = item_workflow;
	}

	//	@Override
//	public boolean equals(Object o) {
//		if (o.getClass() == getClass()) {
//			PhotoModel model = (PhotoModel) o;
//			if (this.getOriginalPath().equals(model.getOriginalPath())) {
//				return true;
//			}
//		}
//		return false;
//	}

	public void setChecked(boolean isChecked) {
		System.out.println("checked " + isChecked + " for " + originalPath);
		this.isChecked = isChecked;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((originalPath == null) ? 0 : originalPath.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PhotoModel)) {
			return false;
		}
		PhotoModel other = (PhotoModel) obj;
		if (originalPath == null) {
			if (other.originalPath != null) {
				return false;
			}
		} else if (!originalPath.equals(other.originalPath)) {
			return false;
		}
		return true;
	}


	public boolean isNet(){
		return originalPath.contains("http://");
	}

}
