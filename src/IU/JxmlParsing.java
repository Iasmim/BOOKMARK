package IU;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import bookmark.convert.ExcelConverterConfig;
import bookmark.convert.ExcelToListConverter;
import bookmark.convert.ExcelToListConverter.Item;
import bookmark.convert.ExcelToListConverter.ItemResult;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JPopupMenu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;

public class JxmlParsing extends JPanel {
	private JTable table;
	private JTable table_1;
	private JRadioButton rdbtnAll, rdbtnDiff;

	DefaultTableModel ref;
	DefaultTableModel cs;
	ArrayList<ItemResult> result;
	ExcelConverterConfig config;

	/**
	 * Create the panel.
	 */
	public JxmlParsing() {

		config = new ExcelConverterConfig();

		result = new ArrayList<ItemResult>();

		setLayout(new BorderLayout(0, 0));
		setBounds(0, 0, 700, 352);

		final JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		JPanel panelLabel = new JPanel();

		final JLabel lblCustomeLabel = new JLabel("");
		lblCustomeLabel.setHorizontalAlignment(SwingConstants.LEFT);

		final JLabel lblModelLabel = new JLabel("");
		lblModelLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GroupLayout gl_panelLabel = new GroupLayout(panelLabel);
		gl_panelLabel.setHorizontalGroup(
			gl_panelLabel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLabel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCustomeLabel, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
					.addGap(245)
					.addComponent(lblModelLabel, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		gl_panelLabel.setVerticalGroup(
			gl_panelLabel.createParallelGroup(Alignment.LEADING)
				.addComponent(lblModelLabel, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
				.addGroup(gl_panelLabel.createSequentialGroup()
					.addGap(5)
					.addComponent(lblCustomeLabel, GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE))
		);
		panelLabel.setLayout(gl_panelLabel);

		JPanel panelRun = new JPanel();

		JButton btnRun = new JButton("Run");

		final JLabel lblResult = new JLabel("");
		lblResult.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		
				JButton btnPoP = new JButton();
				btnPoP.setText("Click to select files");
				
						btnPoP.addActionListener(new java.awt.event.ActionListener() {
							@Override
							public void actionPerformed(java.awt.event.ActionEvent evt) {
				
								String userhome = System.getProperty("user.home");
								JFileChooser file_customer = new JFileChooser(userhome +"\\Downloads");
								
								
								file_customer.setFileFilter(new FileNameExtensionFilter("Planilhas do excel", "xls"));
								file_customer.setAcceptAllFileFilterUsed(false);
								int returnVal = file_customer.showOpenDialog(panel);
				
								if (returnVal == JFileChooser.APPROVE_OPTION) {
				
									lblCustomeLabel
											.setText(file_customer.getSelectedFile().getName()
													.length() > 15 ? file_customer
													.getSelectedFile().getName()
													.substring(0, 15)
													+ "..." : file_customer.getSelectedFile()
													.getName());
				
									File arq = file_customer.getSelectedFile()
											.getAbsoluteFile();
									ExcelConverterConfig.sourceFile = arq.getPath();
				
									JOptionPane.showMessageDialog(panel,
											"Arquivo Customer Spec Escolhido");
								}
								
								String userhomeModel = System.getProperty("user.home");
						        JFileChooser file_model = new JFileChooser(userhomeModel +"\\Downloads");
						        
								file_model.setFileFilter(new FileNameExtensionFilter("Planilhas do excel", "xls"));
								file_model.setAcceptAllFileFilterUsed(false);
								int returnValmodel = file_model.showOpenDialog(panel);
				
								if (returnValmodel == JFileChooser.APPROVE_OPTION) {
				
									lblModelLabel.setText(file_model.getSelectedFile()
											.getName().length() > 15 ? file_model
											.getSelectedFile().getName().substring(0, 15)
											+ "..." : file_model.getSelectedFile().getName());
				
									File arq = file_model.getSelectedFile().getAbsoluteFile();
									ExcelConverterConfig.sourceFileModel = arq.getPath();
				
									JOptionPane.showMessageDialog(panel,
											"Arquivo Model Spec Escolhido");
								}
							}
						});
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				removeRows(cs, ref, lblResult);
				
				ExcelConverterConfig.sourceFileModel = null;
				ExcelConverterConfig.sourceFile = null;
				
				lblCustomeLabel.setText("");
				lblModelLabel.setText("");
				
			}
		});
		
		
		
		GroupLayout gl_panelRun = new GroupLayout(panelRun);
		gl_panelRun.setHorizontalGroup(
			gl_panelRun.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRun.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnPoP, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblResult, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(55, Short.MAX_VALUE))
		);
		gl_panelRun.setVerticalGroup(
			gl_panelRun.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRun.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelRun.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnPoP, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblResult, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnRun, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnReset))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelRun.setLayout(gl_panelRun);
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			
				String valid = config.valid();
				if (valid != null) {
					System.out.println(valid);
					return;
				}

				String validModel = config.validModel();
				if (validModel != null) {
					System.out.println(validModel);
					return;
				}

				try {
					result = ExcelToListConverter.convert(config);
				} catch (InvalidFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				cs = new DefaultTableModel(0, 0);
				String header[] = new String[] { "Main Category",
						"Sub Category", "Field", "Value", "color" };

				cs.setColumnIdentifiers(header);
				table.setModel(cs);

				table.getColumn("color").setWidth(0);
				table.getColumn("color").setMinWidth(0);
				table.getColumn("color").setMaxWidth(0);

				table.getColumn("Main Category").setWidth(0);
				table.getColumn("Main Category").setMinWidth(0);
				table.getColumn("Main Category").setMaxWidth(0);

				ref = new DefaultTableModel(0, 0);
				String headerref[] = new String[] { "Main Category",
						"Sub Category", "Field", "Value", "color" };

				ref.setColumnIdentifiers(headerref);
				table_1.setModel(ref);

				table_1.getColumn("Main Category").setWidth(0);
				table_1.getColumn("Main Category").setMinWidth(0);
				table_1.getColumn("Main Category").setMaxWidth(0);

				table_1.getColumn("color").setWidth(0);
				table_1.getColumn("color").setMinWidth(0);
				table_1.getColumn("color").setMaxWidth(0);

				addDataLine(cs, ref, lblResult);

			}

		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(panelRun, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(panelLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addComponent(panelRun, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panelLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel);

		final JPanel panelTables = new JPanel();
		add(panelTables, BorderLayout.CENTER);
		panelTables.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPaneTable1 = new JScrollPane();
		panelTables.add(scrollPaneTable1);

		table = new JTable();
		table.setBounds(10, 25, 385, 358);
		scrollPaneTable1.setViewportView(table);
		panel.setVisible(true);

		JScrollPane scrollPaneTable2 = new JScrollPane();
		panelTables.add(scrollPaneTable2);
		table_1 = new JTable();
		table_1.setBounds(423, 25, 385, 358);
		scrollPaneTable2.setViewportView(table_1);

		final TableCellRenderer renderRef = table_1
				.getDefaultRenderer(Object.class);
		table_1.setDefaultRenderer(Object.class, new TableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component c = renderRef.getTableCellRendererComponent(table,
						value, isSelected, hasFocus, row, column);

				if (table.getModel().getValueAt(row, 4).equals(1)) {
					c.setForeground(Color.RED);

				} else
					c.setForeground(Color.BLACK);
				return c;
			}
		});

		final TableCellRenderer renderCz = table
				.getDefaultRenderer(Object.class);
		table.setDefaultRenderer(Object.class, new TableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component c = renderCz.getTableCellRendererComponent(table,
						value, isSelected, hasFocus, row, column);
				if (table.getModel().getValueAt(row, 4).equals(1)) {
					c.setForeground(Color.RED);
				
				} else
					c.setForeground(Color.BLACK);

				return c;

			}
		});

	}

	private void addDataLine(DefaultTableModel cs, DefaultTableModel ref,
			JLabel label) {

		removeRows(cs, ref, label);
		label.setForeground(Color.GREEN);
		label.setText("PASS");
		for (ItemResult i : result) {
			Vector<Object> datacs = new Vector<Object>();
			datacs.add(i.getMain_Category());
			datacs.add(i.getSub_Category());
			datacs.add(i.getField());
			datacs.add(i.getValue());
			datacs.add(i.Color);
			cs.addRow(datacs);

			Vector<Object> dataref = new Vector<Object>();
			dataref.add(i.getMain_Category_Model());
			dataref.add(i.getSub_Category_Model());
			dataref.add(i.getField_Model());
			dataref.add(i.getValue_Model());
			dataref.add(i.Color);
			ref.addRow(dataref);

			if (i.Color == 1) {
				label.setForeground(Color.RED);
				label.setText("FAIL");
			}
		}
	}

	private void removeRows(DefaultTableModel cs, DefaultTableModel ref,
			JLabel label) {
		
		label.setForeground(Color.BLACK);
		label.setText("");
		
		int countcs = cs.getRowCount();
		if (countcs > 0) {
			for (int i = 0; i < countcs; i++) {
				cs.removeRow(0);
			}

		}

		int countref = ref.getRowCount();
		if (countref > 0) {
			for (int i = 0; i < countref; i++) {
				ref.removeRow(0);
			}

		}
	}
}