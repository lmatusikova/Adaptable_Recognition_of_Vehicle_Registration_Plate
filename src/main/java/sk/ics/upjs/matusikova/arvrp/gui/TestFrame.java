package sk.ics.upjs.matusikova.arvrp.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import sk.ics.upjs.matusikova.arvrp.adapter.*;
import sk.ics.upjs.matusikova.arvrp.analyzer.Analyzer;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JSeparator;
import java.awt.Font;

public class TestFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JFileChooser fileChooser;
	private BufferedImage bufferedImg;
	private FileNameExtensionFilter filter;
	private List<String> recognitionList;
	private DefaultListModel<String> resultListModel;
	private DefaultListModel<String> photoListModel;

	private String photoDirPath;
	private String frameworkPath;
	private String photoPath;
	private File chooserFile;

	private double percentage;
	private int total_chars;
	private int total_recognized_chars;
	
	private Class adapterClass;
	private Method getLicensePlateMethod;
	private Object adapterObject;
	
	private Target instance;
	private Analyzer analyzer;
	private File originalFile;
	
	private ImageIcon image;
	private Image img;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String getPhotoDirPath() {
		return photoDirPath;
	}

	public void setPhotoDirPath(String photoDirPath) {
		this.photoDirPath = photoDirPath;
	}

	/**
	 * Initialize other.
	 */
	private void init() {
		fileChooser = new JFileChooser();
		recognitionList = new ArrayList<String>();
		percentage = 0.0;
		resultListModel = new DefaultListModel<String>();
		photoListModel = new DefaultListModel<String>();
	}
	
	/**
	 * Create the frame.
	 * Initialize the contents of the frame.
	 */
	public TestFrame() {
		setTitle("Recognition testing");
		setResizable(false);
		init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 726, 555);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(219, 229, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		menuBar = new JMenuBar();
		
		setContentPane(contentPane);
		setJMenuBar(menuBar);
		
		photosLabel = new JLabel("Photos");
		photosLabel.setBackground(Color.LIGHT_GRAY);
		photosLabel.setBorder(null);
		photosLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		photosLabel.setBounds(10, 38, 190, 14);
		contentPane.add(photosLabel);
		
		frameworkLabel = new JLabel("Framework");
		frameworkLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		frameworkLabel.setBounds(210, 38, 72, 14);
		contentPane.add(frameworkLabel);
		
		resultsLabel = new JLabel("Results");
		resultsLabel.setBorder(null);
		resultsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		resultsLabel.setBounds(210, 354, 553, 23);
		contentPane.add(resultsLabel);
		
		choosenFrameworkLabel = new JLabel("");
		choosenFrameworkLabel.setBounds(279, 38, 234, 14);
		contentPane.add(choosenFrameworkLabel);
		
		photoLabel = new JLabel("");
		photoLabel.setOpaque(true);
		photoLabel.setBorder(null);
		photoLabel.setBackground(Color.WHITE);
		photoLabel.setBounds(210, 55, 500, 299);
		contentPane.add(photoLabel);
		
		imageMenu = new JMenu("Image");
		
		loadImagesMenuItem = new JMenuItem("Load images");
		
		frameworkMenu = new JMenu("Framework");
		
		loadAdapterClassMenuItem = new JMenuItem("Load adapter class");
		
		loadFrameworkMenuItem = new JMenuItem("Load framework");
		
		helpMenu = new JMenu("Help");
		
		helpMenuItem = new JMenuItem("Help");
		image = new ImageIcon(TestFrame.class.getResource("/icons/help.png"));
		img = image.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		helpMenuItem.setIcon(new ImageIcon(img));
		
		aboutMenuItem = new JMenuItem("About");
		
		photoList = new JList<String>();
		photoList.setBackground(Color.WHITE);
		
		photoPane = new JScrollPane();
		photoPane.setBackground(Color.WHITE);
		photoPane.setBorder(null);
		photoPane.setBounds(10, 55, 190, 422);
		photoPane.setRowHeaderView(photoList);
		photoPane.setViewportView(photoList);
		contentPane.add(photoPane);
		
		resultsList = new JList<String>();
		resultsList.setBackground(Color.WHITE);
		
		resultPane = new JScrollPane();
		resultPane.setBorder(null);
		resultPane.setBackground(new Color(240, 250, 200));
		resultPane.setBounds(210, 376, 500, 101);
		resultPane.setViewportView(resultsList);
		contentPane.add(resultPane);
		
		fileMenu = new JMenu("File");
		
		loadOriginalFileMenuItem = new JMenuItem("Load original file");
		fileMenu.add(loadOriginalFileMenuItem);
		
		saveAsMenuItem = new JMenuItem("Save as...");
		fileMenu.add(saveAsMenuItem);
		
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);

		imageMenu.add(loadImagesMenuItem);
		helpMenu.add(aboutMenuItem);	
		
		frameworkMenu.add(loadAdapterClassMenuItem);
		frameworkMenu.add(loadFrameworkMenuItem);

		helpMenu.add(helpMenuItem);
			
		menuBar.add(fileMenu);
		menuBar.add(imageMenu);
		menuBar.add(frameworkMenu);
		menuBar.add(helpMenu);
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(new Color(231, 234, 245));
		toolBar.setBounds(0, 0, 720, 30);
		contentPane.add(toolBar);
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(null);
		toolBar.add(panel);
		
		txtButton = new JButton("");
		txtButton.setToolTipText("Load txt file");
		txtButton.setBounds(3, 4, 22, 22);
		//txtButton.setFocusPainted(true);
		txtButton.setContentAreaFilled(false);
		txtButton.setBorderPainted(false);
		txtButton.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/txt.png")));
		loadOriginalFileMenuItem.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/txt.png")));
		panel.add(txtButton);
		
		imageButton = new JButton("");
		imageButton.setToolTipText("Load images");
		imageButton.setBounds(29, 4, 22, 22);
		//imageButton.setFocusPainted(true);
		imageButton.setContentAreaFilled(false);
		imageButton.setBorderPainted(false);
		imageButton.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/image3.png")));
		loadImagesMenuItem.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/image3.png")));
		panel.add(imageButton);
		
		saveAsButton = new JButton("");
		saveAsButton.setToolTipText("Save as...");
		saveAsButton.setBounds(55, 4, 22, 22);
		//saveAsButton.setFocusPainted(true);
		saveAsButton.setContentAreaFilled(false);
		saveAsButton.setBorderPainted(false);
		saveAsButton.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/save3.png")));
		saveAsMenuItem.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/save3.png")));
		panel.add(saveAsButton);
		
		analyzeButton = new JButton("");
		analyzeButton.setToolTipText("Analyse image");
		analyzeButton.setBounds(133, 4, 22, 22);
		//analyzeButton.setFocusPainted(true);
		analyzeButton.setContentAreaFilled(false);
		analyzeButton.setBorderPainted(false);
		analyzeButton.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/play.png")));
		panel.add(analyzeButton);
		
		analyzeAllButton = new JButton("");
		analyzeAllButton.setToolTipText("Analyse all images");
		analyzeAllButton.setBounds(159, 4, 22, 22);
		//analyzeAllButton.setFocusPainted(true);
		analyzeAllButton.setContentAreaFilled(false);
		analyzeAllButton.setBorderPainted(false);
		analyzeAllButton.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/playA2.png")));
		panel.add(analyzeAllButton);
		
		JButton loadJarButton = new JButton("");
		loadJarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				filter = new FileNameExtensionFilter("Jar Files", "jar");
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog((Component) arg0.getSource());	
				chooserFile = fileChooser.getSelectedFile();
				
				if(chooserFile != null) {
					frameworkPath = chooserFile.getAbsolutePath();
					loadLibrary(frameworkPath);
				}
			}
		});
		loadJarButton.setToolTipText("Load jar file");
		loadJarButton.setBounds(81, 4, 22, 22);
		//loadaJarButton.setFocusPainted(true);
		loadJarButton.setContentAreaFilled(false);
		loadJarButton.setBorderPainted(false);
		loadJarButton.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/jar.png")));
		loadFrameworkMenuItem.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/jar.png")));
		panel.add(loadJarButton);
		
		JButton loadClassButton = new JButton("");
		loadClassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				filter = new FileNameExtensionFilter("Java Files", "java");
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog((Component) e.getSource());
				chooserFile = fileChooser.getSelectedFile();
				
				if(chooserFile != null) {
					String classPath = chooserFile.getAbsolutePath();
					loadClass(classPath);	
				}
			}
		});
		loadClassButton.setToolTipText("Load adapter class file");
		loadClassButton.setBounds(107, 4, 22, 22);
		//loadClassButton.setFocusPainted(true);
		loadClassButton.setContentAreaFilled(false);
		loadClassButton.setBorderPainted(false);
		loadClassButton.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/javaClass.png")));
		loadAdapterClassMenuItem.setIcon(new ImageIcon(TestFrame.class.getResource("/icons/javaClass.png")));
		panel.add(loadClassButton);
			
		separator = new JSeparator();
		separator.setBounds(0, 488, 720, 1);
		contentPane.add(separator);
				
		/**
		 * Application actions.
		 */
		txtButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				filter = new FileNameExtensionFilter("Txt Files", "txt");
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog((Component) e.getSource());
				chooserFile = fileChooser.getSelectedFile();
						
				if(chooserFile != null) {
					String pathToFile = chooserFile.getPath();
					originalFile = new File(pathToFile);
				}
			}
		});
				
		saveAsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				filter = new FileNameExtensionFilter("Txt Files", "txt");
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog((Component) e.getSource());					
				chooserFile = fileChooser.getSelectedFile();	
				
				if(chooserFile != null) {
					String pathToFile = chooserFile.getPath();
					writeResultToFile(pathToFile);
				}
			}
		});
				
		imageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!photoListModel.isEmpty()) {
					photoListModel.clear();
					total_chars = 0;
					total_recognized_chars = 0;
					recognitionList.clear();
					resultListModel.clear();
					photoLabel.setIcon(new ImageIcon());		
				}

				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog((Component) e.getSource());
				loadPhotos();
			}
		});
				
		analyzeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long start = System.currentTimeMillis();
				// instance = new TessAdapter();
				instance = new JavaanprAdapter();
				// instance = new PlateRecognizerAdapter();
				//instance = new AspriseOcrAdapter();
				//instance = new CAdapter();
				// instance = new TCRNeurophAdapter();
				// instance = new OpenErpAdapter();
				// instance = new NeurophAdapter();
				String result = instance.getLicensePlate(bufferedImg, photoPath);
				long end = System.currentTimeMillis();
				String resultString = photoList.getSelectedValue() + "   " + result + "   " + (end-start) + " miliseconds";		
				resultListModel.addElement(resultString);
				resultsList.setModel(resultListModel);
						
			}
		});
				
		analyzeAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long start = System.currentTimeMillis();
				int NUMBER_OF_RECOGNIZED = 0;
				int NUMBER_OF_UNRECOGNIZED = 0;

				// instance = new TessAdapter();
				instance = new JavaanprAdapter();
				//instance = new PlateRecognizerAdapter();
				//instance = new TCRNeurophAdapter();
				// instance = new NeurophAdapter();

				Scanner s = null;

					try {
						s = new Scanner(originalFile);
						System.out.println("Original txt file >> " + originalFile.toString());
					} catch (FileNotFoundException ex) {
						Logger.getLogger(TestFrame.class.getName()).log(Level.SEVERE, null, ex);
					}

				for (int i = 0; i < photoListModel.size(); i++) {
					int recognized_chars = 0;
					String photo = getPhotoDirPath() + File.separator + photoListModel.get(i);
					File file = new File(photo);
							
						try {
							bufferedImg = ImageIO.read(file);
						} catch (IOException ex) {
							Logger.getLogger(TestFrame.class.getName()).log(Level.SEVERE, null, ex);
						}
							
					long start2 = System.currentTimeMillis();
					String result = instance.getLicensePlate(bufferedImg, photo);
					long end2 = System.currentTimeMillis();
							
					if ((("UNRECOGNIZED").equals(result))) {
						percentage = 0;
						NUMBER_OF_UNRECOGNIZED++;
						s.next();
						String unr_plate = s.next();
						total_chars = total_chars + unr_plate.length();
					} else {
						s.next();
						String original_plate = s.next();
						total_chars = total_chars + original_plate.length();
						analyzer = new Analyzer(original_plate, result);
						recognized_chars = analyzer.analyze();	
						percentage = (recognized_chars * 100) / original_plate.length();
						total_recognized_chars = total_recognized_chars + recognized_chars;
						
						if (percentage == 100) {
							NUMBER_OF_RECOGNIZED++;
						} else {
							NUMBER_OF_UNRECOGNIZED++;
						}
					}
						
					String resultString = photoListModel.get(i) + "   " + result + "   " + percentage + "%" + "   " + (end2-start2) + " miliseconds";
					recognitionList.add(resultString);
					resultListModel.add(i, resultString);

					if (i == photoListModel.size() - 1) {
						resultsList.setModel(resultListModel);
						long end = System.currentTimeMillis();
						long time = end - start;
						recognitionList.add(
										"Recognized successful in " + time + "miliseconds / " + (time / 1000) + "seconds.");
								recognitionList.add("Recognized: " + NUMBER_OF_RECOGNIZED + " of "
										+ (NUMBER_OF_RECOGNIZED + NUMBER_OF_UNRECOGNIZED) + " -> "
										+ ((NUMBER_OF_RECOGNIZED * 100) / (NUMBER_OF_RECOGNIZED + NUMBER_OF_UNRECOGNIZED))
										+ "%");
								recognitionList.add("Total chars: " + total_chars + ", recognized: " + total_recognized_chars);
					}
				}
			}
		});

		loadOriginalFileMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				filter = new FileNameExtensionFilter("Txt Files", "txt");
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog((Component) arg0.getSource());
				chooserFile = fileChooser.getSelectedFile();
				
				if(chooserFile != null) {
					String pathToFile = chooserFile.getPath();
					originalFile = new File(pathToFile);
				}
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		saveAsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				filter = new FileNameExtensionFilter("Txt Files", "txt");
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog((Component) arg0.getSource());
				
				chooserFile = fileChooser.getSelectedFile();
				if(chooserFile != null) {
					String pathToFile = chooserFile.getPath();
					writeResultToFile(pathToFile);
				}
			}
		});
		
		loadAdapterClassMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				filter = new FileNameExtensionFilter("Java Files", "java");
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog((Component) e.getSource());
				chooserFile = fileChooser.getSelectedFile();
				
				if(chooserFile != null) {
					String classPath = chooserFile.getAbsolutePath();
					loadClass(classPath);	
				}
			}
		});

		loadFrameworkMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				filter = new FileNameExtensionFilter("Jar Files", "jar");
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog((Component) e.getSource());	
				chooserFile = fileChooser.getSelectedFile();
				
				if(chooserFile != null) {
					frameworkPath = chooserFile.getAbsolutePath();
					loadLibrary(frameworkPath);
				}
			}
		});

		loadImagesMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!photoListModel.isEmpty()) {
					photoListModel.clear();
					total_chars = 0;
					total_recognized_chars = 0;
					recognitionList.clear();
					resultListModel.clear();
					photoLabel.setIcon(new ImageIcon());		
				}

				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog((Component) e.getSource());
				loadPhotos();
			}
		});

		photoList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 1) {
					displayPhoto();
				}
			}
		});
		
	}
	

	/**
	 * Other methods.
	 */
	private void loadClass(String classPath) {
		URL myAdapterClass = null;
		System.out.println(classPath);
		
		try {
			myAdapterClass = new URL("file", "", classPath);
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}
			
		URLClassLoader cl = URLClassLoader.newInstance(new URL[]{myAdapterClass});
		
		try {			
			adapterClass = cl.getSystemClassLoader().loadClass("sk.ics.upjs.matusikova.arvrp.adapter.JavaanprAdapter");
			getLicensePlateMethod = adapterClass.getMethod("getLicensePlate", new Class[] {BufferedImage.class, String.class});
			adapterObject = adapterClass.newInstance();
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	private void loadLibrary(String libraryPath) {
		File jar = new File(libraryPath);
		
		try {
            URLClassLoader loader = (java.net.URLClassLoader)ClassLoader.getSystemClassLoader();
            URL url = jar.toURI().toURL();
            System.out.println(url);

            for (java.net.URL it : java.util.Arrays.asList(loader.getURLs())){
                if (it.equals(url)){
                    return;
                }
            }  
            java.lang.reflect.Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{java.net.URL.class});
            System.out.println(method);
            method.setAccessible(true);
            method.invoke(loader, new Object[]{url});
        } catch (final java.lang.NoSuchMethodException | 
            java.lang.IllegalAccessException | 
            java.net.MalformedURLException | 
            java.lang.reflect.InvocationTargetException e2){
        	e2.printStackTrace();
            
        }
	}
	
	private void loadPhotos() {
		int index = 0;

		chooserFile = fileChooser.getSelectedFile();
		if(chooserFile != null) {
		setPhotoDirPath(chooserFile.getPath());
		recognitionList.add(getPhotoDirPath());
		File directory = new File(getPhotoDirPath());

		for (String file : directory.list()) {
			if (file.contains(".jpg") || file.contains(".JPG") || file.contains(".PNG")
					|| file.contains(".jpeg") || file.contains(".png")) {
				photoListModel.add(index, file);
				index++;
			}
		}

		photoList.setModel(photoListModel);
		}
	}
	
	private void displayPhoto() {
		int index = photoList.getSelectedIndex();

		ListModel<String> model = photoList.getModel();
		String photoName = model.getElementAt(index);

		photoPath = getPhotoDirPath() + File.separator + photoName;
		File imageFile = new File(photoPath);

		try {
			bufferedImg = ImageIO.read(imageFile);
		} catch (IOException e) {
			System.out.println("Image cannot be load.");
		}

		ImageIcon image = new ImageIcon(bufferedImg);
		Image img = image.getImage().getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(),
				Image.SCALE_SMOOTH);
		photoLabel.setIcon(new ImageIcon(img));
	}
	
	private void writeResultToFile(String path) {
		PrintWriter pw = null;

		try {
			pw = new PrintWriter(new File(path));

			for (int i = 0; i < recognitionList.size(); i++) {
				pw.println(recognitionList.get(i));
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger(TestFrame.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	// Variables declaration - do not modify 
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JLabel frameworkLabel;
	private JLabel photosLabel;
	private JLabel resultsLabel;
	private JLabel choosenFrameworkLabel;
	private JLabel photoLabel;
	private JMenu imageMenu;
	private JMenuItem loadImagesMenuItem;
	private JMenuItem exitMenuItem;
	private JMenu frameworkMenu;
	private JMenuItem loadAdapterClassMenuItem;
	private JMenuItem loadFrameworkMenuItem;
	private JMenu helpMenu;
	private JMenuItem helpMenuItem;
	private JMenuItem aboutMenuItem;
	private JScrollPane photoPane;
	private JList<String> photoList;
	private JScrollPane resultPane;
	private JList<String> resultsList;
	private JMenuItem saveAsMenuItem;
	private JMenuItem loadOriginalFileMenuItem;
	private JMenu fileMenu;
	private JSeparator separator;
	private JButton txtButton;
	private JButton imageButton;
	private JButton saveAsButton;
	private JButton analyzeButton;
	private JButton analyzeAllButton;
	private JPanel panel;
	private JToolBar toolBar;
}
