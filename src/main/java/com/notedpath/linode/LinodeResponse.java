package com.notedpath.linode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LinodeResponse {
	private JSONObject json;
	private static final String DATA = "DATA";
	private static final String ACTION = "ACTION";
	private static final String ERRORARRAY = "ERRORARRAY";

	public LinodeResponse(JSONObject json) {
		this.json = json;
	}

	public Object getData() throws JSONException {
		return  json.get(DATA);
	}
	
	public JSONObject getDataAsJSONObject() throws JSONException {
		return  json.getJSONObject(DATA);
	}
	
	public JSONArray getDataAsJSONArray() throws JSONException {
		return  json.getJSONArray(DATA);
	}


	public String getAction() throws JSONException {
		return json.getString(ACTION);
	}

	public JSONArray getErrorArray() throws JSONException {
		return json.getJSONArray(ERRORARRAY);
	}

	public JSONObject getJSON() {

		return json;
	}
}
