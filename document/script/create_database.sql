--------------------------------------------------------------------------------------------------------------
-- Tạo CSDL
--------------------------------------------------------------------------------------------------------------
USE sys;
DROP DATABASE IF EXISTS deskover;
CREATE DATABASE deskover;

USE deskover;

--------------------------------------------------------------------------------------------------------------
-- Tạo bảng
--------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------
-- Người quản trị
--------------------------------------------------------------------------------------------------------------

CREATE TABLE admin_role (
    id VARCHAR(10) NOT NULL,
    name VARCHAR(50) NOT NULL,
	created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE administrator (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    fullname VARCHAR(128)CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	role_id VARCHAR(10) NOT NULL,
    active BIT NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE KEY UQ_Admin_Username (username),
	CONSTRAINT FK_Admin_Role FOREIGN KEY (role_id) REFERENCES admin_role (id)
);

CREATE TABLE admin_password (
    admin_id BIGINT NOT NULL AUTO_INCREMENT,
    password VARCHAR(60) NOT NULL,
	created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (admin_id),
	CONSTRAINT FK_Password_Admin FOREIGN KEY (admin_id) REFERENCES administrator (id)
);

--------------------------------------------------------------------------------------------------------------
-- Người dùng
--------------------------------------------------------------------------------------------------------------
-- Người dùng
CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  fullname VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  avatar VARCHAR(128) DEFAULT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_login TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  is_active BIT NOT NULL DEFAULT 1,
  is_verify BIT NOT NULL DEFAULT 0, 
  PRIMARY KEY (id),
  UNIQUE KEY UQ_User_Username (username)
);

CREATE TABLE contact (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  address VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  province VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  district VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  ward VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  tel VARCHAR(10) DEFAULT NULL,
  email VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY UQ_Contact_Tel (tel),
  UNIQUE KEY UQ_Contact_Email (email),
  CONSTRAINT FK_Contact_User FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE user_password (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT FK_Password_User FOREIGN KEY (user_id) REFERENCES users (id)
);

--------------------------------------------------------------------------------------------------------------
-- Sản phẩm
--------------------------------------------------------------------------------------------------------------

-- Danh mục
CREATE TABLE category (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  description VARCHAR(150) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NULL,
  slug VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP DEFAULT NULL,
  active BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_Category_Slug (slug)
);

-- Thương hiệu
CREATE TABLE brand (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  description VARCHAR(150) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NULL,
  slug VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP DEFAULT NULL,
  active BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_Brand_Slug (slug)
);

-- Danh mục con
CREATE TABLE subcategory (
  id BIGINT NOT NULL AUTO_INCREMENT,
  category_id BIGINT NOT NULL,
  name VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  description VARCHAR(150) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NULL,
  slug VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP DEFAULT NULL,
  active BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_SubCategory_Slug (slug),
  CONSTRAINT FK_SubCategory_Category FOREIGN KEY (category_id) REFERENCES category (id)
);

-- Lưu trữ
CREATE TABLE inventory (
  id BIGINT NOT NULL AUTO_INCREMENT,
  quantity BIGINT NOT NULL,
  modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

-- giảm giá
CREATE TABLE discount (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  description VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  start DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  end DATETIME DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_date TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id)
);

-- Sản phẩm
CREATE TABLE product (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  image VARCHAR(150) NOT NULL,
  description TEXT CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL,
  price DOUBLE DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_date TIMESTAMP DEFAULT NULL,
  active BIT NOT NULL DEFAULT 1,
  category_id BIGINT NOT NULL,
  sub_category_id BIGINT NOT NULL,
  brand_id BIGINT NOT NULL,
  inventory_id BIGINT NOT NULL,
  discount_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_Product_Category FOREIGN KEY (category_id) REFERENCES category (id),
  CONSTRAINT FK_Product_SubCategory FOREIGN KEY (sub_category_id) REFERENCES subcategory (id),
  CONSTRAINT FK_Product_Brand FOREIGN KEY (brand_id) REFERENCES brand (id),
  CONSTRAINT FK_Product_Inventory FOREIGN KEY (inventory_id) REFERENCES inventory (id),
  CONSTRAINT FK_Product_Discount FOREIGN KEY (discount_id) REFERENCES discount (id)
);

--------------------------------------------------------------------------------------------------------------
-- Đặt hàng
--------------------------------------------------------------------------------------------------------------

-- Đơn đặt hàng
CREATE TABLE orders (
  id BIGINT NOT NULL AUTO_INCREMENT,
  order_code VARCHAR(11) NOT NULL,
  user_id BIGINT NOT NULL,
  full_name VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL,
  address VARCHAR(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL,
  tel VARCHAR(10) NOT NULL,
  email VARCHAR(50) NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status INT NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE KEY UQ_Order_OrderCode (order_code),
  CONSTRAINT FK_Order_User FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Chi tiết đơn đặt hàng
CREATE TABLE order_item (
  id BIGINT NOT NULL AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  price DOUBLE NOT NULL,
  create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT FK_OrderItems_Product FOREIGN KEY (product_id) REFERENCES product (id),
  CONSTRAINT FK_OrderItems_Order FOREIGN KEY (order_id) REFERENCES orders (id)
);

-- Thông tin đơn đặt hàng
CREATE TABLE order_detail (
  id BIGINT NOT NULL AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  address VARCHAR(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL,
  province VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  district VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  ward VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  tel VARCHAR(10) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_OrderDetail_Order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE
);

--------------------------------------------------------------------------------------------------------------
-- Giở hàng
--------------------------------------------------------------------------------------------------------------

CREATE TABLE cart (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NULL,
  product_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_Cart_User (user_id),
  CONSTRAINT FK_Cart_Product FOREIGN KEY (product_id) REFERENCES product (id),
  CONSTRAINT FK_Cart_User FOREIGN KEY (user_id) REFERENCES users (id)
);

--------------------------------------------------------------------------------------------------------------
-- Giở hàng
--------------------------------------------------------------------------------------------------------------

CREATE TABLE verify (
  id BIGINT NOT NULL AUTO_INCREMENT,
  token VARCHAR(255) NOT NULL,
  active BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_Verify_Token (token)
);