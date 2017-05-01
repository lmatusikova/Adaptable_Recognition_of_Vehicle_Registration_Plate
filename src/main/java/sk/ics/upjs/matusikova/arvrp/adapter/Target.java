package sk.ics.upjs.matusikova.arvrp.adapter;

import java.awt.image.BufferedImage;

public interface Target {
    public String getLicensePlate(BufferedImage image, String path);
	
	public String getFrameworkName();
	
	public String getFrameworkDescription();
	
}
