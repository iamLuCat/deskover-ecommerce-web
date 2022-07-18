use deskover;
SELECT SUM(quantity*price) as 'totalPrice' FROM order_item WHERE id =1;

SELECT count(orders.modified_by) as 'totalOrder' from orders where month(created_at) = '07'  and modified_by='minhnh';

SELECT sum(order_item.quantity * order_item.price) as 'total',product.id,product.name
from product inner join order_item on product.id = order_item.product_id
GROUP BY product_id;

SELECT sum(order_item.quantity * order_item.price) as 'totalOrder'  from orders join order_item on orders.id = order_item.order_id where order_item.order_id = '3' group by order_item.order_id;

SELECT count(*) from orders join status_order on orders.status_id = status_order.id  where status_order.`code` = 'GH-TC';

-- tìm tổng tiền theo loại
SELECT category.name,  sum(order_item.quantity * order_item.price) as 'totalProduct'
from 
	category 
		join subcategory
					on subcategory.category_id = category.id 
		inner join product 
					on subcategory.id = product.sub_category_id 

		join order_item 
					on product.id = order_item.product_id
GROUP BY category.id;





SELECT sum(order_item.quantity * order_item.price),order_item.product_id from order_item GROUP BY product_id;  

-- tổng tiền của đơn hàng
DROP procedure IF EXISTS `totalOrder`;
DELIMITER $$
CREATE PROCEDURE `totalOrder`(IN `id` int)
BEGIN
	DECLARE totalOrder INT DEFAULT 0;
	SET totalOrder = 
    ( 
			SELECT sum(order_item.quantity * order_item.price) as 'totalOrder'  
            from orders 
					join order_item on orders.id = order_item.order_id 
			where 
					order_item.order_id = `id` 
			group by order_item.order_id
    );
    SELECT totalOrder;
END$$

DELIMITER ;
;
call deskover.totalOrder('3');

-- doanh thu ngay
DROP procedure IF EXISTS `getTotalPrice_Shipping_PerDay`;
DELIMITER $$
CREATE PROCEDURE `getTotalPrice_Shipping_PerDay`(
					IN `day` varchar(2),
					IN `month` varchar(2),
					IN `year` varchar(4),
					IN modified_by varchar(50),
					IN `code` varchar(5))
BEGIN
	DECLARE totalPricePerDay varchar(20) DEFAULT 0;
	SET totalPricePerDay = 
    ( 
					SELECT SUM( order_item.quantity * order_item.price) as 'total'
			FROM 
				orders 
					INNER JOIN order_item ON orders.id = order_item.order_id
                    JOIN status_order ON orders.status_id = status_order.id
			WHERE
                DAY(orders.created_at) = `day`
                AND MONTH(orders.created_at)= `month`
                AND YEAR(orders.created_at) = `year`
                AND status_order.`code` = `code`
                AND orders.modified_by = modified_by
    );
    SELECT totalPricePerDay;
END$$

DELIMITER ;
;
call deskover.getTotalPrice_Shipping_PerDay('10', '07', '2022', 'minhnh','GH-TC');


-- doanh thu thang shipper

DROP procedure IF EXISTS `getTotalPrice_Shiping_PerMonth`;

DELIMITER $$
CREATE  PROCEDURE `getTotalPrice_Shiping_PerMonth`(IN `month` varchar(2),IN `year` varchar(4), IN modified_by varchar(50))
BEGIN
		DECLARE totalPricePerMonth varchar(20) DEFAULT 0;
		SET totalPricePerMonth = 
    ( 
					SELECT SUM( order_item.quantity * order_item.price) as 'Total'
			FROM 
				orders 
					INNER JOIN order_item ON orders.id = order_item.order_id
                    JOIN status_order ON orders.status_id = status_order.id
			WHERE
				MONTH(orders.created_at)= `month`
                AND YEAR(orders.created_at) = `year`
                AND status_order.`code` = 'GH-TC'
                AND orders.modified_by = modified_by
    );
    SELECT totalPricePerMonth;
END$$
DELIMITER ;
;
call deskover.getTotalPrice_Shiping_PerMonth('07', '2022', 'minhnh');

-- Tổng số đơn hàng giao thành công của tháng

DROP procedure IF EXISTS `countOrder`;

DELIMITER $$
CREATE  PROCEDURE `countOrder`(IN `month` varchar(2),IN `year` varchar(4), IN modified_by varchar(50))
BEGIN
		DECLARE countOrder varchar(20) DEFAULT 0;
		SET countOrder = 
    ( 
			SELECT count(*)
            from orders 
				join status_order 
					on orders.status_id = status_order.id  
				where 
					MONTH(orders.created_at)= `month`
					AND YEAR(orders.created_at) = `year`
					AND status_order.`code` = 'GH-TC'
					AND orders.modified_by = modified_by
    );
    SELECT countOrder;
END$$
DELIMITER ;
;
call deskover.countOrder('07', '2022', 'minhnh');

-- doanh thu nam
use deskover;
DROP procedure IF EXISTS `getTotalPricePerYear`;

DELIMITER $$
CREATE PROCEDURE `getTotalPricePerYear` (IN `month` varchar(2),IN `year` varchar(4))
BEGIN
	DECLARE totalPricePerYear varchar(20) DEFAULT 0;
		SET totalPricePerYear = 
    ( 
					SELECT SUM( order_item.quantity * order_item.price) as 'Total_year'
			FROM 
				orders 
					INNER JOIN order_item ON orders.id = order_item.order_id
			WHERE
			     YEAR(orders.created_at) = `year`
                 AND MONTH(orders.created_at) = `month`
    );
   SELECT IF(totalPricePerYear>0, totalPricePerYear, 0);
END$$
DELIMITER ;

call deskover.getTotalPricePerYear('07','2022');

-- Doanh thu tháng

DROP procedure IF EXISTS `totalOrderPerMonth`;

DELIMITER $$
CREATE PROCEDURE `totalOrderPerMonth` (IN `month` varchar(2), IN modified_by varchar(50))
BEGIN
	DECLARE totalOrder varchar(20) DEFAULT 0;
    SET totalOrder =(
		SELECT count(orders.modified_by) as 'totalOrder' 
			from orders 
			where 
				orders.modified_by= modified_by
				and month(orders.created_at) = `month`  
				
    );
    SELECT totalOrder;
END$$
DELIMITER ;

-- Doanh thu theo loại

USE `deskover`;
DROP procedure IF EXISTS `totalByNameCategory`;

-- price
DELIMITER $$
USE `deskover`$$
CREATE PROCEDURE `totalByNameCategory` (IN `month` varchar(2),IN `year` varchar(4))
BEGIN
	SELECT category.name as 'totalProduct'
	from 
		category 
			join subcategory
						on subcategory.category_id = category.id 
			inner join product 
						on subcategory.id = product.sub_category_id 

			join order_item 
						on product.id = order_item.product_id
			join orders
						on order_item.order_id = orders.id
			where
						month(orders.created_at) = `month`
						and year(orders.created_at) = `year`
	GROUP BY category.id;
	
END$$

DELIMITER ;

call deskover.totalByNameCategory('07', '2022');


USE `deskover`;
DROP procedure IF EXISTS `totalPriceByCategory`;

-- name.
DELIMITER $$
USE `deskover`$$
CREATE PROCEDURE `totalPriceByCategory` (IN `month` varchar(2),IN `year` varchar(4))
BEGIN
	SELECT  sum(order_item.quantity * order_item.price) as 'totalProduct'
	from 
		category 
			join subcategory
						on subcategory.category_id = category.id 
			inner join product 
						on subcategory.id = product.sub_category_id 

			join order_item 
						on product.id = order_item.product_id
			join orders
						on order_item.order_id = orders.id
			where
						month(orders.created_at) = `month`
						and year(orders.created_at) = `year`
	GROUP BY category.id;
	
END$$
DELIMITER ;

USE `deskover`;
DROP procedure IF EXISTS `getToTalByCategory`;
DELIMITER $$
USE `deskover`$$
CREATE PROCEDURE `getToTalByCategory` (IN `month` varchar(2),IN `year` varchar(4))
BEGIN
	SELECT category.name,  sum(order_item.quantity * order_item.price) as 'totalProduct'
	from 
		category 
			join subcategory
						on subcategory.category_id = category.id 
			inner join product 
						on subcategory.id = product.sub_category_id 

			join order_item 
						on product.id = order_item.product_id
			join orders
						on order_item.order_id = orders.id
			where
						month(orders.created_at) = `month`
						and year(orders.created_at) = `year`
	GROUP BY category.id;
	
END$$
DELIMITER ;

call deskover.getToTalByCategory('07', '2022');





