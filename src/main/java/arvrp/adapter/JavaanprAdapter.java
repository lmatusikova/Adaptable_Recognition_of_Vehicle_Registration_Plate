package arvrp.adapter;

import java.awt.image.BufferedImage;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;

public class JavaanprAdapter implements Target {

	/**
	 * Method for obtaining characters from license plate
	 * 
	 * @param image
	 *            given image
	 * @param path
	 *            path to the given image
	 * @return recognized characters
	 */
	public String getLicensePlate(BufferedImage image, String path) {
		String recognizedText = "";
		Intelligence intelligence = null;
		try {
			intelligence = new Intelligence(false);
			recognizedText = intelligence.recognize(new CarSnapshot(image));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Recognized exception.");
		}

		if (recognizedText == null || recognizedText.equals("")) {
			recognizedText = "UNRECOGNIZED";
		}
		return recognizedText;
	}

	/**
	 * Get framework name
	 * 
	 * @return framework name
	 */
	public String getFrameworkName() {
		return "Javaanpr";
	}

	/**
	 * Get framework description
	 * 
	 * @return framework description
	 */
	public String getFrameworkDescription() {
		return "Recognition plate library";
	}
}
