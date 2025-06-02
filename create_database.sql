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
(2025003, '2025-06-02 18:30:00', 102.00, '处理中'),
-- 新增订单数据，确保每个用户都有处理中、已完成、已取消三种状态的订单
(2025001, '2025-06-03 08:15:00', 58.50, '处理中'),
(2025001, '2025-06-02 21:30:00', 43.00, '已取消'),
(2025002, '2025-06-03 13:20:00', 77.50, '已取消'),
(2025003, '2025-06-03 19:45:00', 50.50, '已取消'),
(2025004, '2025-06-02 11:30:00', 63.00, '已完成'),
(2025004, '2025-06-03 20:15:00', 28.50, '已取消'),
(2025005, '2025-06-02 12:30:00', 86.00, '已完成'),
(2025005, '2025-06-03 18:20:00', 43.50, '处理中'),
-- 再次新增订单数据，确保每个用户各有2-3个不同状态的订单
-- 张三(2025001)新增订单：1个已完成、1个处理中、1个已取消，总共6个订单
(2025001, '2025-06-04 09:25:00', 92.50, '已完成'),
(2025001, '2025-06-04 13:40:00', 48.00, '处理中'),
(2025001, '2025-06-03 22:10:00', 36.50, '已取消'),
-- 李四(2025002)新增订单：2个已完成、2个处理中、1个已取消，总共7个订单
(2025002, '2025-06-03 08:50:00', 63.50, '已完成'),
(2025002, '2025-06-04 12:15:00', 52.00, '已完成'),
(2025002, '2025-06-04 18:30:00', 44.50, '处理中'),
(2025002, '2025-06-05 08:25:00', 38.00, '处理中'),
(2025002, '2025-06-03 21:35:00', 55.00, '已取消'),
-- 王五(2025003)新增订单：1个已完成、0个处理中、1个已取消，总共4个订单
(2025003, '2025-06-04 11:45:00', 75.50, '已完成'),
(2025003, '2025-06-04 19:20:00', 42.00, '已取消'),
-- 赵六(2025004)新增订单：1个已完成、2个处理中、1个已取消，总共6个订单
(2025004, '2025-06-04 10:50:00', 57.00, '已完成'),
(2025004, '2025-06-04 17:35:00', 49.50, '处理中'),
(2025004, '2025-06-05 09:15:00', 32.00, '处理中'),
(2025004, '2025-06-03 23:05:00', 41.50, '已取消'),
-- 陈七(2025005)新增订单：1个已完成、1个处理中、2个已取消，总共6个订单
(2025005, '2025-06-04 13:10:00', 69.50, '已完成'),
(2025005, '2025-06-04 19:50:00', 51.00, '处理中'),
(2025005, '2025-06-03 22:45:00', 43.00, '已取消'),
(2025005, '2025-06-05 08:10:00', 27.50, '已取消');

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

-- 新增订单详情数据
-- 订单9详情 (张三-处理中)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(9, 19, 1, 28.00),
(9, 6, 1, 18.00),
(9, 12, 1, 14.00),
(9, 15, 1, 3.00);

-- 订单10详情 (张三-已取消)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(10, 2, 1, 22.00),
(10, 10, 1, 20.00),
(10, 14, 1, 5.00);

-- 订单11详情 (李四-已取消)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(11, 3, 1, 32.00),
(11, 1, 1, 25.50),
(11, 13, 2, 19.00),
(11, 14, 1, 5.00);

-- 订单12详情 (王五-已取消)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(12, 20, 1, 36.00),
(12, 21, 1, 8.00),
(12, 8, 1, 15.00),
(12, 15, 1, 3.00);

-- 订单13详情 (赵六-已完成)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(13, 18, 1, 58.00),
(13, 22, 2, 4.00),
(13, 14, 1, 5.00);

-- 订单14详情 (赵六-已取消)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(14, 9, 1, 28.00),
(14, 15, 1, 3.00);

-- 订单15详情 (陈七-已完成)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(15, 4, 1, 45.00),
(15, 1, 1, 25.50),
(15, 7, 1, 12.50),
(15, 15, 1, 3.00);

-- 订单16详情 (陈七-处理中)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(16, 11, 2, 32.00),
(16, 12, 1, 14.00);

-- 新增第二批订单详情数据
-- 订单17详情 (张三-已完成)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(17, 4, 1, 45.00),
(17, 3, 1, 32.00),
(17, 7, 1, 12.50),
(17, 14, 1, 5.00);

-- 订单18详情 (张三-处理中)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(18, 6, 1, 18.00),
(18, 10, 1, 20.00),
(18, 13, 1, 9.50),
(18, 22, 1, 2.00);

-- 订单19详情 (张三-已取消)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(19, 1, 1, 25.50),
(19, 21, 1, 8.00),
(19, 14, 1, 5.00);

-- 订单20详情 (李四-已完成)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(20, 3, 1, 32.00),
(20, 8, 2, 30.00),
(20, 15, 1, 3.00);

-- 订单21详情 (李四-已完成)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(21, 19, 1, 28.00),
(21, 6, 1, 18.00),
(21, 8, 1, 15.00),
(21, 15, 1, 3.00);

-- 订单22详情 (李四-处理中)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(22, 2, 2, 44.00),
(22, 15, 1, 3.00);

-- 订单23详情 (李四-处理中)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(23, 6, 2, 36.00),
(23, 14, 1, 5.00);

-- 订单24详情 (李四-已取消)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(24, 1, 1, 25.50),
(24, 19, 1, 28.00),
(24, 15, 1, 3.00);

-- 订单25详情 (王五-已完成)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(25, 18, 1, 58.00),
(25, 7, 1, 12.50),
(25, 22, 2, 4.00),
(25, 14, 1, 5.00);

-- 订单26详情 (王五-已取消)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(26, 9, 1, 28.00),
(26, 20, 1, 36.00),
(26, 14, 1, 5.00);

-- 订单27详情 (赵六-已完成)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(27, 1, 1, 25.50),
(27, 3, 1, 32.00);

-- 订单28详情 (赵六-处理中)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(28, 5, 1, 38.00),
(28, 21, 1, 8.00),
(28, 14, 1, 5.00);

-- 订单29详情 (赵六-处理中)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(29, 6, 1, 18.00),
(29, 12, 1, 14.00),
(29, 15, 1, 3.00);

-- 订单30详情 (赵六-已取消)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(30, 1, 1, 25.50),
(30, 8, 1, 15.00),
(30, 14, 1, 5.00);

-- 订单31详情 (陈七-已完成)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(31, 3, 1, 32.00),
(31, 1, 1, 25.50),
(31, 12, 1, 14.00);

-- 订单32详情 (陈七-处理中)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(32, 8, 2, 30.00),
(32, 10, 1, 20.00),
(32, 14, 1, 5.00);

-- 订单33详情 (陈七-已取消)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(33, 2, 1, 22.00),
(33, 7, 1, 12.50),
(33, 21, 1, 8.00),
(33, 15, 1, 3.00);

-- 订单34详情 (陈七-已取消)
INSERT INTO `orderDetail` (`oId`, `fId`, `quantity`, `sum`) VALUES 
(34, 11, 1, 16.00),
(34, 12, 1, 14.00); 