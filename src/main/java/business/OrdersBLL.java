package business;

import java.util.List;

import dao.OrdersDAO;
import model.Orders;

/**
 * This class defines all the methods that one can use in order to interact with
 * orders. This class also communicates with the database and processes the
 * incoming data and delivers it in a meaningful way to the user.
 * 
 * @author Bogdan Paun
 */
public class OrdersBLL {

	private OrdersDAO ordersDAO;

	public OrdersBLL() {
		ordersDAO = new OrdersDAO();
	}

	public Orders get(int id) {
		return ordersDAO.findByField("id", id);
	}
	
	public List<Orders> getAll(){
		return ordersDAO.findAll();
	}

	public void insert(Orders a) {
		int id = ordersDAO.insert(a);
		a.setId(id);
	}

	public void update(Orders a) {
		ordersDAO.update(a);
	}

	public void remove(int id) {
		ordersDAO.remove(id);
	}

	public int getNextId() {
		return ordersDAO.findNextId();
	}

}
