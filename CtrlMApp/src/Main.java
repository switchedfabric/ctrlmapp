import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.regions.Regions;
import com.bmc.ctmem.emapi.EMBasicXMLInvoker;
import com.bmc.ctmem.emapi.EMXMLInvoker;
import com.bmc.ctmem.emapi.GSRComponent;
import com.bmc.ctmem.emapi.InvokeException;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		String[] hosts;
		
		System.out.println("new CtrlMConnection()...");
		CtrlM c = new CtrlM(args);
		System.out.println("new CtrlMConnection() done");
		
		System.out.println("c.login()...");
		if (! c.login("wroos", "wroos123")) {
			System.out.println("c.login() failed");
			c.close();
			return;
		}
		else {
			System.out.println("c.login() done");
		}

		System.out.println("Getting list of jobs...");
		String[] jobs = c.getJobs();
		System.out.println("Done");

		System.out.println("c.logout() & c.close()...");
		c.logout();
		c.close();
		System.out.println("c.logout() & c.close() done");
		
		// AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		// client.withRegion(Regions.EU_WEST_1);
		
	}

	void foo(String[] args) {
		
		Properties props = new Properties();
		// props.setProperty("jacorb.implname", "StandardNS");
		props.setProperty("ORBInitRef.NameService", "corbaloc:iiop:1.2@ctrldevintapp1:13076/NameService");
		props.setProperty("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
	    props.setProperty("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");
		
		System.out.println("EMXMLInvoker.init()...");
		try {
			EMXMLInvoker.init(args, props);
		} catch (Exception e) {
			System.out.println("EMXMLInvoker.init() error");
			e.printStackTrace();
		}
		System.out.println("Done");
		
		System.out.println("new EMBasicXMLInvoker(gsrComponent)...");
		GSRComponent gsrComponent = new GSRComponent();
		EMBasicXMLInvoker gsrInvoker = new EMBasicXMLInvoker(gsrComponent);
		System.out.println("Done");

		String xmlRequest = "";
		String password = "";
		
		try {
			password = gsrInvoker.BuildPasswordString("wroos123");
		} catch (InvokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // xml request
		xmlRequest = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + 
			"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
				"<SOAP-ENV:Body>" +
					"<ctmem:request_register xmlns:ctmem=\"http://www.bmc.com/ctmem/schema800\">" +
						"<ctmem:user_name>wroos</ctmem:user_name>" +
						"<ctmem:password>" + password + "</ctmem:password>" +
						"<ctmem:timeout>720</ctmem:timeout>" +
					"</ctmem:request_register>" +
				"</SOAP-ENV:Body>" +
			"</SOAP-ENV:Envelope>";
		System.out.println("Request:");
		System.out.println(XMLHelper.formatXML(xmlRequest));

		String xmlResponse = null;
		try{
		    xmlResponse = gsrInvoker.invoke(xmlRequest);
		}
		catch(InvokeException ex){
			System.out.println("gsrInvoker.invoke(xmlRequest) error");
			ex.printStackTrace();
			return;
		}
		System.out.println("Response:");
		System.out.println(XMLHelper.formatXML(xmlResponse));
		System.out.println("Done");

		
		System.out.println("Disconnect...");
		EMXMLInvoker.done();
		System.out.println("Done");
		
	}
}
	

