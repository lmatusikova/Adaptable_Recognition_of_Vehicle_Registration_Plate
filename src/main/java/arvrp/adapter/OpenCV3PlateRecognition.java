package arvrp.adapter;

import java.awt.image.BufferedImage;

public class OpenCV3PlateRecognition implements Target {
	/**
	 * Native method for obtaining characters from license plate
	 * 
	 * @param path
	 *            path to image
	 * @return recognized characters
	 */
	public native String RecognizePlate(String path);

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
		System.loadLibrary("Recognition");
		String recognizedText = "";
		recognizedText = RecognizePlate(path);
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
		return "OpenCV 3 License Plate Recognition";
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
