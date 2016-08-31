import java.util.Properties;

import com.bmc.ctmem.emapi.EMBasicXMLInvoker;
import com.bmc.ctmem.emapi.EMXMLInvoker;
import com.bmc.ctmem.emapi.GSRComponent;
import com.bmc.ctmem.emapi.InvokeException;

public class CtrlM {
	private EMBasicXMLInvoker invoker;
	private String userToken;
	private boolean debug;
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/*
	 * Constructors:
	 */
	public CtrlM() {
		init(null, false);
	}
	
	public CtrlM(boolean debug) {
		init(null, debug);
	}
	
	public CtrlM(String[] args) {
		init(args, false);
	}

	public CtrlM(String[] args, boolean debug) {
		init(args, debug);
	}

	/*
	 * EMBasicXMLInvoker and GSRComponent weirdness
	 */
	public void init(String[] args, boolean debug) {
		this.debug = debug;
		
		Properties props = new Properties();
		// props.setProperty("jacorb.implname", "StandardNS");
		props.setProperty("ORBInitRef.NameService", "corbaloc:iiop:1.2@ctrldevintapp1:13076/NameService");
		props.setProperty("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
	    props.setProperty("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");
	    props.setProperty("jacorb.security.support_ssl", "false");
		
		if(debug) System.out.println("EMXMLInvoker.init()...");
		try {
			EMXMLInvoker.init(args, props);
		} catch (Exception e) {
			System.out.println("EMXMLInvoker.init() error");
			e.printStackTrace();
		}
		if(debug) System.out.println("Done");

		
		if(debug) System.out.println("new EMBasicXMLInvoker(gsrComponent)...");
		invoker = new EMBasicXMLInvoker(new GSRComponent());
		if(debug) System.out.println("Done");
		
		userToken = "";
	}
	
	/*
	 * XML soap requests
	 */
	public boolean login(String username, String password) {
		String xmlRequest = "";
		
		xmlRequest = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + 
				"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
					"<SOAP-ENV:Body>" +
						"<ctmem:request_register xmlns:ctmem=\"http://www.bmc.com/ctmem/schema800\">" +
							"<ctmem:user_name>" + username + "</ctmem:user_name>" +
							"<ctmem:password>" + password + "</ctmem:password>" +
							"<ctmem:timeout>720</ctmem:timeout>" +
						"</ctmem:request_register>" +
					"</SOAP-ENV:Body>" +
				"</SOAP-ENV:Envelope>";
		if (debug) {
			System.out.println("Request:");
			System.out.println(XMLHelper.formatXML(xmlRequest));
		}

		String xmlResponse = "";
		try{
		    xmlResponse = invoker.invoke(xmlRequest);
		}
		catch(InvokeException ex){
			System.out.println("gsrInvoker.invoke(xmlRequest) error");
			ex.printStackTrace();
			return false;
		}
		if (debug) {
			System.out.println("Response:");
			System.out.println(XMLHelper.formatXML(xmlResponse));
			System.out.println("Done");
		}
		
		/*
		 * Now check if th status is OK and get the token
		 */
		if (debug) System.out.println("Check status:");
		String status = XMLHelper.getElement(xmlResponse, "ctmem:status");
		if (debug) System.out.println("Status: " + status);
		
		if(! status.equals("OK")) {
			return false;
		}
		
		userToken = XMLHelper.getElement(xmlResponse, "ctmem:user_token");
		if (debug) System.out.println("Token: " + userToken);

		return true;
	}
	
	public boolean logout() {
		String xmlRequest = "";
		
		xmlRequest = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + 
				"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
					"<SOAP-ENV:Body>" +
						"<ctmem:request_unregister xmlns:ctmem=\"http://www.bmc.com/ctmem/schema800\">" +
							"<ctmem:user_token>" + userToken + "</ctmem:user_token>" +
						"</ctmem:request_unregister>" +
					"</SOAP-ENV:Body>" +
				"</SOAP-ENV:Envelope>";
		if (debug) {
			System.out.println("Request:");
			System.out.println(XMLHelper.formatXML(xmlRequest));
		}
		
		String xmlResponse = "";
		try{
		    xmlResponse = invoker.invoke(xmlRequest);
		}
		catch(InvokeException ex){
			System.out.println("gsrInvoker.invoke(xmlRequest) error");
			ex.printStackTrace();
			return false;
		}
		if (debug) {
			System.out.println("Response:");
			System.out.println(XMLHelper.formatXML(xmlResponse));
			System.out.println("Done");
		}

		/*
		 * Now check if the status is OK and get the token
		 */
		if (debug) System.out.println("Check status:");
		String status = XMLHelper.getElement(xmlResponse, "ctmem:status");
		if (debug) System.out.println("Status: " + status);
		
		if(! status.equals("OK")) {
			return false;
		}
		
		return true;
	}

	public void close() {
		EMXMLInvoker.done();
	}

	public String[] getJobs() {
		String[] jobs = null;
		
		String xmlRequest = "<?xml?>..."; // xml request
		if (debug) System.out.println("gsrInvoker.invoke(" + xmlRequest + "))...");

		@SuppressWarnings("unused")
		String xmlResponse;

		try{
		    xmlResponse = invoker.invoke(xmlRequest);
		}
		catch(InvokeException ex){
			System.out.println("gsrInvoker.invoke(" + xmlRequest + ")");
			ex.printStackTrace();
			return null;
		}
		if (debug) System.out.println("Done");
		
		return jobs;
	}

	public boolean dummyOperation() {
		String xmlRequest = "<?xml?>..."; // xml request
		if (debug) System.out.println("gsrInvoker.invoke(" + xmlRequest + "))...");

		@SuppressWarnings("unused")
		String xmlResponse;

		try{
		    xmlResponse = invoker.invoke(xmlRequest);
		}
		catch(InvokeException ex){
			System.out.println("gsrInvoker.invoke(" + xmlRequest + ")");
			ex.printStackTrace();
			return false;
		}
		if (debug) System.out.println("Done");
		
		return true;
	}
}
