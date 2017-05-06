package arvrp.adapter;

import java.awt.image.BufferedImage;

public class OpenCV3PlateRecognition implements Target {
	public native String RecognizePlate(String path);
	
	public String getLicensePlate(BufferedImage image, String path) {
		System.loadLibrary("Recognition");
		String recognizedText = "";
		recognizedText = RecognizePlate(path);
		if(recognizedText == null || recognizedText.equals("")) {
            recognizedText = "UNRECOGNIZED";
        }
		
		return recognizedText;
	}
	
	public String getFrameworkName() {
		return "OpenCV 3 License Plate Recognition";
	}

	public String getFrameworkDescription() {
		return "Recognition plate library";
	}
	
}
