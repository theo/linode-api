package com.notedpath.linode;

import junit.framework.TestCase;

public class LinodeTest extends TestCase {
	private Linode linode;

	public void setUp() throws Exception {
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

}
