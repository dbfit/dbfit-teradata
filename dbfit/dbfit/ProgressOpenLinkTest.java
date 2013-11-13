package dbfit;

import dbfit.environment.*;;
public class ProgressOpenLinkTest extends DatabaseTest {
	public ProgressOpenLinkTest(){
		super(new TeradataEnvironment());
		System.out.println("ProgressOpenLinkTest: ProgressOpenLinkTest()");
	}
	public void dbfitDotTeradataTest() {
		// required by fitnesse release 20080812
	}
}
