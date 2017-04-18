package IU;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class MainGUI {

	private JFrame frame;
	private JScrollPane scrollPaneAll;
	private JTabbedPane tabbedPane;
	private JRadioButton rdbtnAll, rdbtnDiff;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 * @throws BadLocationException
	 */
	public MainGUI() throws BadLocationException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 * @throws BadLocationException
	 */
	private void initialize() throws BadLocationException, IOException {
		frame = new JFrame();
		frame.setBounds(0, 0, 1026, 590);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		scrollPaneAll = new JScrollPane();
		frame.getContentPane().add(scrollPaneAll, BorderLayout.CENTER);

		tabbedPane = new JTabbedPane();
		scrollPaneAll.setViewportView(tabbedPane);

		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Web BookMarks", null, panel_4, null);
		panel_4.setLayout(new BorderLayout(0, 0));
		JxmlParsing jxmlParsing = new JxmlParsing();
		panel_4.add(jxmlParsing, BorderLayout.CENTER);

		rdbtnAll = new JRadioButton();

		rdbtnDiff = new JRadioButton();

	}

}
