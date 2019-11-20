package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;

/**
 * This class defines the view for the order placing step.
 * @author Bogdan Paun
 */
public class OrdersView extends JFrame {
	private static final long serialVersionUID = -3624898878523737778L;

	private static final String TAHOMA = "Tahoma";
	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 650;

	private JTable clientsTable;
	private JTable productsTable;
	private JTextField quantityTextField;
	private JLabel lblClientsName;
	private JLabel lblClientsEmail;
	private JLabel lblProductsName;
	private JButton btnPlaceOrder;
	private JButton btnCancel;
	private JLabel lblMessage;

	public OrdersView(Dimension screenSize) {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds((int) (screenSize.getWidth() / 2.0) - (int) (WINDOW_WIDTH / 2.0),
				(int) (screenSize.getHeight() / 2.0) - (int) (WINDOW_HEIGHT / 2.0), WINDOW_WIDTH, WINDOW_HEIGHT);
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setTitle("Place a new order");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		JLabel lblClients = new JLabel("Client");
		lblClients.setHorizontalAlignment(SwingConstants.LEFT);
		lblClients.setFont(new Font(TAHOMA, Font.BOLD, 16));
		lblClients.setBounds(36, 25, 80, 30);
		getContentPane().add(lblClients);

		lblClientsName = new JLabel("");
		lblClientsName.setBounds(100, 25, 211, 30);
		getContentPane().add(lblClientsName);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font(TAHOMA, Font.BOLD, 13));
		lblEmail.setBounds(36, 59, 70, 30);
		getContentPane().add(lblEmail);

		lblClientsEmail = new JLabel("");
		lblClientsEmail.setBounds(100, 59, 211, 30);
		getContentPane().add(lblClientsEmail);

		JLabel lblProduct = new JLabel("Product");
		lblProduct.setHorizontalAlignment(SwingConstants.LEFT);
		lblProduct.setFont(new Font(TAHOMA, Font.BOLD, 16));
		lblProduct.setBounds(488, 25, 80, 30);
		getContentPane().add(lblProduct);

		lblProductsName = new JLabel("");
		lblProductsName.setBounds(563, 25, 120, 30);
		getContentPane().add(lblProductsName);

		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font(TAHOMA, Font.BOLD, 13));
		lblQuantity.setBounds(489, 59, 70, 30);
		getContentPane().add(lblQuantity);

		quantityTextField = new JTextField();
		quantityTextField.setBounds(564, 61, 70, 28);
		getContentPane().add(quantityTextField);
		quantityTextField.setColumns(10);

		clientsTable = new JTable();
		clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		clientsTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		clientsTable.setShowVerticalLines(true);

		JScrollPane scrollPaneClients = new JScrollPane(clientsTable);
		scrollPaneClients.setBounds(36, 102, 360, 400);
		getContentPane().add(scrollPaneClients);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(442, 25, 9, 526);
		getContentPane().add(separator);

		productsTable = new JTable();
		productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productsTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		productsTable.setShowVerticalLines(true);

		JScrollPane scrollPaneProducts = new JScrollPane(productsTable);
		scrollPaneProducts.setBounds(489, 102, 360, 400);
		getContentPane().add(scrollPaneProducts);

		btnPlaceOrder = new JButton("Place order");
		btnPlaceOrder.setFont(new Font(TAHOMA, Font.BOLD, 13));
		btnPlaceOrder.setForeground(new Color(0, 128, 0));
		btnPlaceOrder.setBounds(281, 548, 115, 25);
		getContentPane().add(btnPlaceOrder);

		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font(TAHOMA, Font.BOLD, 13));
		btnCancel.setForeground(new Color(255, 0, 0));
		btnCancel.setBounds(489, 548, 97, 25);
		getContentPane().add(btnCancel);

		lblMessage = new JLabel("Error message");
		lblMessage.setForeground(new Color(255, 0, 0));
		lblMessage.setFont(new Font(TAHOMA, Font.BOLD, 14));
		lblMessage.setBounds(36, 577, 260, 25);
		getContentPane().add(lblMessage);
		lblMessage.setVisible(false);
	}

	public void setClientsNameTextFieldText(String name) {
		lblClientsName.setText(name);
	}

	public void setClientsEmailTextFieldText(String email) {
		lblClientsEmail.setText(email);
	}

	public void setProductsNameTextFieldText(String name) {
		lblProductsName.setText(name);
	}

	public String getProductQuantityTextField() {
		return quantityTextField.getText();
	}

	public void setProductQuantityTextField(String text) {
		quantityTextField.setText(text);
	}

	public void addPlaceOrderButtonActionListener(ActionListener actionListener) {
		btnPlaceOrder.addActionListener(actionListener);
	}

	public void addCancelButtonActionListener(ActionListener actionListener) {
		btnCancel.addActionListener(actionListener);
	}

	public void addClientsTableActionListener(ListSelectionListener listSelectionListener) {
		clientsTable.getSelectionModel().addListSelectionListener(listSelectionListener);
	}

	public void addProductsTableActionListener(ListSelectionListener listSelectionListener) {
		productsTable.getSelectionModel().addListSelectionListener(listSelectionListener);
	}

	private JTable createTable(List<Object> objects) {
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

	public String[] getClientsTableRow() {
		String[] rowData = new String[clientsTable.getColumnCount()];
		if (clientsTable.getSelectedRow() == -1) {
			return new String[0];
		}
		for (int i = 0; i < rowData.length; i++) {
			rowData[i] = clientsTable.getValueAt(clientsTable.getSelectedRow(), i).toString();
		}
		return rowData;
	}

	public String[] getProductsTableRow() {
		String[] rowData = new String[productsTable.getColumnCount()];
		if (productsTable.getSelectedRow() == -1) {
			return new String[0];
		}
		for (int i = 0; i < rowData.length; i++) {
			rowData[i] = productsTable.getValueAt(productsTable.getSelectedRow(), i).toString();
		}
		return rowData;
	}

	public void setClientsTableModel(List<Object> objects) {
		clientsTable.setModel(createTable(objects).getModel());
	}

	public void setProductsTableModel(List<Object> objects) {
		productsTable.setModel(createTable(objects).getModel());
	}

	public boolean displayConfirmation(String message) {
		int option = JOptionPane.showConfirmDialog(this, message, "Confirm your selection",
				JOptionPane.OK_CANCEL_OPTION);
		return option == JOptionPane.OK_OPTION;
	}

	public void displayError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void displayMessage(String message) {
		Runnable runnable = () -> {
			lblMessage.setText(message);
			lblMessage.setVisible(true);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				lblMessage.setVisible(false);
			}
		};

		new Thread(runnable).start();
	}
}
