package dbMethods;

import java.sql.Connection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import business.AddressBLL;
import dao.AddressDAO;
import database.ConnectionFactory;
import model.Address;

public class testAddress {

	private Connection connection = null;

	@Test
	public void testGetAll() {
		if ((connection = ConnectionFactory.getConnection()) != null) {
			System.out.println("Connection established - TestAddress - GetAll!");
			ConnectionFactory.close(connection);

			List<Address> addresses = new AddressDAO().findAll();
			List<Address> bllAddresses = new AddressBLL().getAll();

			Assert.assertEquals(bllAddresses, addresses);
		} else {
			System.out.println("Connection failed - TestAddress - GetAll!");
		}

	}

}
