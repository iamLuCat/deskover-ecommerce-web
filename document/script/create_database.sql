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
	id BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    `description` VARCHAR(50) NOT NULL,
	created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

insert admin_role (id, `name`, `description`)
values 	(1, 'ROLE_ADMIN','Quản trị viên'),
		(2, 'ROLE_MANAGER','Nhân viên quản lý'),
        (3, 'ROLE_STAFF','Nhân viên'),
		(4, 'ROLE_WAREHOUSE','Nhân viên kho'),
        (5, 'ROLE_SHIPPER','Nhân viên giao hàng')
;

CREATE TABLE administrator (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    fullname VARCHAR(128)CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	role_id BIGINT NOT NULL,
    actived BIT NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE KEY UQ_Admin_Username (username),
	CONSTRAINT FK_Admin_Role FOREIGN KEY (role_id) REFERENCES admin_role (id)
);

insert administrator (username,fullname,role_id)
values 	('minhnh','Nguyễn Hoài Minh',1),
		('vupq','Phạm Quang Vũ',1),
		('haipv','Phạm Văn Hải',1),
        ('manager1','Nguyễn Thị Lài',2),
		('staff1','Nguyễn Tuyết Vân',3),
        ('staffwarehouse1','Phạm Văn Mạnh',4),
        ('shipper1','Nguyễn Mạnh Hùng',5)
;

CREATE TABLE admin_password (
	id BIGINT NOT NULL AUTO_INCREMENT,
    admin_id BIGINT NOT NULL,
    password VARCHAR(60) NOT NULL,
	created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
	CONSTRAINT FK_Password_Admin FOREIGN KEY (admin_id) REFERENCES administrator (id)
);

insert admin_password (admin_id,password)
values 	('1','$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
		('2','$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
		('3','$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
        ('4','$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
		('5','$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
        ('6','$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
        ('7','$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i')
;

--------------------------------------------------------------------------------------------------------------
-- Người dùng

CREATE TABLE `user` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  fullname VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  avatar VARCHAR(128) DEFAULT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_login TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actived BIT NOT NULL DEFAULT 1,
  verify BIT NOT NULL DEFAULT 0, 
  PRIMARY KEY (id),
  UNIQUE KEY UQ_User_Username (username)
);

insert `user` (username,fullname,verify)
values 	('huynq','Nguyễn Quang Huy',1),
		('minhbd','Bùi Đức Minh',1)
;

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
  CONSTRAINT FK_Contact_User FOREIGN KEY (user_id) REFERENCES `user`(id)
);

insert contact (user_id,email)
values 	('1','huynq2022@gmail.com'),
		('2','minhbd2022@gmail.com')
;

CREATE TABLE user_password (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  password VARCHAR(60) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT FK_Password_User FOREIGN KEY (user_id) REFERENCES `user` (id)
);

insert user_password (user_id,password)
values 	('1','12345678'),
		('2','12345678')
;

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
  actived BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_Category_Slug (slug)
);

insert category (name,slug)
values 	('Laptop','laptop'),
		('Bàn Phím','ban-phim-may-tinh'),
        ('Chuột + Lót Chuột','chuot-may-tinh'),
		('Màn Hình','man-hinh-may-tinh'),
		('Linh Kiện PC','linh-kien-may-tinh'),
        ('Ghế','ghe-gaming-gia-re')
;

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
  actived BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_SubCategory_Slug (slug),
  CONSTRAINT FK_SubCategory_Category FOREIGN KEY (category_id) REFERENCES category (id)
);

insert subcategory (category_id,name,slug)
values 	(1,'Laptop Văn Phòng','laptop-van-phong'),
		(1,'Laptop Gaming','laptop-gaming'),
		(2,'Bàn Phím Gaming','ban-phim-gaming'),
        (2,'Bàn Phím Văn Phòng','ban-phim-van-phong'),
		(3,'Chuột Gaming','chuot-gaming'),
        (3,'Chuột Văn Phòng','chuot-van-phong')
;

-- Thương hiệu
CREATE TABLE brand (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  description VARCHAR(150) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  slug VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP DEFAULT NULL,
  actived BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_Brand_Slug (slug)
);

insert brand (name,slug)
values 	('ASUS','laptop-asus'),
		('ACER','laptop-acer')
;

-- giảm giá
CREATE TABLE discount (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  description VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  start_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_date TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id)
);

-- Sản phẩm
CREATE TABLE product (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  slug VARCHAR(150) NOT NULL,
  image VARCHAR(150) NOT NULL,
  description TEXT CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI DEFAULT NULL,
  price DOUBLE DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_date TIMESTAMP DEFAULT NULL,
  actived BIT NOT NULL DEFAULT 1,
  sub_category_id BIGINT DEFAULT NULL,
  brand_id BIGINT NOT NULL,
  discount_id BIGINT DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_Product_SubCategory FOREIGN KEY (sub_category_id) REFERENCES subcategory (id),
  CONSTRAINT FK_Product_Brand FOREIGN KEY (brand_id) REFERENCES brand (id),
  CONSTRAINT FK_Product_Discount FOREIGN KEY (discount_id) REFERENCES discount (id)
);

insert product (name,slug,image,price,sub_category_id,brand_id)
values 	
		-- asus
		('Laptop Asus VivoBook A415EA EB1750W','asus-vivobook-a415ea-eb1750w','asus-vivobook-a415ea-eb1750w',14990000,1,1),
		('Laptop ASUS Vivobook Flip TP470EA EC346W','asus-vivobook-flip-tp470ea-ec346w','asus-vivobook-flip-tp470ea-ec346w',15890000,1,1),
        ('Laptop Asus Vivobook OLED A515EA L12033W','asus-vivobook-a515ea-l12033w','asus-vivobook-a515ea-l12033w',19990000,1,1),
		('Laptop ASUS VivoBook Pro 16X OLED M7600QC L2077W','asus-vivobook-pro-16x-oled-m7600qc-l2077w','asus-vivobook-pro-16x-oled-m7600qc-l2077w',32990000,1,1),
        ('Laptop Asus ZenBook 13 UX325EA KG599W','asus-zenbook-13-ux325ea-kg599w','asus-zenbook-13-ux325ea-kg599w',30890000,1,1),
		-- acer
        ('Laptop Acer Swift X SFX16 51G 50GS','acer-swift-x-sfx16-51g-50gs','acer-swift-x-sfx16-51g-50gs',29990000,1,2),
        ('Laptop Acer Swift 3 SF314 43 R52K','acer-swift-3-sf314-43-r52k','acer-swift-3-sf314-43-r52k',24490000,1,2),
        ('Laptop Acer Swift 3 SF314 43 R4X3','acer-swift-3-sf314-43-r4x3','acer-swift-3-sf314-43-r4x3',20990000,1,2),
		('Laptop Acer Aspire 3 A315 56 37DV','acer-aspire-3-a315-56-37dv','acer-aspire-3-a315-56-37dv',12490000,1,2),
		('Laptop Acer Aspire 5 A515 57 52Y2','acer-aspire-5-a515-57-52y2','acer-aspire-5-a515-57-52y2',18950000,1,2)
;

-- Lưu trữ
CREATE TABLE inventory (
  id BIGINT NOT NULL AUTO_INCREMENT,
  product_id BIGINT NOT NULL,
  quantity BIGINT NOT NULL,
  modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT FK_Inventory_Product FOREIGN KEY (product_id) REFERENCES product (id)
);

insert inventory (product_id,quantity)
values 	(1,100),
		(2,100),
        (3,100),
        (4,100),
        (5,100),
        (6,100),
        (7,100),
        (8,100),
        (9,100),
        (10,100)
;

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
  CONSTRAINT FK_Order_User FOREIGN KEY (user_id) REFERENCES `user` (id)
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
  CONSTRAINT FK_Cart_User FOREIGN KEY (user_id) REFERENCES `user` (id)
);

--------------------------------------------------------------------------------------------------------------
-- Giở hàng
--------------------------------------------------------------------------------------------------------------

CREATE TABLE verify (
  id BIGINT NOT NULL AUTO_INCREMENT,
  token VARCHAR(255) NOT NULL,
  actived BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_Verify_Token (token)
);