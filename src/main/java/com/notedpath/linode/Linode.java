package com.notedpath.linode;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Linode API client written in Java.
 * <p>
 * Sample invocation:<br/>
 * 
 * <pre>
 * // create client
 * Linode linode = new Linode(&quot;&lt;MY API KEY&gt;&quot;);
 * 
 * // execute test.echo method - https://api.linode.com/?api_key=&lt;MY API KEY&gt;&amp;api_action=test.echo&amp;foo=bar
 * LinodeResponse response = linode.execute(API_ACTION.TEST_ECHO, &quot;foo&quot;, &quot;bar&quot;);
 * 
 * // get data
 * System.out.println(&quot;data: &quot; + response.getData());
 * 
 * // get the action we invoked
 * System.out.println(&quot;action: &quot; + response.getAction());
 * 
 * // get array of errors returned
 * System.out.println(&quot;errors: &quot; + response.getErrorArray());
 * 
 * </pre>
 * <p>
 * 
 * @author theodore nguyen-cao
 * 
 */
public class Linode {
	/**
	 * Http client
	 */
	private HttpClient client;

	/**
	 * User's API key
	 */
	private String apiKey;

	/**
	 * API end point - defaults to https://api.linode.com/
	 */
	private String apiEndPoint = "https://api.linode.com/";

	/**
	 * True to enable request debugging, false otherwise
	 */
	private boolean debug;

	/**
	 * Create a Linode client with the specified API key
	 * 
	 * @param apiKey
	 *            user's API key
	 */
	public Linode(String apiKey) {
		this(apiKey, false);
	}

	/**
	 * Create a Linode client with specified API key and debug setting
	 * 
	 * @param apiKey
	 *            user's API key
	 * @param debug
	 *            true to enable request debugging, false otherwise
	 */
	public Linode(String apiKey, boolean debug) {
		this.apiKey = apiKey;
		this.client = new HttpClient();
		this.debug = debug;
	}

	/**
	 * Executes and returns the response for the test.echo method
	 * 
	 * @param params
	 *            array of key-value pairs
	 * @return response
	 * @throws IOException
	 * @throws HttpException
	 * @throws JSONException
	 */
	public LinodeResponse testEcho(String... params) throws IOException, HttpException, JSONException {
		return execute(API_ACTION.TEST_ECHO, params);
	}

	/**
	 * Executes and returns the response for the avail.datacenters method
	 * 
	 * @return response
	 * @throws IOException
	 * @throws HttpException
	 * @throws JSONException
	 */
	public LinodeResponse availableDatacenters() throws IOException, HttpException, JSONException {
		return execute(API_ACTION.AVAIL_DATACENTERS);
	}

	/**
	 * Executes and returns the response for the avail.distributions method
	 * 
	 * @return response
	 * @throws IOException
	 * @throws HttpException
	 * @throws JSONException
	 */
	public LinodeResponse availableDistributions() throws IOException, HttpException, JSONException {
		return execute(API_ACTION.AVAIL_DISTRIBUTIONS);
	}

	/**
	 * Executes and returns the response for the avail.kernels method
	 * 
	 * @return response
	 * @throws IOException
	 * @throws HttpException
	 * @throws JSONException
	 */
	public LinodeResponse availableKernels() throws IOException, HttpException, JSONException {
		return execute(API_ACTION.AVAIL_KERNELS);
	}

	/**
	 * Executes and returns the response for the avail.linodeplans method
	 * 
	 * @return response
	 * @throws IOException
	 * @throws HttpException
	 * @throws JSONException
	 */
	public LinodeResponse availableLinodePlans() throws IOException, HttpException, JSONException {
		return execute(API_ACTION.AVAIL_LINODEPLANS);
	}

	/**
	 * Executes and returns the response for the domain.create method
	 * 
	 * @return response
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 * @throws JSONException
	 */
	public LinodeResponse domainCreate(String... params) throws IOException, HttpException, JSONException {
		return execute(API_ACTION.DOMAIN_CREATE, params);
	}

	/**
	 * Executes and returns the response for the specified API action
	 * 
	 * @param action
	 *            API action to execute
	 * @param params
	 *            parameters to the action
	 * @return response
	 * @throws IOException
	 * @throws HttpException
	 * @throws JSONException
	 */
	public LinodeResponse execute(API_ACTION action, String... params) throws IOException, HttpException, JSONException {
		StringBuilder b = generateURL(action);
		b = addQueryParameters(b, params);
		return execute(b.toString());
	}

	/**
	 * Batch API requests. Returns a JSONArray if the call was successful.
	 * Returns a JSONObject if there was an error with invoking the batch
	 * request.
	 * 
	 * @param requests
	 *            array of LinodeRequest
	 * @return response (JSONArray or JSONObject)
	 * @throws IOException
	 * @throws HttpException
	 * @throws JSONException
	 */
	public Object batchExecute(List<LinodeRequest> requests) throws IOException, HttpException, JSONException {
		StringBuilder b = generateURL("batch");
		JSONArray params = new JSONArray();
		for (LinodeRequest req : requests) {
			JSONObject o = new JSONObject();
			o.put("api_action", req.getAction().getActionName());
			for (int x = 0; x < req.getParameters().length; x++) {
				if (x + 1 < req.getParameters().length) {
					o.put(req.getParameters()[x], (req.getParameters()[x + 1]));
				} else {
					o.put(req.getParameters()[x], JSONObject.NULL);
				}
				x++;
			}
			params.put(o);
		}
		
		String url = b.toString();
		PostMethod post = new PostMethod(url);
		NameValuePair[] nvp = new NameValuePair[]{new NameValuePair("api_requestArray", params.toString())};
		post.setRequestBody(nvp);
		int rcode = client.executeMethod(post);
		if (rcode != HttpStatus.SC_OK) {
			throw new HttpException("Non-200 HTTP Status code returned: " + rcode);
		}

		String response = post.getResponseBodyAsString();

		if (debug) {
			System.out.println("End point: " + url);
			System.out.println(response);
		}

		if (response.charAt(0) == '[') {
			JSONArray res = new JSONArray(response);
			List<LinodeResponse> responses = new ArrayList<LinodeResponse>();
			for (int x = 0; x < res.length(); x++) {
				responses.add(new LinodeResponse(res.getJSONObject(x)));
			}
			return responses;
		}
		LinodeResponse res = new LinodeResponse(new JSONObject(response));
		return res;

	}

	/**
	 * Performs the HTTP get to the specified url and returns the LinodeResponse
	 * 
	 * @param url
	 *            url to hit
	 * @return response
	 * @throws IOException
	 * @throws HttpException
	 * @throws JSONException
	 */
	private LinodeResponse execute(String url) throws IOException, HttpException, JSONException {
		GetMethod get = new GetMethod(url);
		int rcode = client.executeMethod(get);
		if (rcode != HttpStatus.SC_OK) {
			throw new HttpException("Non-200 HTTP Status code returned: " + rcode);
		}

		String response = get.getResponseBodyAsString();
		if (debug) {
			System.out.println("End point: " + url);
			System.out.println(response);
		}
		JSONObject o = new JSONObject(response);
		return new LinodeResponse(o);
	}

	/**
	 * Generates the URL to hit based on the specified action
	 * 
	 * @param action
	 *            API action to invoke
	 * @return StringBuilder of URL
	 */
	private StringBuilder generateURL(API_ACTION action) {
		return generateURL(action.getActionName());
	}

	/**
	 * Generates the URL to hit based on the specified action name
	 * 
	 * @param action
	 *            API action name
	 * @return StringBuilder of URL
	 */
	private StringBuilder generateURL(String action) {
		StringBuilder b = new StringBuilder(this.apiEndPoint);
		if (this.apiKey != null) {
			b.append("?api_key=" + this.apiKey + "&");
		} else {
			b.append("?");
		}
		b.append("api_action=" + action);
		return b;
	}

	/**
	 * Adds the specified query parameters to the URL
	 * 
	 * @param b
	 *            StringBuilder to append to
	 * @param parameters
	 *            parameters to append in key-value format
	 * @return StringBuilder being appended to
	 */
	private StringBuilder addQueryParameters(StringBuilder b, String... parameters) {
		for (int x = 0; x < parameters.length; x++) {
			b.append("&" + encode(parameters[x]) + "=");
			if (x + 1 < parameters.length) {
				b.append(encode(parameters[x + 1]));
			}
			x++;
		}
		return b;
	}

	/**
	 * URL encode as UTF-8
	 * 
	 * @param s
	 *            string to encode
	 * @return encoded string
	 */
	private String encode(String s) {
		String str = s;
		try {
			str = URLEncoder.encode(s, "UTF-8");
		} catch (Exception e) {
			// ignore
		}
		return str;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey
	 *            the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return the apiEndPoint
	 */
	public String getApiEndPoint() {
		return apiEndPoint;
	}

	/**
	 * @param apiEndPoint
	 *            the apiEndPoint to set
	 */
	public void setApiEndPoint(String apiEndPoint) {
		this.apiEndPoint = apiEndPoint;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug
	 *            the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
