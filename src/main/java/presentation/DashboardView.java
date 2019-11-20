package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;


/**
 * This class defines the dashboard view that the user
 * sees from the moment he opens the application.
 * @author Bogdan Paun
 */
public class DashboardView extends JFrame {

	private static final long serialVersionUID = -2483285235784924600L;

	private static final String TAHOMA = "Tahoma";
	private static final int WINDOW_WIDTH = 1150;
	private static final int WINDOW_HEIGHT = 670;

	private JTable table;

	private JLabel lblErrorMessage;
	private JLabel lblSuccessMessage;

	private JLabel[] labels = new JLabel[8];
	private JTextField[] textFields = new JTextField[8];

	private JButton btnSave;
	private JButton btnCreate;
	private JButton btnEdit;
	private JButton btnRemove;
	private JButton btnCancel;

	private JComboBox<String> categoryComboBox;

	public DashboardView(Dimension screenSize) {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds((int) (screenSize.getWidth() / 2.0) - (int) (WINDOW_WIDTH / 2.0),
				(int) (screenSize.getHeight() / 2.0) - (int) (WINDOW_HEIGHT / 2.0), WINDOW_WIDTH, WINDOW_HEIGHT);
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setTitle("Order management app");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setShowVerticalLines(true);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(52, 98, 770, 481);
		getContentPane().add(scrollPane);

		btnCreate = new JButton("Create");
		btnCreate.setBounds(118, 45, 97, 25);
		getContentPane().add(btnCreate);

		btnEdit = new JButton("Edit");
		btnEdit.setBounds(380, 45, 97, 25);
		getContentPane().add(btnEdit);

		btnRemove = new JButton("Remove");
		btnRemove.setBounds(637, 45, 97, 25);
		getContentPane().add(btnRemove);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(854, 39, 2, 561);
		getContentPane().add(separator);

		for (int i = 0; i < 8; i++) {
			labels[i] = new JLabel("Field " + i);
			labels[i].setFont(new Font(TAHOMA, Font.BOLD, 13));
			labels[i].setBounds(879, 134 + 46 * i, 100, 30);
			getContentPane().add(labels[i]);
		}

		for (int i = 0; i < 8; i++) {
			textFields[i] = new JTextField();
			textFields[i].setBounds(975, 138 + 46 * i, 140, 22);
			getContentPane().add(textFields[i]);
			textFields[i].setColumns(10);
		}

		btnSave = new JButton("Save");
		btnSave.setForeground(new Color(0, 128, 0));
		btnSave.setBounds(950, 518, 97, 25);
		getContentPane().add(btnSave);

		categoryComboBox = new JComboBox<>();
		categoryComboBox.addItem("Orders");
		categoryComboBox.addItem("Clients");
		categoryComboBox.addItem("Products");
		categoryComboBox.addItem("Addresses");
		categoryComboBox.setToolTipText("Category");
		categoryComboBox.setBounds(938, 45, 135, 25);
		getContentPane().add(categoryComboBox);

		lblErrorMessage = new JLabel("Error message");
		lblErrorMessage.setForeground(Color.RED);
		lblErrorMessage.setFont(new Font(TAHOMA, Font.BOLD, 14));
		lblErrorMessage.setBounds(50, 592, 554, 29);
		getContentPane().add(lblErrorMessage);
		lblErrorMessage.setVisible(false);

		lblSuccessMessage = new JLabel("Success message");
		lblSuccessMessage.setForeground(Color.GREEN);
		lblSuccessMessage.setFont(new Font(TAHOMA, Font.BOLD, 14));
		lblSuccessMessage.setBounds(50, 592, 554, 29);
		getContentPane().add(lblSuccessMessage);
		lblSuccessMessage.setVisible(false);

		btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.RED);
		btnCancel.setBounds(950, 570, 97, 25);
		getContentPane().add(btnCancel);
		btnCancel.setVisible(false);
	}

	public String[] getTableRow() {
		String[] rowData = new String[table.getColumnCount()];
		if (table.getSelectedRow() == -1) {
			return new String[0];
		}
		for (int i = 0; i < rowData.length; i++) {
			rowData[i] = table.getValueAt(table.getSelectedRow(), i).toString();
		}
		return rowData;
	}

	public void addTableActionListener(ListSelectionListener listSelectionListener) {
		table.getSelectionModel().addListSelectionListener(listSelectionListener);
	}

	public String getSelectedCategory() {
		return (String) categoryComboBox.getSelectedItem();
	}

	public void setTextFieldIVisible(int index, boolean flag) {
		textFields[index].setVisible(flag);
	}

	public void setTextFieldIText(int index, String text) {
		textFields[index].setText(text);
	}

	public String getTextFieldIText(int index) {
		return textFields[index].getText();
	}

	public void setFieldILabelVisible(int index, boolean flag) {
		labels[index].setVisible(flag);
	}

	public void setFieldILabelText(int index, String text) {
		labels[index].setText(text);
	}

	public void setSaveButtonVisible(boolean flag) {
		btnSave.setVisible(flag);
	}

	public void setCancelButtonVisible(boolean flag) {
		btnCancel.setVisible(flag);
	}

	public void addCategoryComboBoxListener(ActionListener actionListener) {
		categoryComboBox.addActionListener(actionListener);
	}

	public void addCreateButtonActionListener(ActionListener actionListener) {
		btnCreate.addActionListener(actionListener);
	}

	public void addEditButtonActionListener(ActionListener actionListener) {
		btnEdit.addActionListener(actionListener);
	}

	public void addDeleteButtonActionListener(ActionListener actionListener) {
		btnRemove.addActionListener(actionListener);
	}

	public void addSaveButtonActionListener(ActionListener actionListener) {
		btnSave.addActionListener(actionListener);
	}

	public void addCancelButtonActionListener(ActionListener actionListener) {
		btnCancel.addActionListener(actionListener);
	}

	public void setTableModel(List<Object> objects) {
		table.setModel(createTable(objects).getModel());
	}

	private JTable createTable(List<Object> objects) {
		if (objects.isEmpty()) {
			return new JTable();
		}

		String[] header = getFieldsNames(objects.get(0));
		Object[][] data = getData(objects);
		DefaultTableModel tableModel = new DefaultTableModel(data, header) {
			private static final long serialVersionUID = 852089186808579205L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		return new JTable(tableModel);
	}

	private Object[][] getData(List<Object> objects) {

		int fieldsCount = objects.get(0).getClass().getDeclaredFields().length;
		Object[][] data = new Object[objects.size()][fieldsCount];

		for (int i = 0; i < objects.size(); i++) {
			Object obj = objects.get(i);
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {
				try {
					fields[j].setAccessible(true);
					data[i][j] = fields[j].get(obj);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}

	private String[] getFieldsNames(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	public void displayInputPanel(int count) {
		for (int i = 0; i < 8; i++) {
			if (i < count) {
				labels[i].setVisible(true);
				textFields[i].setVisible(true);
			} else {
				labels[i].setVisible(false);
				textFields[i].setVisible(false);
			}
		}
	}

	public void setTextFieldsEditable(boolean flag) {
		for (int i = 0; i < 8; i++) {
			textFields[i].setEditable(flag);
		}
	}

	public String[] getTextFields(int count) {
		String[] fields = new String[count];
		for (int i = 0; i < count; i++) {
			fields[i] = textFields[i].getText();
		}
		return fields;
	}

	public void displayErrorMessage(String message) {
		Runnable runnable = () -> {
			lblErrorMessage.setText(message);
			lblErrorMessage.setVisible(true);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				lblErrorMessage.setVisible(false);
			}
		};

		new Thread(runnable).start();
	}

	public void displaySuccessMessage(String message) {
		Runnable runnable = () -> {
			lblSuccessMessage.setText(message);
			lblSuccessMessage.setVisible(true);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				lblSuccessMessage.setVisible(false);
			}
		};

		new Thread(runnable).start();
	}

	public boolean displayConfirmation(String message) {
		int option = JOptionPane.showConfirmDialog(this, message, "Confirm your selection",
				JOptionPane.OK_CANCEL_OPTION);
		return option == JOptionPane.OK_OPTION;
	}
}
