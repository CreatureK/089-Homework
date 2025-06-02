# 在线外卖订餐系统

这是一个基于Java Web的在线外卖订餐系统，提供用户登录、查看订单、取消订单和删除订单等功能。

## 功能概述

### 用户登录
- 用户可以通过学号、密码、学院、专业和验证码进行登录
- 登录成功后跳转到订单列表页面
- 登录失败显示错误信息

### 订单管理
- 查看所有订单：显示订单ID、状态、下单时间、订购菜肴和订单总价
- 删除订单：可以删除"已完成"状态的订单
- 取消订单：可以取消"处理中"状态的订单，取消后订单状态变更为"已取消"
- 批量操作：支持批量删除和批量取消订单

## 技术架构

- 前端：JSP, HTML, CSS, JavaScript
- 后端：Java Servlet
- 数据库：MySQL

## 项目结构

```
├── src/                      # 源代码目录
│   ├── controller/           # 控制器类
│   │   ├── LoginController.java     # 登录控制器
│   │   ├── OrderController.java     # 订单控制器
│   │   └── VerifyCodeController.java # 验证码控制器
│   ├── dao/                  # 数据访问对象
│   │   ├── FoodDAO.java      # 菜肴数据访问
│   │   ├── LoginDAO.java     # 登录数据访问
│   │   └── OrderDAO.java     # 订单数据访问
│   ├── model/                # 实体类
│   │   ├── Food.java         # 菜肴实体类
│   │   ├── Login.java        # 登录实体类
│   │   └── Order.java        # 订单实体类
│   ├── service/              # 业务逻辑层
│   │   ├── LoginService.java # 登录业务逻辑
│   │   └── OrderService.java # 订单业务逻辑
│   └── util/                 # 工具类
│       ├── DBUtil.java       # 数据库工具类
│       └── VerifyCodeUtil.java # 验证码工具类
├── web/                      # Web资源目录
│   ├── css/                  # CSS样式文件
│   ├── js/                   # JavaScript文件
│   ├── WEB-INF/              # Web配置文件
│   │   └── web.xml           # Web应用配置
│   ├── allOrders.jsp         # 订单列表页面
│   ├── login.jsp             # 登录页面
│   ├── loginFailure.jsp      # 登录失败页面
│   ├── orderFailure.jsp      # 订单操作失败页面
│   └── index.jsp             # 首页（重定向到登录）
└── README.md                 # 项目说明文档
```

## 使用说明

### 订单删除功能
- 选中一个或多个订单（通过订单列表中的复选框）
- 点击"删除订单"按钮或批量删除按钮
- 系统会检查所选订单是否为"已完成"状态
- 删除成功后会显示成功信息
- 删除失败会跳转到失败页面并显示原因

### 订单取消功能
- 选中一个或多个订单（通过订单列表中的复选框）
- 点击"撤销订单"按钮或批量取消按钮
- 系统会检查所选订单是否为"处理中"状态
- 取消成功后会显示成功信息
- 取消失败会跳转到失败页面并显示原因

## 数据库设计

系统使用MySQL数据库，主要包含以下表：
- `user`：用户信息表
- `food`：菜肴信息表
- `order`：订单主表
- `orderDetail`：订单详情表 