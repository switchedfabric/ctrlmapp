import java.util.Properties;

import com.bmc.ctmem.emapi.EMBasicXMLInvoker;
import com.bmc.ctmem.emapi.EMXMLInvoker;
import com.bmc.ctmem.emapi.GSRComponent;
import com.bmc.ctmem.emapi.InvokeException;

public class CtrlMConnection {
	private GSRComponent gsrComponent;
	private EMBasicXMLInvoker gsrInvoker;
	
	public CtrlMConnection() {
		open(null);
	}
	
	public CtrlMConnection(String[] args) {
		open(args);
	}

	public void open(String[] args) {
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
		gsrComponent = new GSRComponent();
		setGSRInvoker(new EMBasicXMLInvoker(gsrComponent));
		System.out.println("Done");
	}
	
	public void close() {
		EMXMLInvoker.done();
	}

	public EMBasicXMLInvoker getGSRInvoker() {
		return gsrInvoker;
	}

	public void setGSRInvoker(EMBasicXMLInvoker gsrInvoker) {
		this.gsrInvoker = gsrInvoker;
	}
	
	public String[] getJobs() {
		String[] jobs = null;
		
		String xmlRequest = "<?xml?>..."; // xml request
		System.out.println("gsrInvoker.invoke(" + xmlRequest + "))...");

		@SuppressWarnings("unused")
		String xmlResponse;

		try{
		    xmlResponse = gsrInvoker.invoke(xmlRequest);
		}
		catch(InvokeException ex){
			System.out.println("gsrInvoker.invoke(" + xmlRequest + ")");
			ex.printStackTrace();
			return null;
		}
		System.out.println("Done");
		
		return jobs;
	}

	public boolean dummyOperation() {
		String xmlRequest = "<?xml?>..."; // xml request
		System.out.println("gsrInvoker.invoke(" + xmlRequest + "))...");

		@SuppressWarnings("unused")
		String xmlResponse;

		try{
		    xmlResponse = gsrInvoker.invoke(xmlRequest);
		}
		catch(InvokeException ex){
			System.out.println("gsrInvoker.invoke(" + xmlRequest + ")");
			ex.printStackTrace();
			return false;
		}
		System.out.println("Done");
		
		return true;
	}
}
