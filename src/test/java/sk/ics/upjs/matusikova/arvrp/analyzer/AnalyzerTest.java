package sk.ics.upjs.matusikova.arvrp.analyzer;

import org.junit.Test;
import junit.framework.Assert;

public class AnalyzerTest {

	private Analyzer analyzer;
	
	@Test
	public void test() {
		analyzer = new Analyzer("PO123KL", "PP127KJ");
		int result = analyzer.analyze();
		Assert.assertEquals(4, result);
	}

}
