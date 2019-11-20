package dbMethods;

import java.sql.Connection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import business.OrdersBLL;
import dao.OrdersDAO;
import database.ConnectionFactory;
import model.Orders;

public class testOrders {

	private Connection connection = null;

	@Test
	public void testGetAll() {
		if ((connection = ConnectionFactory.getConnection()) != null) {
			System.out.println("Connection established - TestOrders - GetAll!");
			ConnectionFactory.close(connection);

			List<Orders> orders = new OrdersDAO().findAll();
			List<Orders> bllOrders = new OrdersBLL().getAll();

			Assert.assertEquals(bllOrders, orders);
		} else {
			System.out.println("Connection failed - TestOrders - GetAll!");
		}

	}

}
