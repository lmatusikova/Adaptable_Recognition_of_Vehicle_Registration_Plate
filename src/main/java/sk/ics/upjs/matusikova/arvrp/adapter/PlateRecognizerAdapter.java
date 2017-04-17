package sk.ics.upjs.matusikova.arvrp.adapter;

import com.ragerant.platerecognizer.PlateRecognizer;
import java.awt.image.BufferedImage;
import java.io.File;

public class PlateRecognizerAdapter implements Target{

    public String getLicensePlate(BufferedImage image, String path) {
        File img = new File(path);
        String recognizedText = "";
        PlateRecognizer p = new PlateRecognizer();
        recognizedText = PlateRecognizer.recognize(img);
        if(recognizedText == null || recognizedText.equals("")) {
            recognizedText = "UNRECOGNIZED";
        } 
        
        return recognizedText;

    }

    public String getFrameworkName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getFrameworkDescription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }  
}
