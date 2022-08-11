USE deskover;
SELECT SUM(quantity * price) AS 'totalPrice'
FROM order_item
WHERE id = 1;

SELECT COUNT(orders.modified_by) AS 'totalOrder'
FROM orders
WHERE MONTH(created_at) = '07'
  AND modified_by = 'minhnh';

SELECT SUM(order_item.quantity * order_item.price) AS 'total', product.id, product.name
FROM product
         INNER JOIN order_item ON product.id = order_item.product_id
GROUP BY product_id;

SELECT SUM(order_item.quantity * order_item.price) AS 'totalOrder'
FROM orders
         JOIN order_item ON orders.id = order_item.order_id
WHERE order_item.order_id = '3'
GROUP BY order_item.order_id;

SELECT COUNT(*)
FROM orders
         JOIN status_order ON orders.status_id = status_order.id
WHERE status_order.`code` = 'GH-TC';

-- tìm tổng tiền theo loại
SELECT category.name, SUM(order_item.quantity * order_item.price) AS 'totalProduct'
FROM category
         JOIN subcategory
              ON subcategory.category_id = category.id
         INNER JOIN product
                    ON subcategory.id = product.sub_category_id

         JOIN order_item
              ON product.id = order_item.product_id
GROUP BY category.id;



SELECT SUM(order_item.quantity * order_item.price), order_item.product_id
FROM order_item
GROUP BY product_id;

-- tổng tiền của đơn hàng
DROP PROCEDURE IF EXISTS `totalOrder`;
DELIMITER $$
CREATE PROCEDURE `totalOrder`(IN `id` INT)
BEGIN
    DECLARE totalOrder INT DEFAULT 0;
    SET totalOrder =
            (SELECT SUM(order_item.quantity * order_item.price) AS 'totalOrder'
             FROM orders
                      JOIN order_item ON orders.id = order_item.order_id
             WHERE order_item.order_id = `id`
             GROUP BY order_item.order_id);
    SELECT totalOrder;
END$$

DELIMITER ;
;
CALL deskover.totalOrder('3');

-- doanh thu ngay
DROP PROCEDURE IF EXISTS `getTotalPrice_Shipping_PerDay`;
DELIMITER $$
CREATE PROCEDURE `getTotalPrice_Shipping_PerDay`(
    IN `day` VARCHAR(2),
    IN `month` VARCHAR(2),
    IN `year` VARCHAR(4),
    IN modified_by VARCHAR(50),
    IN `code` VARCHAR(5))
BEGIN
    DECLARE totalPricePerDay VARCHAR(20) DEFAULT 0;
    SET totalPricePerDay =
            (SELECT SUM(order_item.quantity * order_item.price) AS 'total'
             FROM orders
                      INNER JOIN order_item ON orders.id = order_item.order_id
                      JOIN status_order ON orders.status_id = status_order.id
             WHERE DAY(orders.created_at) = `day`
               AND MONTH(orders.created_at) = `month`
               AND YEAR(orders.created_at) = `year`
               AND status_order.`code` = `code`
               AND orders.modified_by = modified_by);
    SELECT totalPricePerDay;
END$$

DELIMITER ;
;
CALL deskover.getTotalPrice_Shipping_PerDay('10', '07', '2022', 'minhnh', 'GH-TC');


-- doanh thu thang shipper

DROP PROCEDURE IF EXISTS `getTotalPrice_Shiping_PerMonth`;

DELIMITER $$
CREATE PROCEDURE `getTotalPrice_Shiping_PerMonth`(IN `month` VARCHAR(2), IN `year` VARCHAR(4),
                                                  IN modified_by VARCHAR(50))
BEGIN
    DECLARE totalPricePerMonth VARCHAR(20) DEFAULT 0;
    SET totalPricePerMonth =
            (SELECT SUM(order_item.quantity * order_item.price) AS 'Total'
             FROM orders
                      INNER JOIN order_item ON orders.id = order_item.order_id
                      JOIN status_order ON orders.status_id = status_order.id
             WHERE MONTH(orders.created_at) = `month`
               AND YEAR(orders.created_at) = `year`
               AND status_order.`code` = 'GH-TC'
               AND orders.modified_by = modified_by);
    SELECT totalPricePerMonth;
END$$
DELIMITER ;
;
CALL deskover.getTotalPrice_Shiping_PerMonth('07', '2022', 'minhnh');

-- Tổng số đơn hàng giao thành công của tháng

DROP PROCEDURE IF EXISTS `countOrder`;

DELIMITER $$
CREATE PROCEDURE `countOrder`(IN `month` VARCHAR(2), IN `year` VARCHAR(4), IN modified_by VARCHAR(50))
BEGIN
    DECLARE countOrder VARCHAR(20) DEFAULT 0;
    SET countOrder =
            (SELECT COUNT(*)
             FROM orders
                      JOIN status_order
                           ON orders.status_id = status_order.id
             WHERE MONTH(orders.created_at) = `month`
               AND YEAR(orders.created_at) = `year`
               AND status_order.`code` = 'GH-TC'
               AND orders.modified_by = modified_by);
    SELECT countOrder;
END$$
DELIMITER ;

CALL deskover.countOrder('07', '2022', 'minhnh');

-- doanh thu nam
USE deskover;
DROP PROCEDURE IF EXISTS `getTotalPricePerYear`;

DELIMITER $$
CREATE PROCEDURE `getTotalPricePerYear`(IN `month` VARCHAR(2), IN `year` VARCHAR(4))
BEGIN
    DECLARE totalPricePerYear VARCHAR(20) DEFAULT 0;
    SET totalPricePerYear =
            (SELECT SUM(order_item.quantity * order_item.price) AS 'Total_year'
             FROM orders
                      INNER JOIN order_item ON orders.id = order_item.order_id
             WHERE YEAR(orders.created_at) = `year`
               AND MONTH(orders.created_at) = `month`);
    SELECT IF(totalPricePerYear > 0, totalPricePerYear, 0);
END$$
DELIMITER ;

CALL deskover.getTotalPricePerYear('06', '2022');

-- Doanh thu tháng

DROP PROCEDURE IF EXISTS `totalOrderPerMonth`;

DELIMITER $$
CREATE PROCEDURE `totalOrderPerMonth`(IN `month` VARCHAR(2), IN modified_by VARCHAR(50))
BEGIN
    DECLARE totalOrder VARCHAR(20) DEFAULT 0;
    SET totalOrder = (SELECT COUNT(orders.modified_by) AS 'totalOrder'
                      FROM orders
                      WHERE orders.modified_by = modified_by
                        AND MONTH(orders.created_at) = `month`);
    SELECT totalOrder;
END$$
DELIMITER ;

-- Doanh thu theo loại

USE `deskover`;
DROP PROCEDURE IF EXISTS `getTotalByCategory`;
DELIMITER $$
USE `deskover`$$
CREATE PROCEDURE `getToTalByCategory`(IN `month` VARCHAR(2), IN `year` VARCHAR(4))
BEGIN
    SELECT category.name, SUM(order_item.quantity * order_item.price) AS 'totalProduct'
    FROM category
             JOIN subcategory
                  ON subcategory.category_id = category.id
             INNER JOIN product
                        ON subcategory.id = product.sub_category_id

             JOIN order_item
                  ON product.id = order_item.product_id
             JOIN orders
                  ON order_item.order_id = orders.id
    WHERE MONTH(orders.created_at) = `month`
      AND YEAR(orders.created_at) = `year`
    GROUP BY category.id;

END$$
DELIMITER ;

CALL deskover.getToTalByCategory('07', '2022');

-- Tổng doanh thu các đơn giao thành công
DROP PROCEDURE IF EXISTS `countOrder`;

DELIMITER $$
CREATE PROCEDURE `countOrder`(IN `month` VARCHAR(2), IN `year` VARCHAR(4), IN modified_by VARCHAR(50))
BEGIN
    DECLARE countOrder VARCHAR(20) DEFAULT 0;
    SET countOrder =
            (SELECT COUNT(*)
             FROM orders
                      JOIN status_order
                           ON orders.status_id = status_order.id
             WHERE MONTH(orders.created_at) = `month`
               AND YEAR(orders.created_at) = `year`
               AND status_order.`code` = 'GH-TC'
               AND orders.modified_by = modified_by);
    SELECT countOrder;
END$$
DELIMITER ;

CALL deskover.countOrder('07', '2022', 'minhnh');


