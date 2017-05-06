package arvrp.adapter;

import java.awt.image.BufferedImage;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;

public class JavaanprAdapter implements Target {
    
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
        
        if(recognizedText == null || recognizedText.equals("")) {
            recognizedText = "UNRECOGNIZED";
        } 
        return recognizedText;
    }

    public String getFrameworkName() {
       return "Javaanpr";
    }

    public String getFrameworkDescription() {
       return "Recognition plate library";
    } 
}
