package sk.ics.upjs.matusikova.arvrp.adapter;
import java.awt.image.BufferedImage;
import java.io.File;

import org.neuroph.core.NeuralNetwork;
import org.tcrneuroph.neuroph.RecognizeImage;

public class TCRNeurophAdapter implements Target {

	private org.neuroph.core.NeuralNetwork nnet2;
	private NeuralNetwork load;
	
	@Override
	public String getLicensePlate(BufferedImage image, String path) {
		load = NeuralNetwork.load("C:/Users/Freya/workspace/ARVRP/lib/TCRNeuroph/dist/neuralNetwork.nnet");
		System.out.println(load);	
		RecognizeImage r = new RecognizeImage();
	//	org.tcrneuroph.neuroph.RecognizeImage.nnet = load;
		String result = r.recognize(image);
		return result;
	}

	@Override
	public String getFrameworkName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFrameworkDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}
}
