DROP TRIGGER IF EXISTS on_insert_orders;
Delimiter //
CREATE TRIGGER on_insert_orders AFTER INSERT ON orders
FOR EACH ROW BEGIN
	SET @new_quantity := (SELECT quantity FROM product WHERE id = NEW.productId) - NEW.quantity;
    UPDATE product SET quantity = @new_quantity WHERE id = NEW.productId;
END //
Delimiter //