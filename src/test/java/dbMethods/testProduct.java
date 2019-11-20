package dbMethods;

import java.sql.Connection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import business.ProductBLL;
import dao.ProductDAO;
import database.ConnectionFactory;
import model.Product;

public class testProduct {
	private Connection connection = null;

	@Test
	public void testGetAll() {
		if ((connection = ConnectionFactory.getConnection()) != null) {
			System.out.println("Connection established - TestProduct - GetAll!");
			ConnectionFactory.close(connection);

			List<Product> products = new ProductDAO().findAll();
			List<Product> bllProducts = new ProductBLL().getAll();

			Assert.assertEquals(bllProducts, products);
		} else {
			System.out.println("Connection failed - TestProduct - GetAll!");
		}

	}

}
