package arvrp.adapter;

import com.ragerant.platerecognizer.PlateRecognizer;
import java.awt.image.BufferedImage;
import java.io.File;

public class PlateRecognizerAdapter implements Target{

    public String getLicensePlate(BufferedImage image, String path) {
        File img = new File(path);
        String recognizedText = "";
        recognizedText = PlateRecognizer.recognize(img);
        if(recognizedText == null || recognizedText.equals("")) {
            recognizedText = "UNRECOGNIZED";
        } 
        
        return recognizedText;

    }

    public String getFrameworkName() {
    	return "PlateRecognizer";
    }

    public String getFrameworkDescription() {
    	return "Recognition plate library";
    }
}
