package model;

public class Product {

	private int id;
	private String name;
	private float unitPrice;
	private int quantity;

	public Product() {

	}

	public Product(String name, float unitPrice) {
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantity = 0;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product: id = " + id + ", Name = " + name + ", UnitPrice = " + unitPrice + ", Quantity = " + quantity;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Product)) {
			return false;
		}

		Product aObj = (Product) obj;

		return aObj.id == this.id;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = result * 31 + id;
		result = result * 31 + name.hashCode();
		result = result * 31 + (int) unitPrice;
		result = result * 31 + quantity;
		return result;
	}

}
