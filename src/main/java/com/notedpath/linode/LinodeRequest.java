package com.notedpath.linode;

/**
 * Linode API request
 * 
 * @author theodore nguyen-cao
 * 
 */
public class LinodeRequest {
	/**
	 * API_ACTION action to invoke
	 */
	private API_ACTION action;

	/**
	 * Array of parameters (key,value,key,value,...)
	 */
	private String[] parameters;

	public LinodeRequest() {

	}

	/**
	 * Parameterized constructor
	 * 
	 * @param action
	 * @param parameters
	 */
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
