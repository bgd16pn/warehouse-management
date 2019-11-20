package business;

import java.util.List;

import dao.AddressDAO;
import model.Address;

/**
 * This class defines all the methods that one can use in order to interact with
 * addresses. This class also communicates with the database and processes the
 * incoming data and delivers it in a meaningful way to the user.
 * 
 * @author Bogdan Paun
 */
public class AddressBLL {

	private AddressDAO addressDAO;

	public AddressBLL() {
		addressDAO = new AddressDAO();
	}

	public Address get(int id) {
		return addressDAO.findByField("id", id);
	}

	public List<Address> getAll() {
		return addressDAO.findAll();
	}

	public void insert(Address a) {
		int id = addressDAO.insert(a);
		a.setId(id);
	}

	public void update(Address a) {
		addressDAO.update(a);
	}

	public void remove(int id) {
		addressDAO.remove(id);
	}
}
