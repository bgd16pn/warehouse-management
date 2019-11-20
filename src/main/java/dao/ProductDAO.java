package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

import database.ConnectionFactory;
import model.Product;

public class ProductDAO extends AbstractDAO<Product> {

	/**
	 * Updates the quantity of a product given by its id.
	 * <p>
	 * 
	 * @param productId the id of a product
	 * @param newQuantity the new quantity of the selected product
	 */
	public void updateQuantity(int productId, int newQuantity) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "UPDATE product SET quantity = " + newQuantity + " WHERE id = " + productId + ";";

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Product" + "ProductDAO:updateQuantity " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}

}
