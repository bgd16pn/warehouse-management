package model;

public class Address {

	private int id;
	private String street;
	private String number;
	private String flat;
	private String apartment;
	private String city;
	private String county;
	private String country;
	private String postalCode;

	public Address() {

	}

	public Address(String street, String number, String city, String county, String country, String postalCode) {
		this.street = street;
		this.number = number;
		this.city = city;
		this.county = county;
		this.country = country;
		this.postalCode = postalCode;
		this.flat = "";
		this.apartment = "";
	}

	public int getId() {
		return id;
	}

	public String getStreet() {
		return street;
	}

	public String getNumber() {
		return number;
	}

	public String getFlat() {
		return flat;
	}

	public String getApartment() {
		return apartment;
	}

	public String getCity() {
		return city;
	}

	public String getCounty() {
		return county;
	}

	public String getCountry() {
		return country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "Address: id = " + id + ", street = " + street + ", number = " + number + ", flat = " + flat
				+ ", apartment = " + apartment + ", city = " + city + ", county = " + county + ", country = " + country
				+ ", postalCode = " + postalCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Address)) {
			return false;
		}

		Address aObj = (Address) obj;

		return aObj.id == this.id;
	}

	@Override
	public int hashCode() {
		int result = 17 * 31 + id;
		result = result * 31 + street.hashCode();
		result = result * 31 + number.hashCode();
		result = result * 31 + flat.hashCode();
		result = result * 31 + apartment.hashCode();
		result = result * 31 + city.hashCode();
		result = result * 31 + county.hashCode();
		result = result * 31 + country.hashCode();
		result = result * 31 + postalCode.hashCode();
		return result;
	}

}
