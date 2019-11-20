package business;

import java.util.List;

import dao.ClientDAO;
import model.Client;

/**
 * This class defines all the methods that one can use in order to interact with
 * clients. This class also communicates with the database and processes the
 * incoming data and delivers it in a meaningful way to the user.
 * 
 * @author Bogdan Paun
 */
public class ClientBLL {

	private ClientDAO clientDAO;

	public ClientBLL() {
		clientDAO = new ClientDAO();
	}

	public Client get(int id) {
		return clientDAO.findByField("id", id);
	}

	public List<Client> getAll() {
		return clientDAO.findAll();
	}

	public void insert(Client c) {
		int id = clientDAO.insert(c);
		c.setId(id);
	}

	public void update(Client c) {
		clientDAO.update(c);
	}

	public void remove(int id) {
		clientDAO.remove(id);
	}
}
