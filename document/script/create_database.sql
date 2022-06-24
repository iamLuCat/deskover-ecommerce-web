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
    role_id VARCHAR(20) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
	created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_user VARCHAR(50) DEFAULT NULL,
    UNIQUE KEY UQ_AdminRole_Name (`name`),
    PRIMARY KEY (id)
);

INSERT admin_role (id, role_id, `name`)
VALUES 	(1, 'ROLE_ADMIN','Quản trị viên'),
		(2, 'ROLE_MANAGER','Nhân viên quản lý'),
        (3, 'ROLE_STAFF','Nhân viên'),
		(4, 'ROLE_WAREHOUSE','Nhân viên Kho'),
        (5, 'ROLE_SHIPPER','Nhân viên giao hàng');

CREATE TABLE administrator (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    fullname VARCHAR(128)CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
	avatar VARCHAR(128) DEFAULT NULL,
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actived BIT NOT NULL DEFAULT 1,
    modified_user VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY UQ_Admin_Username (username)
);

INSERT administrator (id,username,fullname)
VALUES 	(1,'minhnh','Nguyễn Hoài Minh'),
		(2,'vupq','Phạm Quang Vũ'),
		(3,'haipv','Phạm Văn Hải'),
        (4,'manager1','Nguyễn Thị Lài'),
		(5,'staff1','Nguyễn Tuyết Vân'),
        (6,'staffwarehouse1','Phạm Văn Mạnh'),
        (7,'shipper1','Nguyễn Mạnh Hùng')
;

CREATE TABLE admin_password (
	id BIGINT NOT NULL AUTO_INCREMENT,
    admin_id BIGINT NOT NULL,
    `password` VARCHAR(60) NOT NULL,
	created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT NULL,
    modified_user VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (id),
	CONSTRAINT FK_Password_Admin FOREIGN KEY (admin_id) REFERENCES administrator (id)
);

INSERT admin_password (id,admin_id,password)
VALUES 	(1,1,'$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
		(2,2,'$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
		(3,3,'$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
        (4,4,'$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
		(5,5,'$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
        (6,6,'$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
        (7,7,'$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i')
;

CREATE TABLE admin_authority (
	id BIGINT NOT NULL AUTO_INCREMENT,
    role_id BIGINT NOT NULL,
    admin_id BIGINT NOT NULL,
    modified_user VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_Authority_Admin FOREIGN KEY (admin_id) REFERENCES administrator (id),
    CONSTRAINT FK_Authority_Role FOREIGN KEY (role_id) REFERENCES admin_role (id)
);

INSERT INTO admin_authority (id, role_id, admin_id) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 2, 4),
(5, 3, 5),
(6, 4, 6),
(7, 5, 7);

--------------------------------------------------------------------------------------------------------------
-- Người dùng

CREATE TABLE `user` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  fullname VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  avatar VARCHAR(128) DEFAULT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP DEFAULT NULL,
  last_login TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actived BIT NOT NULL DEFAULT 1,
  verify BIT NOT NULL DEFAULT 0, 
  modified_user VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_User_Username (username)
);

insert `user` (id,username,fullname,verify)
values 	(1,'huynq','Nguyễn Quang Huy',1),
		(2,'minhbd','Bùi Đức Minh',1)
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

insert contact (id,user_id,email)
values 	(1,1,'huynq2022@gmail.com'),
		(2,2,'minhbd2022@gmail.com')
;

CREATE TABLE user_password (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_date TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_Password_User FOREIGN KEY (user_id) REFERENCES `user` (id)
);

insert user_password (id,user_id,password)
values 	(1,1,'$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i'),
		(2,2,'$2a$12$iSxWCDhCdIlnPOvIvaO.7eNqEWTiZu7f/evEL3GYn8QrABKUOxd9i')
;

--------------------------------------------------------------------------------------------------------------
-- Sản phẩm
--------------------------------------------------------------------------------------------------------------

-- Danh mục
CREATE TABLE category (
  id BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  `description` VARCHAR(150) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NULL,
  slug VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP DEFAULT NULL,
  actived BIT NOT NULL DEFAULT 1,
  modified_user VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_Category_Slug (slug)
);

insert category (id,name,slug)
values 	(1,'Laptop','laptop'),
		(2,'Bàn Phím','ban-phim'),
        (3,'Chuột + Lót Chuột','chuot-lot-chuot'),
		(4,'Màn Hình','man-hinh'),
		(5,'Linh Kiện PC','linh-kien-pc'),
        (6,'Ghế','ghe'),
        (7,'Apple','apple')
;

-- Danh mục con
CREATE TABLE subcategory (
  id BIGINT NOT NULL AUTO_INCREMENT,
  category_id BIGINT NOT NULL,
  `name` VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  `description` VARCHAR(150) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NULL,
  slug VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP DEFAULT NULL,
  actived BIT NOT NULL DEFAULT 1,
  modified_user VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_SubCategory_Slug (slug),
  CONSTRAINT FK_SubCategory_Category FOREIGN KEY (category_id) REFERENCES category (id)
);

insert subcategory (id,category_id,name,slug)
values 	(1,1,'Laptop Văn Phòng','laptop-van-phong'),
		(2,1,'Laptop Gaming','laptop-gaming'),
		(3,2,'Bàn Phím Gaming','ban-phim-gaming'),
        (4,2,'Bàn Phím Văn Phòng','ban-phim-van-phong'),
        (5,2,'Bàn Phím Bluetooth','ban-phim-bluetooth'),
		(6,3,'Chuột Gaming','chuot-gaming'),
        (7,3,'Chuột Văn Phòng','chuot-van-phong'),
        (8,3,'Chuột Không dây','chuot-khong-day'),
        (9,3,'Ghế Gaming','ghe-gaming'),
        (10,3,'Ghế Công Thái Học','ghe-cong-thai-hoc'),
        (11,7,'MacBook','macbook'),
        (12,7,'IMac','imac'),
        (13,7,'Mac mini','mac-mini')
;

-- Thương hiệu
CREATE TABLE brand (
  id BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  `description` VARCHAR(150) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  slug VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP DEFAULT NULL,
  actived BIT NOT NULL DEFAULT 1,
  modified_user VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_Brand_Slug (slug)
);

insert brand (id,`name`,slug)
values 	(1,'Asus','asus'),
		(2,'Acer','acer'),
        (3,'Dell','dell'),
        (4,'Msi','msi'),
        (5,'Lenovo','lenovo'),
        (6,'Hp','hp'),
        (7,'Lg','lg'),
        (8,'Apple','apple')
;

-- giảm giá
CREATE TABLE discount (
  id BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  `description` VARCHAR(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI DEFAULT NULL,
  percent INT NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_date TIMESTAMP DEFAULT NULL,
  actived BIT NOT NULL DEFAULT 1,
  modified_user VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (id)
);

insert discount (id,`name`,percent,start_date)
values 	(1,'Black Friday',50,'2022-11-25'),
		(2,'Valentine','10','2022-02-14'),
        (3,'Lễ giáng sinh',20,'2022-12-24'),
        (4,'Test',20,'2022-01-01')
;

-- Sản phẩm
CREATE TABLE product (
  id BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI NOT NULL,
  slug VARCHAR(150) NOT NULL,
  image VARCHAR(150) DEFAULT NULL,
  `description` TEXT CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI DEFAULT NULL,
  price DOUBLE DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_date TIMESTAMP DEFAULT NULL,
  actived BIT NOT NULL DEFAULT 1,
  sub_category_id BIGINT DEFAULT NULL,
  brand_id BIGINT NOT NULL,
  discount_id BIGINT DEFAULT NULL,
  modified_user VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_Product_SubCategory FOREIGN KEY (sub_category_id) REFERENCES subcategory (id),
  CONSTRAINT FK_Product_Brand FOREIGN KEY (brand_id) REFERENCES brand (id),
  CONSTRAINT FK_Product_Discount FOREIGN KEY (discount_id) REFERENCES discount (id)
);

insert product (id,`name`,slug,price,sub_category_id,brand_id,discount_id)
values 	
		-- asus
			-- laptop-van-phong
		(1,'Laptop Asus VivoBook A415EA EB1750W','laptop-asus-vivobook-a415ea-eb1750w',14990000,1,1,4),
		(2,'Laptop ASUS Vivobook Flip TP470EA EC346W','laptop-asus-vivobook-flip-tp470ea-ec346w',15890000,1,1,4),
        (3,'Laptop Asus Vivobook OLED A515EA L12033W','laptop-asus-vivobook-a515ea-l12033w',19990000,1,1,4),
		(4,'Laptop ASUS VivoBook Pro 16X OLED M7600QC L2077W','laptop-asus-vivobook-pro-16x-oled-m7600qc-l2077w',32990000,1,1,4),
        (5,'Laptop Asus ZenBook 13 UX325EA KG599W','laptop-asus-zenbook-13-ux325ea-kg599w',30890000,1,1,4),
		-- acer
			-- laptop-van-phong
        (6,'Laptop Acer Swift X SFX16 51G 50GS','laptop-acer-swift-x-sfx16-51g-50gs',29990000,1,2,null),
        (7,'Laptop Acer Swift 3 SF314 43 R52K','laptop-acer-swift-3-sf314-43-r52k',24490000,1,2,null),
        (8,'Laptop Acer Swift 3 SF314 43 R4X3','laptop-acer-swift-3-sf314-43-r4x3',20990000,1,2,null),
		(9,'Laptop Acer Aspire 3 A315 56 37DV','laptop-acer-aspire-3-a315-56-37dv',12490000,1,2,null),
		(10,'Laptop Acer Aspire 5 A515 57 52Y2','laptop-acer-aspire-5-a515-57-52y2',18950000,1,2,null),
        -- Dell
			-- laptop-van-phong
        (11,'Laptop Dell Vostro 3510 V5I3305W Black','laptop-dell-vostro-3510-v5i3305w-black',15990000,1,3,null),
        (12,'Laptop Dell Vostro 3400 P132G003 70270645','laptop-dell-vostro-3400-p132g003-70270645',17490000,1,3,null),
        (13,'Laptop Dell Inspiron 15 3511 P112F002 70270650','laptop-dell-inspiron-15-3511-p112f002-70270650',20990000,1,3,null),
        (14,'Laptop Dell Vostro 5620 70282719 P117F001','laptop-dell-vostro-5620-70282719-p117f001',22990000,1,3,null),
        (15,'Laptop Dell XPS 17 9700 XPS7I7001W1 Silver','laptop-dell-xps-17-9700-xps7i7001w1-silver',75000000,1,3,null),
        -- MSI
			-- laptop-van-phong
        (16,'Laptop MSI Modern 14 B11MOU 1028VN','laptop-msi-modern-14-b11mou-1028vn',14490000,1,4,null),
        (17,'Laptop MSI Modern 14 B5M 202VN','laptop-msi-modern-14-b5m-202vn',16990000,1,4,null),
        (18,'Laptop MSI Modern 15 A5M 238VN','laptop-msi-modern-15-a5m-238vn',17990000,1,4,null),
        (19,'Laptop MSI Modern 15 A5M 239VN','laptop-msi-modern-15-a5m-239vn',19990000,1,4,null),
        (20,'Laptop MSI Modern 14 B11MOU 1033VN','laptop-msi-modern-14-b11mou-1033vn',21990000,1,4,null),
        -- LENOVO
			-- laptop-van-phong
        (21,'Laptop Lenovo V14 G2 ITL 82KA00RXVN','laptop-lenovo-v14-g2-itl-82ka00rxvn',13590000,1,5,null),
        (22,'Laptop Lenovo ThinkBook 15 G3 ACL 21A400CFVN','laptop-lenovo-thinkbook-15-g3-acl-21a400cfvn',19990000,1,5,null),
        (23,'Laptop Lenovo IdeaPad 5 Pro 16ACH6 82L500WMVN','laptop-lenovo-ideapad-5-pro-16ach6-82l500wmvn',25990000,1,5,null),
        (24,'Laptop Lenovo Yoga Slim 7 Pro 14ACH5 82NK003HVN','laptop-lenovo-yoga-slim-7-pro-14ach5-82nk003hvn',32990000,1,5,null),
        (25,'Laptop Lenovo Yoga Slim 7 Carbon 14ACN6 82L0005AVN','laptop-lenovo-yoga-slim-7-carbon-14acn6-82l0005avn',35990000,1,5,null),
        -- HP
			-- laptop-van-phong
        (26,'Laptop HP Pavilion 15 EG0506TX 46M05PA','laptop-hp-pavilion-15-eg0506tx-46m05pa',19990000,1,6,null),
        (27,'Laptop HP ProBook 450 G8 614K3PA','laptop-hp-probook-450-g8-614k3pa',20990000,1,6,null),
        (28,'Laptop HP Pavilion 14 dv0534TU 4P5G3PA','laptop-hp-pavilion-14-dv0534tu-4p5g3pa',22490000,1,6,null),
        -- LG
			-- laptop-van-phong
        (29,'LG Gram 17ZD90P-G.AX71A5','laptop-lg-gram-17zd90p-g-ax71a5',44990000,1,7,null),
        (30,'LG Gram 16Z90P-G.AH73A5','laptop-lg-gram-16z90p-g-ah73a5',48900000,1,7,null),
        (31,'LG Gram 14Z90P-G.AH75A5','laptop-lg-gram-14z90p-g-ah75a5',47990000,1,7,null),
        -- APPLE
			-- Macbook
		(32,'Macbook Air M2 10GPU 8GB 512GB - Silver','macbook-air-m2-10gpu-8gb-512gb-silver',42990000,9,8,null),
		(33,'Macbook Air M2 8GPU 8GB 256GB - Starlight','macbook-air-m2-8gpu-8gb-256gb-starlight',33990000,9,8,null),
        (34,'MacBook Pro 13 M2 10GPU 8GB 512GB Space Gray','macbook-pro-13-m2-10gpu-8gb-512gb-space-gray',39990000,9,8,null),
        (35,'MacBook Pro 14" 2021 M1 Pro 10CPU 16 GPU 16GB 1TB Silver','macbook-pro-14-2021-m1-pro-10-cpu-16gpu-16gb-1tb-silver',64990000,9,8,null),
        (36,'MacBook Pro 16 2021 M1 Max 32GPU 32GB 1TB Space Gray','macbook-pro-16-2021-m1-max-32gb-1tb-space-gray',99000000,9,8,null),
			-- IMac
		(37,'iMac 24 2021 M1 7GPU 8GB 256GB MGTF3SA/A - Silver','imac-24-2021-m1-7gpu-8gb-256gb-mgtf3sa-a-silver',33990000,10,8,null),
		(38,'iMac 24 2021 M1 8GPU 8GB 256GB MGPK3SA/A - Blue','imac-24-2021-m1-7gpu-8gb-256gb-mgpk3sa-a-blue',38990000,10,8,null),
		(39,'iMac 24 2021 M1 8GPU 16GB 512GB Z12R00047 - Silver','imac-24-2021-m1-8gpu-16gb-512gb-z12r00047-silver',50990000,10,8,null),
			-- Mac Mini
		(40,'Mac Mini M1 8GPU 16GB 1TB Z12P000HM','mac-mini-m1-8gpu-16gb-1tb-z12p000hm',41990000,11,8,null),
        (41,'Mac Mini M1 8GPU 16GB 512GB Z12P000HK','mac-mini-m1-8gpu-16gb-512gb-z12p000hk',34990000,11,8,null)
;

-- Lưu trữ
CREATE TABLE inventory (
  id BIGINT NOT NULL AUTO_INCREMENT,
  product_id BIGINT NOT NULL,
  quantity BIGINT NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_date TIMESTAMP DEFAULT NULL,
  modified_user VARCHAR(50) DEFAULT NULL,
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
        (10,100),
        (11,100),
        (12,100),
        (13,100),
        (14,100),
        (15,100),
        (16,100),
        (17,100),
        (18,100),
        (19,100),
        (20,100),
        (21,100),
		(22,100),
        (23,100),
        (24,100),
        (25,100),
        (26,100),
        (27,100),
        (28,100),
        (29,100),
        (30,100),
        (31,100),
        (32,100),
        (33,100),
        (34,100),
        (35,100),
        (36,100),
        (37,100),
        (38,100),
        (39,100),
        (40,100),
        (41,100)
;

--------------------------------------------------------------------------------------------------------------
-- Đặt hàng
--------------------------------------------------------------------------------------------------------------

-- Đơn đặt hàng
CREATE TABLE orders (
  id BIGINT NOT NULL AUTO_INCREMENT,
  order_code VARCHAR(11) NOT NULL,
  user_id BIGINT DEFAULT NULL,
  full_name VARCHAR(128) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL,
  email VARCHAR(50) DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` INT NOT NULL DEFAULT '0',
  modified_user VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_Order_OrderCode (order_code),
  CONSTRAINT FK_Order_User FOREIGN KEY (user_id) REFERENCES `user` (id)
);

insert orders (id,order_code,user_id,full_name)
values 	(1,'HD-11062022',1,'Nguyễn Quang Huy'),
		(2,'HD-12062022',1,'Nguyễn Quang Huy'),
        (3,'HD-13062022',1,'Nguyễn Quang Huy'),
        (4,'HD-14062022',1,'Nguyễn Quang Huy'),
        (5,'HD-15062022',1,'Nguyễn Quang Huy'),
        (6,'HD-16062022',2,'Bùi Đức Minh'),
        (7,'HD-17062022',2,'Bùi Đức Minh'),
        (8,'HD-18062022',2,'Bùi Đức Minh'),
        (9,'HD-19062022',2,'Bùi Đức Minh'),
        (10,'HD-20062022',2,'Bùi Đức Minh')
;

-- Chi tiết đơn đặt hàng
CREATE TABLE order_item (
  id BIGINT NOT NULL AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  price DOUBLE NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_OrderItems_Product FOREIGN KEY (product_id) REFERENCES product (id),
  CONSTRAINT FK_OrderItems_Order FOREIGN KEY (order_id) REFERENCES orders (id)
);

insert order_item (id,order_id,product_id,quantity,price)
values 
		-- huynq
		(1,1,1,1,14990000),
		(2,2,1,1,14990000),
        (3,2,2,1,15890000),
        (4,3,1,1,14990000),
        (5,3,2,1,15890000),
        (6,3,3,1,19990000),
        (7,4,4,1,32990000),
        (8,5,5,1,30890000), 
        -- minhbd
        (9,6,6,1,29990000),
        (10,7,7,1,24490000),
        (11,8,8,1,20990000),
        (12,9,8,1,20990000),
        (13,9,9,1,12490000),
        (14,10,8,1,20990000),
        (15,10,9,1,12490000),
        (16,10,10,1,18950000)
;

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

insert order_detail (id,order_id,address,tel)
values 	(1,1,'121 Tô ký, Phường 9, Quận 12, TP HCM','0987654321'),
		(2,2,'121 Tô ký, Phường 9, Quận 12, TP HCM','0987654321'),
        (3,3,'121 Tô ký, Phường 9, Quận 12, TP HCM','0987654321'),
        (4,4,'121 Tô ký, Phường 9, Quận 12, TP HCM','0987654321'),
        (5,5,'121 Tô ký, Phường 9, Quận 12, TP HCM','0987654321'),
        (6,6,'121 Tô ký, Phường 9, Quận 12, TP HCM','0345678921'),
        (7,7,'121 Tô ký, Phường 9, Quận 12, TP HCM','0345678921'),
        (8,8,'121 Tô ký, Phường 9, Quận 12, TP HCM','0345678921'),
        (9,9,'121 Tô ký, Phường 9, Quận 12, TP HCM','0345678921'),
        (10,10,'121 Tô ký, Phường 9, Quận 12, TP HCM','0345678921')
;

--------------------------------------------------------------------------------------------------------------
-- Giở hàng
--------------------------------------------------------------------------------------------------------------

CREATE TABLE cart (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT DEFAULT NULL,
  product_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP DEFAULT NULL,
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