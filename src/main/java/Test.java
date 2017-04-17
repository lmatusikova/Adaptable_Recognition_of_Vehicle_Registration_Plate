public class Test {

	public native String RecognizePlate(String path);
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("java.library.path"));
		System.loadLibrary("OpenCV_3_DLL");
		Test cpp = new Test();
	    System.out.println(cpp.RecognizePlate("JAVA"));
	
	}
}
