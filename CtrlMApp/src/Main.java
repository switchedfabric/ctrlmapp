import java.util.List;

public class Main {

	public static void main(String[] args) {
		String[] hosts;
		
		System.out.println("new CtrlMConnection()...");
		CtrlMConnection c = new CtrlMConnection(args);
		System.out.println("new CtrlMConnection() done");
		
		System.out.println("c.dummyOperation()...");
		c.dummyOperation();
		System.out.println("c.dummyOperation() done");

		System.out.println("Getting list of jobs...");
		String[] jobs = c.getJobs();
		System.out.println("Done");

		System.out.println("c.close()...");
		c.close();
		System.out.println("c.close() done");
		
	}

}
