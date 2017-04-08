package sk.ics.upjs.matusikova.arvrp.adapter;

public class Skuska {
	static {
		try {
			System.loadLibrary("OpenCV_3_DLL");
		} catch(java.lang.UnsatisfiedLinkError err) {
			System.out.println("Unable to load MyJNI library");
		}
	
	}
	private native String OpenCV_3_Recognize_Plate(String file);
	
	public static void main(String[] args) {
		Skuska s = new Skuska();
		s.OpenCV_3_Recognize_Plate("C:\\Users\\Freya\\Desktop\\test_4.png");
	}

}
