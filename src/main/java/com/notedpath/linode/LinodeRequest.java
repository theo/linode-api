package com.notedpath.linode;

public class LinodeRequest {
	private API_ACTION action;
	private String[] parameters;

	public LinodeRequest() {

	}

	public LinodeRequest(API_ACTION action, String... parameters) {
		this.action = action;
		this.parameters = parameters;
	}

	/**
	 * @return the action
	 */
	public API_ACTION getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(API_ACTION action) {
		this.action = action;
	}

	/**
	 * @return the parameters
	 */
	public String[] getParameters() {
		return parameters;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(String... parameters) {
		this.parameters = parameters;
	}

}
