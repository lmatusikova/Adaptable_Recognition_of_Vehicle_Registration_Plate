package sk.ics.upjs.matusikova.arvrp.gui;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.net.URLClassLoader;
import sk.ics.upjs.matusikova.arvrp.gui.TestFrame;
import javax.swing.AbstractAction;
import sk.ics.upjs.matusikova.arvrp.adapter.Target;

public class Action extends AbstractAction{

	private Class adapterClass;
	private Target instance = null;
	private TestFrame frame;
	private String text;
	
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
		ClassLoader cl = new URLClassLoader(urls);

		try {			
			adapterClass = cl.loadClass("sk.ics.upjs.matusikova.arvrp.adapter."+name);
			instance = (Target) adapterClass.newInstance();
			System.out.println(instance);
			System.out.println(instance.toString());
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SecurityException e1) {
			e1.printStackTrace();
		}

		return instance;
	}
	

}
