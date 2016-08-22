import java.util.*;

import com.bmc.ctmem.emapi.EMBasicXMLInvoker;
import com.bmc.ctmem.emapi.EMXMLInvoker;
import com.bmc.ctmem.emapi.GSRComponent;
import com.bmc.ctmem.emapi.InvokeException;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		
		System.out.println("gsrInvoker.invoke(xmlRequest))...");
		String xmlRequest = "<?xml?>..."; // xml request
		String xmlResponse;
		try{
		    xmlResponse = gsrInvoker.invoke(xmlRequest);
		}
		catch(InvokeException ex){
			System.out.println("gsrInvoker.invoke(" + xmlRequest + ")");
			ex.printStackTrace();
		}
		System.out.println("Done");
		
		System.out.println("EMXMLInvoker.done()...");
		EMXMLInvoker.done();
		System.out.println("Done");
	}

}
