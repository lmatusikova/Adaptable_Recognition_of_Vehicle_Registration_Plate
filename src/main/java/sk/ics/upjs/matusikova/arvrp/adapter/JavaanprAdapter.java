package sk.ics.upjs.matusikova.arvrp.adapter;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            Logger.getLogger(JavaanprAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
