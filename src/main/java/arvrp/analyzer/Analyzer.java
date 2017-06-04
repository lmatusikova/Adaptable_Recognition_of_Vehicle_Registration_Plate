package arvrp.analyzer;

public class Analyzer {
	private String original;
	private String recognized;

	/**
	 * Constructor
	 * 
	 * @param original
	 *            original characters from license plate
	 * @param recognized
	 *            recognized characters from license plate
	 */
	public Analyzer(String original, String recognized) {
		this.original = original;
		this.recognized = recognized;
	}

	/**
	 * Method for analyse recognition result
	 * 
	 * @return number of correctly recognized characters
	 */
	public int analyse() {
		int[][] result = new int[original.length() + 1][recognized.length() + 1];

		for (int i = 1; i < original.length() + 1; i++) {
			for (int j = 1; j < recognized.length() + 1; j++) {
				if (original.charAt(i - 1) == recognized.charAt(j - 1)) {
					result[i][j] = 1 + result[i - 1][j - 1];
				} else {
					result[i][j] = Math.max(result[i - 1][j], result[i][j - 1]);
				}
			}
		}
		return result[original.length()][recognized.length()];
	}
}
