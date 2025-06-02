-- 创建数据库
CREATE DATABASE IF NOT EXISTS `089_JDBC` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 使用创建的数据库
USE `089_JDBC`;

-- 创建user表
CREATE TABLE IF NOT EXISTS `user` (
  `uId` INT NOT NULL,
  `uName` VARCHAR(50) NOT NULL,
  `uPw` VARCHAR(50) NOT NULL,
  `uSchool` VARCHAR(100),
  `uDepartment` VARCHAR(100),
  PRIMARY KEY (`uId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建food表
CREATE TABLE IF NOT EXISTS `food` (
  `fId` INT NOT NULL AUTO_INCREMENT,
  `fName` VARCHAR(100) NOT NULL,
  `fPrice` DECIMAL(10,2) NOT NULL,
  `fType` ENUM('在售', '停售', '缺货') NOT NULL DEFAULT '在售',
  PRIMARY KEY (`fId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建order表
CREATE TABLE IF NOT EXISTS `order` (
  `oId` INT NOT NULL AUTO_INCREMENT,
  `uId` INT NOT NULL,
  `oTime` DATETIME NOT NULL,
  `totalPrice` DECIMAL(10,2) NOT NULL,
  `status` ENUM('处理中', '已完成', '已取消') NOT NULL DEFAULT '处理中',
  PRIMARY KEY (`oId`),
  FOREIGN KEY (`uId`) REFERENCES `user`(`uId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建orderDetail表
CREATE TABLE IF NOT EXISTS `orderDetail` (
  `oId` INT NOT NULL,
  `fId` INT NOT NULL,
  `quantity` INT NOT NULL,
  `sum` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`oId`, `fId`),
  FOREIGN KEY (`oId`) REFERENCES `order`(`oId`) ON DELETE CASCADE,
  FOREIGN KEY (`fId`) REFERENCES `food`(`fId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入用户数据
INSERT INTO `user` (`uId`, `uName`, `uPw`, `uSchool`, `uDepartment`) VALUES 
(2025001, '张三', '123a', '计算机学院', '软件工程'),
(2025002, '李四', '123a', '电子信息学院', '通信工程'),
(2025003, '王五', '123a', '经济管理学院', '金融学'),
(2025004, '赵六', '123a', '外国语学院', '英语'),
(2025005, '陈七', '123a', '数学学院', '应用数学');

-- 插入菜肴数据
INSERT INTO `food` (`fName`, `fPrice`, `fType`) VALUES 
('宫保鸡丁', 25.50, '在售'),
('鱼香肉丝', 22.00, '在售'),
('红烧排骨', 32.00, '在售'),
('酸菜鱼', 45.00, '在售'),
('水煮肉片', 38.00, '在售'),
('麻婆豆腐', 18.00, '在售'),
('炒青菜', 12.50, '在售'),
('番茄炒蛋', 15.00, '在售'),
('红烧牛肉面', 28.00, '在售'),
('扬州炒饭', 20.00, '在售'),
('小笼包', 16.00, '在售'),
('蒸饺', 14.00, '在售'),
('芒果布丁', 9.50, '在售'),
('可乐', 5.00, '在售'),
('矿泉水', 3.00, '在售'),
('烤鸭', 68.00, '停售'),
('北京烤鸭', 88.00, '缺货'),
('清蒸鲈鱼', 58.00, '在售'),
('干锅土豆片', 28.00, '在售'),
('辣子鸡', 36.00, '在售'),
('鸡蛋汤', 8.00, '在售'),
('米饭', 2.00, '在售');

-- 插入订单数据
INSERT INTO `order` (`uId`, `oTime`, `totalPrice`, `status`) VALUES 
(2025001, '2025-05-30 12:10:00', 65.00, '已完成'),
(2025002, '2025-05-30 18:30:00', 88.00, '已完成'),
(2025003, '2025-05-31 13:45:00', 48.00, '已完成'),
(2025004, '2025-05-31 19:20:00', 45.00, '处理中'),
(2025005, '2025-06-01 11:55:00', 38.00, '已取消'),
(2025001, '2025-06-01 20:05:00', 72.00, '已完成'),
(2025002, '2025-06-02 12:40:00', 56.50, '处理中'),
(2025003, '2025-06-02 18:30:00', 102.00, '处理中');

-- 插入订单详情数据
-- 订单1详情
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(1, 1, 2, 51.00),
(1, 7, 1, 12.50),
(1, 14, 1, 1.50);

-- 订单2详情
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(2, 4, 1, 45.00),
(2, 7, 2, 25.00),
(2, 15, 6, 18.00);

-- 订单3详情
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(3, 6, 2, 36.00),
(3, 14, 1, 5.00),
(3, 15, 2, 6.00),
(3, 21, 1, 8.00);

-- 订单4详情
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(4, 4, 1, 45.00),
(4, 2, 2, 44.00);

-- 订单5详情
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(5, 5, 1, 38.00),
(5, 8, 2, 30.00);

-- 订单6详情
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(6, 3, 1, 32.00),
(6, 8, 2, 30.00),
(6, 22, 5, 10.00);

-- 订单7详情
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(7, 9, 1, 28.00),
(7, 10, 1, 20.00),
(7, 14, 1, 5.00),
(7, 21, 1, 8.00);

-- 订单8详情
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(8, 18, 1, 58.00),
(8, 5, 1, 38.00),
(8, 15, 2, 6.00); 