package arvrp.analyzer;

public class Analyzer {

	private String original;
	private String rozpoznane;
	
	public Analyzer(String original, String rozpoznane) {
		this.original = original;
		this.rozpoznane = rozpoznane;
	}
	
	public int analyze() {
		int[][] result = new int[original.length()+1][rozpoznane.length()+1];
		
		for(int i = 1; i < original.length()+1; i++) {
			for(int j = 1; j < rozpoznane.length()+1; j++) {
				if(original.charAt(i-1) == rozpoznane.charAt(j-1)){
					result[i][j] = 1 + result[i-1][j-1];
				} else {
					result[i][j] = Math.max(result[i-1][j], result[i][j-1]);
				}
			}
		}	
		return result[original.length()][rozpoznane.length()];
	}		
}
