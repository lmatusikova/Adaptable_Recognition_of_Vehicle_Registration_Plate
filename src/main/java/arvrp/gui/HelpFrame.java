package arvrp.gui;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URL;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

public class HelpFrame extends JFrame {

	private static final long serialVersionUID = 2302078282580481904L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelpFrame frame = new HelpFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HelpFrame() {
		setResizable(false);
		setTitle("Help");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 494, 362);
		contentPane.add(scrollPane);
		editorPane = new JEditorPane();

		editorPane.setEditable(false);
		URL helpURL = HelpFrame.class.getResource("/help.html");

		try {

			editorPane.setPage(helpURL);

		} catch (IOException e) {
			System.err.println("Attempted to read a bad URL: " + helpURL);
		}

		scrollPane.setViewportView(editorPane);
	}

	// Variables declaration - do not modify
	private JScrollPane scrollPane;
	private JEditorPane editorPane;
	// End of variables declaration
}
