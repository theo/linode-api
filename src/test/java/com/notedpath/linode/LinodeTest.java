package com.notedpath.linode;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class LinodeTest extends TestCase {
	private Linode linode;

	public void setUp() throws Exception {

		// Set your API key!!!
		String API_KEY = "MY_API_KEY";
		linode = new Linode(API_KEY, true);
	}

	public void testTestEcho() throws Exception {
		LinodeResponse o = linode.testEcho("foo", "bar");
		System.out.println("data: " + o.getData());
		System.out.println("action: " + o.getAction());
		System.out.println("errors: " + o.getErrorArray());
	}

	public void testAvailableDatacenters() throws Exception {
		LinodeResponse o = linode.availableDatacenters();
		System.out.println("data: " + o.getData());
		System.out.println("action: " + o.getAction());
		System.out.println("errors: " + o.getErrorArray());
	}

	public void testAvailableDistributions() throws Exception {
		LinodeResponse o = linode.availableDistributions();
		System.out.println("data: " + o.getData());
		System.out.println("action: " + o.getAction());
		System.out.println("errors: " + o.getErrorArray());
	}

	public void testAvailableKernels() throws Exception {
		LinodeResponse o = linode.availableKernels();
		System.out.println("data: " + o.getData());
		System.out.println("action: " + o.getAction());
		System.out.println("errors: " + o.getErrorArray());
	}

	public void testAvailableLinodePlans() throws Exception {
		LinodeResponse o = linode.availableLinodePlans();
		System.out.println("data: " + o.getData());
		System.out.println("action: " + o.getAction());
		System.out.println("errors: " + o.getErrorArray());
	}

	public void testDomainCreate() throws Exception {
		LinodeResponse o = linode.domainCreate();
		System.out.println("data: " + o.getData());
		System.out.println("action: " + o.getAction());
		System.out.println("errors: " + o.getErrorArray());
	}

	public void testExecute() throws Exception {
		LinodeResponse o = linode.execute(API_ACTION.TEST_ECHO, "foo", "bar");
		System.out.println("data: " + o.getData());
		System.out.println("action: " + o.getAction());
		System.out.println("errors: " + o.getErrorArray());
	}

	@SuppressWarnings("unchecked")
	public void testBatch() throws Exception {
		LinodeRequest req1 = new LinodeRequest(API_ACTION.TEST_ECHO, "foo", "bar");
		LinodeRequest req2 = new LinodeRequest(API_ACTION.TEST_ECHO, "foo", "bar");
		List<LinodeRequest> reqs = new ArrayList<LinodeRequest>();
		reqs.add(req1);
		reqs.add(req2);

		Object obj = linode.batchExecute(reqs);
		if (obj instanceof LinodeResponse) {
			LinodeResponse o = (LinodeResponse) obj;
		} else if (obj instanceof List<?>) {
			List<LinodeResponse> responses = (List<LinodeResponse>) obj;
			for (LinodeResponse o : responses) {
				System.out.println("data: " + o.getData());
				System.out.println("action: " + o.getAction());
				System.out.println("errors: " + o.getErrorArray());

			}
		}
		// https://api.linode.com/?api_key=MY_API_KEY&api_action=batch&api_requestArray=[{"api_action":"test.echo","foo":"bar"},{"api_action":"test.echo","foo":"bar"}]

	}

}
