package arvrp.analyzer;

import org.junit.Test;

import arvrp.analyzer.Analyzer;
import junit.framework.Assert;

public class AnalyzerTest {

	private Analyzer analyzer;
	
	@Test
	public void test() {
		analyzer = new Analyzer("PO123KL", "PP127KJ");
		int result = analyzer.analyse();
		Assert.assertEquals(4, result);
	}

}
