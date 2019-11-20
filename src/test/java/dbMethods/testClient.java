package dbMethods;

import java.sql.Connection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import business.ClientBLL;
import dao.ClientDAO;
import database.ConnectionFactory;
import model.Client;

public class testClient {

	private Connection connection = null;

	@Test
	public void testGetAll() {
		if ((connection = ConnectionFactory.getConnection()) != null) {
			System.out.println("Connection established - TestClient - GetAll!");
			ConnectionFactory.close(connection);

			List<Client> clients = new ClientDAO().findAll();
			List<Client> bllClients = new ClientBLL().getAll();

			Assert.assertEquals(bllClients, clients);
		} else {
			System.out.println("Connection failed - TestClient - GetAll!");
		}

	}

}
