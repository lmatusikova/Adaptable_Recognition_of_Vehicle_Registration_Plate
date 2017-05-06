package arvrp.gui;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.AbstractAction;

import arvrp.adapter.Target;
import arvrp.gui.TestFrame;

public class Action extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5017720778730194098L;
	private Class<?> adapterClass;
	private Target instance = null;
	private TestFrame frame;
	private String text;
	private ClassLoader cl;
	
	public Action(String text, TestFrame frame)
    {
        super(text);
        this.text = text;
        this.frame = frame;
    }
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		URL location = TestFrame.class.getProtectionDomain().getCodeSource().getLocation();
		frame.instance = loadClass(location, text);
	}
	
	private Target loadClass(URL url, String name) {
	    URL[] urls = new URL[]{url};
		cl = new URLClassLoader(urls);

		try {			
			adapterClass = cl.loadClass("arvrp.adapter."+name);
			instance = (Target) adapterClass.newInstance();
			frame.choosenFrameworkLabel.setText(name);
			frame.resultListModel.clear();
			
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SecurityException e1) {
			e1.printStackTrace();
		}

		return instance;
	}
	
	public static synchronized void loadLibrary(java.io.File jar) {
        try {
            /*We are using reflection here to circumvent encapsulation; addURL is not public*/
            java.net.URLClassLoader loader = (java.net.URLClassLoader)ClassLoader.getSystemClassLoader();
            java.net.URL url = jar.toURI().toURL();
            /*Disallow if already loaded*/
            for (java.net.URL it : java.util.Arrays.asList(loader.getURLs())){
                if (it.equals(url)){
                    return;
                }
            }
            java.lang.reflect.Method method = java.net.URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{java.net.URL.class});
            method.setAccessible(true); /*promote the method to public access*/
            method.invoke(loader, new Object[]{url});
        } catch (final java.lang.NoSuchMethodException | 
            java.lang.IllegalAccessException | 
            java.net.MalformedURLException | 
            java.lang.reflect.InvocationTargetException e){
            System.out.println("Daco sa posralo");
        }
    }
	

}
