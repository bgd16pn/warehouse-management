package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import database.ConnectionFactory;
import model.Orders;

public class OrdersDAO extends AbstractDAO<Orders> {

	/**
	 * Adds a new order to the database and returns the id of the inserted entity.
	 * If insertion fails, it returns -1.
	 * <p>
	 * 
	 * @param order the order to be inserted
	 * @return the index of the newly inserted order
	 */
	@Override
	public int insert(Orders order) {
		Connection connection = null;
		PreparedStatement insertStatement = null;
		ResultSet resultSet = null;
		int insertedId = -1;
		String query = "INSERT INTO orders(clientId, productId, quantity, price, receiptPath) VALUES(?, ?, ?, ?, ?)";
		try {
			connection = ConnectionFactory.getConnection();
			insertStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			insertStatement.setInt(1, order.getClientId());
			insertStatement.setInt(2, order.getProductId());
			insertStatement.setInt(3, order.getQuantity());
			insertStatement.setFloat(4, order.getPrice());
			insertStatement.setString(5, order.getReceiptPath());
			insertStatement.executeUpdate();
			resultSet = insertStatement.getGeneratedKeys();
			if (resultSet.next()) {
				insertedId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrdersDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(connection);
		}
		return insertedId;
	}

	/**
	 * Updates the fields of a given order without altering the id, the clientId,
	 * the productId or the date.
	 * <p>
	 * 
	 * @param order the entity to be updated
	 */
	@Override
	public void update(Orders order) {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		String query = "UPDATE Orders SET quantity = ?, price = ?, receiptPath = ? WHERE id = ?";
		try {
			connection = ConnectionFactory.getConnection();
			updateStatement = connection.prepareStatement(query);
			updateStatement.setInt(1, order.getQuantity());
			updateStatement.setFloat(2, order.getPrice());
			updateStatement.setString(3, order.getReceiptPath());
			updateStatement.setInt(4, order.getId());
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "DAO:update " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(connection);
		}
	}

}
