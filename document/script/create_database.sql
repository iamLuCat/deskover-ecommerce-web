--------------------------------------------------------------------------------------------------------------
-- Tạo CSDL
--------------------------------------------------------------------------------------------------------------
DROP DATABASE `deskover;

CREATE DATABASE `deskover`;
USE `deskover`;

--------------------------------------------------------------------------------------------------------------
-- Tạo bảng
--------------------------------------------------------------------------------------------------------------

-- Người dùng
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `fullname` VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  `avatar` VARCHAR(128) DEFAULT NULL,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_active` BIT NOT NULL DEFAULT 1,
  `is_verify` BIT NOT NULL DEFAULT 0, 
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_User_Username` (`username`),
  UNIQUE KEY `UQ_User_Email` (`email`)
);

CREATE TABLE `contact` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `address` VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  `province` VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  `district` VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  `ward` VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  `tel` VARCHAR(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_Contact_User` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  UNIQUE KEY `UQ_Contact_Tel` (`tel`)
);

CREATE TABLE `cart_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NULL,
  `product_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_Cart_User` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

/*
CREATE TABLE `rank` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  `province` VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  `district` VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  `ward` VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  `tel` VARCHAR(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_Contact_User` FOREIGN KEY (`id`) REFERENCES `user` (`id`),
);
*/

CREATE TABLE `user_admin` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL,
    `fullname` VARCHAR(128)CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
    `last_login_date` TIMESTAMP NULL,
    `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_date` TIMESTAMP DEFAULT NULL,
	`role_id` VARCHAR(50) NOT NULL,
    `is_active` BIT NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UQ_User_Username` (`username`)
);

CREATE TABLE `admin_role` (
    `id` VARCHAR(50) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
	`created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`modified_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `admin_password` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `password` VARCHAR(60) NOT NULL,
	`created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`modified_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
	CONSTRAINT `FK_AdminPassWord_UserAdmin` FOREIGN KEY (`id`) REFERENCES `user_admin` (`id`),
);

/*
INSERT INTO `user` (`username`,`password`,`fullname`,`created_date`) 
		VALUES ('huynq','123456789','Nguyễn Quang Huy',CURDATE()),
				('minhnh','123456789','Nguyễn Hoài Minh',CURDATE()),
            	('vupq','123456789','Phạm Quang Vũ',CURDATE()),
				('haipv','123456789','Phạm Văn Hải',CURDATE()),
				('minhbd','123456789','Bùi Đức Minh',CURDATE());
*/

-- Thương hiệu
CREATE TABLE `brand` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  `slug` VARCHAR(50) NOT NULL,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `delete_date` TIMESTAMP NULL,
  `is_active` BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Brand_Slug` (`slug`)
);

-- Danh mục
CREATE TABLE `category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  `slug` VARCHAR(50) NOT NULL,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `delete_date` TIMESTAMP NULL,
  `is_active` BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Category_Slug` (`slug`)
);

-- Danh mục
CREATE TABLE `sub_category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `category_id` BIGINT NOT NULL,
  `name` VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  `slug` VARCHAR(50) NOT NULL,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `delete_date` TIMESTAMP NULL,
  `is_active` BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_SubCategory_Slug` (`slug`)
  KEY `FK_SubCategory_Category` (`category_id`),
  CONSTRAINT `FK_SubCategory_Category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
);

CREATE TABLE `inventory` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `quantity` BIGINT NOT NULL,
  `modified_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `discount` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  `description` VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  `start_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `delete_date` TIMESTAMP NULL,
  PRIMARY KEY (`id`)
);

-- Sản phẩm
CREATE TABLE `product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  `image` VARCHAR(150) NOT NULL,
  `description` TEXT CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL,
  `price` DOUBLE DEFAULT NULL,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `delete_date` TIMESTAMP NULL,
  `status` INT NOT NULL DEFAULT '0',
  `category_id` BIGINT NOT NULL,
  `brand_id` BIGINT NOT NULL,
  `inventory_id` BIGINT NOT NULL,
  `is_active` BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `FK_Product_Brand` (`brand_id`),
  KEY `FK_Product_Category` (`category_id`),
  KEY `FK_Product_Inventory` (`inventory_id`),
  CONSTRAINT `FK_Product_Brand` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FK_Product_Category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_Product_Inventory` FOREIGN KEY (`inventory_id`) REFERENCES `inventory` (`id`)
);

-- Đơn đặt hàng
CREATE TABLE `order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_code` VARCHAR(11) NOT NULL,
  `user_id` BIGINT NOT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` INT NOT NULL DEFAULT '0',
  `full_name` VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL,
  `address` VARCHAR(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL,
  `email` VARCHAR(128) NULL,
  `phone` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UQ_Order_OrderCode` (`order_code`),
  KEY `FK_Order_User` (`user_id`),
  CONSTRAINT `FK_Order_User` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);


-- Đơn đặt hàng
CREATE TABLE `order_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_OrderItems_Product` (`product_id`),
  KEY `FK_OrderItems_Order` (`order_id`),
  CONSTRAINT `FK_OrderItems_Product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_OrderItems_Order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
);

-- Chi tiết đơn đặt hàng
CREATE TABLE `order_detail` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_OrderDetail_Order` (`order_id`),
  KEY `FK_OrderDetail_Product` (`product_id`),
  CONSTRAINT `FK_OrderDetail_Order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_OrderDetail_Product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `verify_token` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_OrderDetail_Order` (`order_id`),
  KEY `FK_OrderDetail_Product` (`product_id`),
  CONSTRAINT `FK_OrderDetail_Order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_OrderDetail_Product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `voucher` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `voucher_id` VARCHAR(50) NOT NULL,
  `start_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `quantity` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_OrderDetail_Order` (`order_id`),
  KEY `FK_OrderDetail_Product` (`product_id`),
  CONSTRAINT `FK_OrderDetail_Order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_OrderDetail_Product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);