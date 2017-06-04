package arvrp.gui;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.AbstractAction;

import arvrp.adapter.Target;
import arvrp.gui.TestFrame;

public class Action extends AbstractAction {

	private static final long serialVersionUID = 5017720778730194098L;
	private Class<?> adapterClass;
	private Target instance = null;
	private TestFrame frame;
	private String text;
	private ClassLoader cl;

	/**
	 * Constructor
	 * 
	 * @param text
	 *            name of given adapter
	 * @param frame
	 *            test frame
	 */
	public Action(String text, TestFrame frame) {
		super(text);
		this.text = text;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		URL location = TestFrame.class.getProtectionDomain().getCodeSource().getLocation();
		frame.instance = loadClass(location, text);
	}

	/**
	 * Load adapter class
	 * 
	 * @param url
	 *            url to adapter class file
	 * @param name
	 *            name of adapter class
	 * @return adapter object
	 */
	private Target loadClass(URL url, String name) {
		URL[] urls = new URL[] { url };
		cl = new URLClassLoader(urls);

		try {
			adapterClass = cl.loadClass("arvrp.adapter." + name);
			instance = (Target) adapterClass.newInstance();
			frame.choosenFrameworkLabel.setText(name);
			frame.resultListModel.clear();

		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SecurityException e1) {
			e1.printStackTrace();
		}

		return instance;
	}
}
