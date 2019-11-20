package model;

import java.util.Date;

public class Orders {

	private int id;
	private int clientId;
	private int productId;
	private int quantity;
	private float price;
	private Date date;
	private String receiptPath;

	public int getId() {
		return id;
	}

	public int getClientId() {
		return clientId;
	}

	public int getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public float getPrice() {
		return price;
	}

	public Date getDate() {
		return date;
	}

	public String getReceiptPath() {
		return receiptPath;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setReceiptPath(String receiptPath) {
		this.receiptPath = receiptPath;
	}

	@Override
	public String toString() {
		return "Order: id = " + id + ", clientId = " + clientId + ", productId = " + productId + ", quantity = "
				+ quantity + ", price = " + price + ", date = " + date.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Orders)) {
			return false;
		}

		Orders aObj = (Orders) obj;

		return aObj.id == this.id;
	}

	@Override
	public int hashCode() {
		int result = 17 * 31 + id;
		result = result * 31 + clientId;
		result = result * 31 + productId;
		result = result * 31 + quantity;
		result = result * 31 + (int) price;
		result = result * 31 + date.hashCode();
		return result;
	}
}
