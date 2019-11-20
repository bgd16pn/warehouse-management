INSERT INTO address
	(street, number, city, county, country, postalCode) 
VALUES
	('Observatorului', '34', 'Cluj-Napoca', 'Cluj', 'Romania', '400066'),
    ('Baritiu', '26', 'Cluj-Napoca', 'Cluj', 'Romania', '400000'),
    ('Popandesti', '34', 'Fanicoara', 'Salaj', 'Romania', '452365');

INSERT INTO client
	(firstName, lastName, addressId, email) 
VALUES
	('Bogdan', 'Paun', 1, 'bgd.paun@utcn.ro'),
    ('Georgel', 'Ionescu', 2, 'grgl.ionescu@utcn.ro'),
    ('Ion', 'Pop', 2, 'ion.pop@utcn.ro'),
    ('Ghita', 'Neculae', 2, 'ghita.neculae@utcn.ro');
    
INSERT INTO product
	(name, unitPrice) 
VALUES
	('Farfurie', 5.6),
    ('Pahar', 3.4),
    ('Bidon plastic', 10.2),
    ('Pizza', 23.7);
    
INSERT INTO orders
	(clientId, productId, quantity, price, date, receiptPath) 
VALUES
	(1, 1, 2, 13.2, NOW(), 'receipt1'),
    (2, 4, 4, 94.8, NOW(), 'receipt2'),
    (3, 3, 6, 61.2, NOW(), 'receipt3'),
    (4, 2, 7, 23.8, NOW(), 'receipt4');
