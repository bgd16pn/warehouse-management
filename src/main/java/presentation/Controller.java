package presentation;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.UIManager;

import business.AddressBLL;
import business.ClientBLL;
import business.OrdersBLL;
import business.ProductBLL;
import component.PDFGenerator;
import model.Address;
import model.Client;
import model.Orders;
import model.Product;


/**
 * This class manages the whole application.
 * It takes as fields all the views and data business classes
 * and controls all the user activity in the application.
 * @author Bogdan Paun
 */
public class Controller {

	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final Map<String, Integer> CATEGORIES = new HashMap<>();
	private static final String CREATE = "create";
	private static final String EDIT = "edit";
	private static final String ORDERS = "Orders";
	private static final String CLIENTS = "Clients";
	private static final String PRODUCTS = "Products";
	private static final String ADDRESSES = "Addresses";
	private static final String INVALID_MESSAGE = "Invalid input data";
	private static final String SUCCESS_CREATE = "Entry successfully inserted";
	private static final String SUCCESS_EDIT = "Entry successfully updated";
	private static final String SUCCESS_DELETE = "Entry successfully deleted";

	private DashboardView dashboardView;
	private OrdersView ordersView;

	private OrdersBLL ordersBLL;
	private ClientBLL clientsBLL;
	private ProductBLL productsBLL;
	private AddressBLL addressesBLL;

	private String status = "";
	private boolean success = false;

	/**
	 * Initializes the application and starts it.
	 */
	public void start() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		dashboardView = new DashboardView(screenSize);
		ordersView = new OrdersView(screenSize);
		ordersBLL = new OrdersBLL();
		clientsBLL = new ClientBLL();
		productsBLL = new ProductBLL();
		addressesBLL = new AddressBLL();

		CATEGORIES.put(ORDERS, 6);
		CATEGORIES.put(CLIENTS, 4);
		CATEGORIES.put(PRODUCTS, 3);
		CATEGORIES.put(ADDRESSES, 8);

		dashboardView.setTableModel(new ArrayList<>(ordersBLL.getAll()));
		dashboardView.displayInputPanel(0);
		dashboardView.setSaveButtonVisible(false);

		initializeButtonListeners();
		initializeContext();

		dashboardView.setVisible(true);
		ordersView.setVisible(false);
	}

	private void initializeContext() {
		dashboardView.addTableActionListener(e -> {
			String category = dashboardView.getSelectedCategory();
			setCategoryEditContext(category, dashboardView.getTableRow());
			dashboardView.setTextFieldsEditable(false);
		});

		dashboardView.addCategoryComboBoxListener(e -> {
			List<Object> objects;
			dashboardView.displayInputPanel(0);
			String category = dashboardView.getSelectedCategory();
			setCategoryEditContext(category, dashboardView.getTableRow());
			switch (category) {
			case ORDERS:
				objects = new ArrayList<>(ordersBLL.getAll());
				break;
			case CLIENTS:
				objects = new ArrayList<>(clientsBLL.getAll());
				break;
			case PRODUCTS:
				objects = new ArrayList<>(productsBLL.getAll());
				break;
			case ADDRESSES:
				objects = new ArrayList<>(addressesBLL.getAll());
				break;
			default:
				objects = null;
			}
			dashboardView.setTableModel(objects);
			dashboardView.setTextFieldsEditable(false);
			for (int i = 0; i < 8; i++) {
				dashboardView.setTextFieldIText(i, "");
			}

		});
	}

	private void initializePlaceOrderContext() {
		dashboardView.setVisible(false);
		ordersView.setVisible(true);
		ordersView.setProductQuantityTextField("");

		ordersView.setClientsTableModel(new ArrayList<Object>(clientsBLL.getAll()));

		List<Product> products = productsBLL.getAll().stream().filter(e -> e.getQuantity() > 0)
				.collect(Collectors.toList());
		if (products.isEmpty()) {
			ordersView.displayError("No products available at the moment!");
			ordersView.setVisible(false);
			dashboardView.setVisible(true);
			return;
		}

		ordersView.setProductsTableModel(new ArrayList<Object>(products));
		ordersView.addClientsTableActionListener(e -> {
			String[] clientData = ordersView.getClientsTableRow();
			if (clientData.length > 0) {
				ordersView.setClientsNameTextFieldText(clientData[1] + " " + clientData[2]);
				ordersView.setClientsEmailTextFieldText(clientData[4]);
			}
		});

		ordersView.addProductsTableActionListener(e -> {
			String[] productData = ordersView.getProductsTableRow();
			if (productData.length > 0) {
				ordersView.setProductsNameTextFieldText(productData[1]);
			}
		});

		ordersView.addPlaceOrderButtonActionListener(e -> {
			String[] clientData = ordersView.getClientsTableRow();
			String[] productData = ordersView.getProductsTableRow();
			if (clientData.length > 0 && productData.length > 0) {
				String quantity = ordersView.getProductQuantityTextField();
				if (validatePlaceOrder(quantity)) {
					int availableQuantity = Integer.parseInt(productData[3]);
					int desiredQuantity = Integer.parseInt(quantity);
					if (desiredQuantity > availableQuantity) {
						ordersView.displayError("Insufficient stock!");
						return;
					}
					if (ordersView.displayConfirmation("Confirm placing your order")) {
						Orders newOrder = new Orders();
						int productId = Integer.parseInt(productData[0]);
						int clientId = Integer.parseInt(clientData[0]);

						newOrder.setId(ordersBLL.getNextId());
						newOrder.setClientId(clientId);
						newOrder.setProductId(productId);
						newOrder.setQuantity(desiredQuantity);
						newOrder.setPrice(desiredQuantity * Float.parseFloat(productData[2]));
						newOrder.setDate(Calendar.getInstance().getTime());
						newOrder.setReceiptPath(generateReceipt(newOrder));
						ordersBLL.insert(newOrder);

						ordersView.setVisible(false);
						dashboardView.setVisible(true);
						dashboardView.setTableModel(new ArrayList<>(ordersBLL.getAll()));
					}
				}
			} else {
				ordersView.displayMessage("Please select a client and a product");
			}
		});

		ordersView.addCancelButtonActionListener(e -> {
			ordersView.setClientsEmailTextFieldText("");
			ordersView.setClientsNameTextFieldText("");
			ordersView.setProductsNameTextFieldText("");
			ordersView.setProductQuantityTextField("");
			ordersView.setVisible(false);
			dashboardView.setVisible(true);
		});
	}

	private boolean validatePlaceOrder(String quantity) {
		if (quantity.equals("") || Integer.parseInt(quantity) < 0) {
			ordersView.displayMessage("Invalid quantity");
			return false;
		}
		return true;
	}

	private void initializeButtonListeners() {
		dashboardView.addCreateButtonActionListener(e -> {
			String category = dashboardView.getSelectedCategory();
			if (category.equals(ORDERS)) {
				initializePlaceOrderContext();
				return;
			}
			setCategoryEditContext(category, new String[CATEGORIES.get(category) + 1]);
			dashboardView.setSaveButtonVisible(true);
			dashboardView.setTextFieldsEditable(true);
			dashboardView.setCancelButtonVisible(true);
			status = CREATE;
		});

		dashboardView.addCancelButtonActionListener(e -> {
			dashboardView.setSaveButtonVisible(false);
			dashboardView.setCancelButtonVisible(false);
			dashboardView.setTextFieldsEditable(false);
			status = "";
		});

		dashboardView.addEditButtonActionListener(e -> {
			if (dashboardView.getTableRow().length == 0) {
				dashboardView.displayErrorMessage("Please select a row");
				return;
			}
			setCategoryEditContext(dashboardView.getSelectedCategory(), dashboardView.getTableRow());
			dashboardView.setTextFieldsEditable(true);
			dashboardView.setSaveButtonVisible(true);
			dashboardView.setCancelButtonVisible(true);
			status = "edit";
		});

		dashboardView.addDeleteButtonActionListener(e -> {
			if (dashboardView.getTableRow().length == 0) {
				dashboardView.displayErrorMessage("Please select a row");
				return;
			}
			if (!dashboardView.displayConfirmation("Are you sure you want to remove this entry?")) {
				return;
			}
			List<Object> objects;
			dashboardView.displayInputPanel(0);
			String category = dashboardView.getSelectedCategory();
			int id = Integer.parseInt(dashboardView.getTableRow()[0]);
			switch (category) {
			case ORDERS:
				ordersBLL.remove(id);
				objects = new ArrayList<>(ordersBLL.getAll());
				break;
			case CLIENTS:
				clientsBLL.remove(id);
				objects = new ArrayList<>(clientsBLL.getAll());
				break;
			case PRODUCTS:
				productsBLL.remove(id);
				objects = new ArrayList<>(productsBLL.getAll());
				break;
			case ADDRESSES:
				addressesBLL.remove(id);
				objects = new ArrayList<>(addressesBLL.getAll());
				break;
			default:
				objects = null;
			}
			dashboardView.setTableModel(objects);
			dashboardView.displaySuccessMessage(SUCCESS_DELETE);
		});

		dashboardView.addSaveButtonActionListener(e -> {
			switch (dashboardView.getSelectedCategory()) {
			case ORDERS:
				Orders newOrder;
				if ((newOrder = validateOrderEdit(dashboardView.getTextFields(6))) != null) {
					if (status.equals(EDIT)) {
						newOrder.setId(Integer.parseInt(dashboardView.getTableRow()[0]));
						ordersBLL.update(newOrder);
						dashboardView.displaySuccessMessage(SUCCESS_EDIT);
					}
					dashboardView.setTableModel(new ArrayList<>(ordersBLL.getAll()));
				}
				break;
			case CLIENTS:
				Client newClient;
				if ((newClient = validateClientEdit(dashboardView.getTextFields(4))) != null) {
					if (status.equals(EDIT)) {
						newClient.setId(Integer.parseInt(dashboardView.getTableRow()[0]));
						clientsBLL.update(newClient);
						dashboardView.displaySuccessMessage(SUCCESS_EDIT);
					} else if (status.equals(CREATE)) {
						clientsBLL.insert(newClient);
						dashboardView.displaySuccessMessage(SUCCESS_CREATE);
					}
					dashboardView.setTableModel(new ArrayList<>(clientsBLL.getAll()));
				}
				break;
			case PRODUCTS:
				Product newProduct;
				if ((newProduct = validateProductEdit(dashboardView.getTextFields(3))) != null) {
					if (status.equals(EDIT)) {
						newProduct.setId(Integer.parseInt(dashboardView.getTableRow()[0]));
						productsBLL.update(newProduct);
						dashboardView.displaySuccessMessage(SUCCESS_EDIT);
					} else if (status.equals(CREATE)) {
						productsBLL.insert(newProduct);
						dashboardView.displaySuccessMessage(SUCCESS_CREATE);
					}
					dashboardView.setTableModel(new ArrayList<>(productsBLL.getAll()));
				}
				break;
			case ADDRESSES:
				Address newAddress;
				if ((newAddress = validateAddressEdit(dashboardView.getTextFields(8))) != null) {
					if (status.equals(EDIT)) {
						newAddress.setId(Integer.parseInt(dashboardView.getTableRow()[0]));
						addressesBLL.update(newAddress);
						dashboardView.displaySuccessMessage(SUCCESS_EDIT);
					} else if (status.equals(CREATE)) {
						addressesBLL.insert(newAddress);
						dashboardView.displaySuccessMessage(SUCCESS_CREATE);
					}
					dashboardView.setTableModel(new ArrayList<>(addressesBLL.getAll()));
				}
				break;
			default:
			}
			if (success) {
				status = "";
				dashboardView.setTextFieldsEditable(false);
				dashboardView.setSaveButtonVisible(false);
				dashboardView.setCancelButtonVisible(false);
			}
		});
	}

	private void setCategoryEditContext(String selectedCategory, String[] tableRow) {
		switch (selectedCategory) {
		case ORDERS:
			setFieldLabels(Orders.class);
			break;
		case CLIENTS:
			setFieldLabels(Client.class);
			break;
		case PRODUCTS:
			setFieldLabels(Product.class);
			break;
		case ADDRESSES:
			setFieldLabels(Address.class);
			break;
		default:
		}
		dashboardView.displayInputPanel(CATEGORIES.get(selectedCategory));

		if (tableRow != null) {
			for (int i = 1; i < tableRow.length; i++) {
				dashboardView.setTextFieldIText(i - 1, tableRow[i]);
				dashboardView.setTextFieldIVisible(i - 1, true);
			}
		} else {
			for (int i = 0; i < 8; i++) {
				dashboardView.setTextFieldIText(i, "");
			}
		}
	}

	private void setFieldLabels(Class<?> clas) {
		Field[] fields = clas.getDeclaredFields();
		for (int i = 1; i < fields.length; i++) {
			fields[i].setAccessible(true);
			dashboardView.setFieldILabelText(i - 1, fields[i].getName());
		}
	}

	private Address validateAddressEdit(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			if (i != 2 && i != 3 && strings[i].equals("")) {
				dashboardView.displayErrorMessage(INVALID_MESSAGE);
				success = false;
				return null;
			}
		}

		Address newAddress = new Address();
		newAddress.setStreet(strings[0]);
		newAddress.setNumber(strings[1]);
		newAddress.setFlat(strings[2]);
		newAddress.setApartment(strings[3]);
		newAddress.setCity(strings[4]);
		newAddress.setCounty(strings[5]);
		newAddress.setCountry(strings[6]);
		newAddress.setPostalCode(strings[7]);

		success = true;
		return newAddress;
	}

	private Product validateProductEdit(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			if (strings[i].equals("")) {
				dashboardView.displayErrorMessage(INVALID_MESSAGE);
				dashboardView.setTextFieldsEditable(false);
				dashboardView.setSaveButtonVisible(false);
				success = false;
				return null;
			}
		}

		String name = strings[0];
		float unitPrice = Float.parseFloat(strings[1]);
		int quantity = Integer.parseInt(strings[2]);

		Product newProduct = new Product();
		newProduct.setName(name);
		newProduct.setUnitPrice(unitPrice);
		newProduct.setQuantity(quantity);
		success = true;
		return newProduct;
	}

	private Client validateClientEdit(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			if (strings[i].equals("")) {
				dashboardView.displayErrorMessage(INVALID_MESSAGE);
				dashboardView.setTextFieldsEditable(false);
				dashboardView.setSaveButtonVisible(false);
				success = false;
				return null;
			}
		}

		String firstName = strings[0];
		String lastName = strings[1];
		int addressId = Integer.parseInt(strings[2]);
		String email = strings[3];

		Client newClient = new Client();
		newClient.setFirstName(firstName);
		newClient.setLastName(lastName);
		newClient.setAddressId(addressId);
		newClient.setEmail(email);
		success = true;
		return newClient;
	}

	private Orders validateOrderEdit(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			if (strings[i].equals("")) {
				dashboardView.displayErrorMessage(INVALID_MESSAGE);
				dashboardView.setTextFieldsEditable(false);
				dashboardView.setSaveButtonVisible(false);
				success = false;
				return null;
			}
		}

		int quantity = Integer.parseInt(strings[2]);
		float price = Float.parseFloat(strings[3]);
		String receiptPath = strings[5];

		Orders newOrder = new Orders();
		newOrder.setQuantity(quantity);
		newOrder.setPrice(price);
		newOrder.setReceiptPath(receiptPath);
		success = true;
		return newOrder;
	}

	/**
	 * Sends a command to generate a new bill in the PDF format to the PDFGenerator.
	 * <p>
	 * @param order the order on which the bill is based on
	 * @return the path of where the bill will be saved
	 */
	private String generateReceipt(Orders order) {
		String exportPath = "";
		Product product = productsBLL.get(order.getProductId());
		Client client = clientsBLL.get(order.getClientId());
		Address address = addressesBLL.get(client.getAddressId());

		exportPath = "src\\main\\resources\\bills" + "\\bill_" + (1 + order.getId()) + ".pdf";
		PDFGenerator.generatePDF(exportPath, address, client, product, order);

		return exportPath;
	}

}
