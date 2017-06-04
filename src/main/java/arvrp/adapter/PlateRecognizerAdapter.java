package arvrp.adapter;

import com.ragerant.platerecognizer.PlateRecognizer;
import java.awt.image.BufferedImage;
import java.io.File;

public class PlateRecognizerAdapter implements Target {

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
		File img = new File(path);
		String recognizedText = "";
		recognizedText = PlateRecognizer.recognize(img);
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
		return "PlateRecognizer";
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
