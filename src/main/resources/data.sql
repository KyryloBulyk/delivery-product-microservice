-- Insert into the Category table
INSERT INTO category (name) VALUES ('Meals');
INSERT INTO category (name) VALUES ('Beverages');

-- Insert into the Product (or Meal/Beverage) table
-- Assuming the IDs of categories are 1 for Meals, 2 for Beverages
-- Meals
INSERT INTO product (name, price, category_id) VALUES ('Pizza Margherita', 10.99, 1);
INSERT INTO product (name, price, category_id) VALUES ('Vegan Salad', 12.50, 1);
INSERT INTO product (name, price, category_id) VALUES ('Grilled Chicken', 15.00, 1);

-- Beverages
INSERT INTO product (name, price, category_id) VALUES ('Mineral Water', 1.99, 2);
INSERT INTO product (name, price, category_id) VALUES ('Coffee', 2.99, 2);
INSERT INTO product (name, price, category_id) VALUES ('Green Tea', 2.49, 2);

