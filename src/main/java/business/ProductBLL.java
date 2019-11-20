package business;

import java.util.List;

import dao.ProductDAO;
import model.Product;

/**
 * This class defines all the methods that one can use in order to interact with
 * products. This class also communicates with the database and processes the
 * incoming data and delivers it in a meaningful way to the user.
 * 
 * @author Bogdan Paun
 */
public class ProductBLL {

	private ProductDAO productDAO;

	public ProductBLL() {
		productDAO = new ProductDAO();
	}

	public Product get(int id) {
		return productDAO.findByField("id", id);
	}

	public void updateQuantity(int id, int newQuantity) {
		productDAO.updateQuantity(id, newQuantity);
	}

	public List<Product> getAll() {
		return productDAO.findAll();
	}

	public void insert(Product p) {
		int id = productDAO.insert(p);
		p.setId(id);
	}

	public void update(Product p) {
		productDAO.update(p);
	}

	public void remove(int id) {
		productDAO.remove(id);
	}

}
