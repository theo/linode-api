package com.notedpath.linode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response from an Linode API call
 * 
 * @author theodore nguyen-cao
 * 
 */
public class LinodeResponse {
	private JSONObject json;
	private static final String DATA = "DATA";
	private static final String ACTION = "ACTION";
	private static final String ERRORARRAY = "ERRORARRAY";

	/**
	 * @param json
	 */
	public LinodeResponse(JSONObject json) {
		this.json = json;
	}

	/**
	 * Get the data as an object (could be JSONArray or JSONObject
	 * 
	 * @return data object
	 * @throws JSONException
	 */
	public Object getData() throws JSONException {
		return json.get(DATA);
	}

	/**
	 * Data as JSONObject
	 * 
	 * @return JSONObject
	 * @throws JSONException
	 */
	public JSONObject getDataAsJSONObject() throws JSONException {
		return json.getJSONObject(DATA);
	}

	/**
	 * Data as JSONArray
	 * 
	 * @return JSONArray
	 * @throws JSONException
	 */
	public JSONArray getDataAsJSONArray() throws JSONException {
		return json.getJSONArray(DATA);
	}

	/**
	 * Action value
	 * 
	 * @return
	 * @throws JSONException
	 */
	public String getAction() throws JSONException {
		return json.getString(ACTION);
	}

	/**
	 * returns JSONArray of errors
	 * 
	 * @return JSONArray of errors
	 * @throws JSONException
	 */
	public JSONArray getErrorArray() throws JSONException {
		return json.getJSONArray(ERRORARRAY);
	}

	public JSONObject getJSON() {

		return json;
	}
}
