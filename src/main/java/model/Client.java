package model;

public class Client {

	private int id;
	private String firstName;
	private String lastName;
	private int addressId;
	private String email;

	public Client() {

	}

	public Client(String firstName, String lastName, int addressId, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressId = addressId;
		this.email = email;
	}

	public int getAddressId() {
		return addressId;
	}

	public String getEmail() {
		return email;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Client: id = " + id + ", FirstName = " + firstName + ", LastName = " + lastName + ", Email = " + email
				+ ", addressId = " + addressId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Client)) {
			return false;
		}

		Client aObj = (Client) obj;

		return aObj.id == this.id;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = result * 31 + id;
		result = result * 31 + firstName.hashCode();
		result = result * 31 + lastName.hashCode();
		result = result * 31 + email.hashCode();
		result = result * 31 + addressId;
		return result;
	}

}
