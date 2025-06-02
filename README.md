# 项目说明书

## 项目架构与技术选型

本项目采用经典的MVC（Model-View-Controller）设计模式进行开发，具体技术选型如下：

- **View（视图层）**：采用JSP（Java Server Pages）技术，负责与用户的交互界面展示。
- **Controller（控制层）**：采用Servlet技术，负责接收用户请求、调用业务逻辑、分发和跳转页面。
- **Service（业务逻辑层）**：采用普通Java类实现，负责处理具体的业务逻辑。
- **POJO（数据交换对象）**：采用普通Java类，仅包含成员变量（属性），不包含成员函数，用于数据的封装与传递。
- **数据库访问层**：采用JDBC（Java Database Connectivity）进行数据库的连接与操作。

## 各层职责说明

- **JSP（View）**：
  - 展示数据和页面
  - 接收用户输入
  - 通过表单或URL将请求发送给Servlet

- **Servlet（Controller）**：
  - 作为前端控制器，接收和解析用户请求
  - 调用Service层处理业务逻辑
  - 将处理结果转发到JSP页面进行展示

- **Service（业务逻辑层）**：
  - 负责具体的业务处理，如数据校验、业务流程控制等
  - 调用DAO层进行数据持久化操作

- **POJO（数据对象）**：
  - 仅包含属性和getter/setter方法
  - 用于在各层之间传递数据

- **JDBC（数据库访问）**：
  - 负责与数据库的连接、增删改查等操作

## 典型流程

1. 用户在JSP页面进行操作，提交请求（如表单提交）。
2. Servlet接收请求，解析参数，调用Service层处理业务。
3. Service层处理业务逻辑，必要时通过JDBC访问数据库。
4. 处理结果通过Servlet转发到JSP页面展示。

## 约定
- 所有数据对象（POJO）仅包含成员变量和getter/setter方法，不包含业务逻辑。
- 所有业务逻辑均在Service层实现，Servlet只做请求分发和结果转发。
- 数据库操作全部通过JDBC实现，严禁在JSP或Servlet中直接操作数据库。

## 数据库设计

本项目使用MySQL数据库，数据库名为"089_JDBC"，包含以下表：

### 用户表(user)

存储学生信息，每行表示一个学生的基本信息。

| 字段名 | 数据类型 | 说明 |
|-------|---------|------|
| uId | INT | 主键，自动递增 |
| uName | VARCHAR(50) | 学生姓名，非空 |
| uPw | VARCHAR(50) | 学生密码，非空 |
| uSchool | VARCHAR(100) | 学生所属学院 |
| uDepartment | VARCHAR(100) | 学生所属系 |

### 菜肴表(food)

存储外卖菜肴信息，每行表示一种菜肴。

| 字段名 | 数据类型 | 说明 |
|-------|---------|------|
| fId | INT | 主键，自动递增 |
| fName | VARCHAR(100) | 菜肴名称，非空 |
| fPrice | DECIMAL(10,2) | 菜肴单价，非空 |
| fType | ENUM('在售', '停售', '缺货') | 菜肴状态，非空，默认'在售' |

### 订单表(order)

存储订单基本信息。

| 字段名 | 数据类型 | 说明 |
|-------|---------|------|
| oId | INT | 主键，自动递增 |
| uId | INT | 外键，关联用户表uId |
| oTime | DATETIME | 订单时间，非空 |
| totalPrice | DECIMAL(10,2) | 订单总价，非空 |
| status | ENUM('处理中', '已完成', '已取消') | 订单状态，非空，默认'处理中' |

### 订单明细表(orderDetail)

存储订单中的具体菜肴信息。

| 字段名 | 数据类型 | 说明 |
|-------|---------|------|
| oId | INT | 外键，与fId共同组成主键 |
| fId | INT | 外键，与oId共同组成主键 |
| quantity | INT | 订购数量，非空 |
| sum | DECIMAL(10,2) | 该菜肴的订购金额，非空 |

## 实体类设计

### 新增实体类

为了支持学院和系的下拉选择功能，项目新增以下实体类：

#### School（学院）实体类

- **描述**：封装学院信息
- **属性**：
  - name: 学院名称
  - departments: 该学院下属的系列表
- **主要方法**：
  - addDepartment(): 添加系到学院
  - getDepartmentNames(): 获取学院下所有系的名称列表

#### Department（系）实体类

- **描述**：封装系信息
- **属性**：
  - name: 系名称
  - school: 所属学院
- **主要方法**：
  - 基本的getter和setter方法

### 数据服务

新增SchoolDataService类，提供静态方法获取学院和系的数据，不依赖数据库：

- getAllSchoolNames(): 获取所有学院名称
- getDepartmentNamesBySchool(): 根据学院名称获取其下属系
- getAllSchoolsAndDepartments(): 获取所有学院和系的映射关系

## 运行说明

初始化数据库:

1. 确保已安装MySQL数据库服务器
2. 执行`create_database.sql`脚本创建数据库和初始数据:
   ```
   mysql -u username -p < create_database.sql
   ```
3. 修改项目中的数据库连接配置，确保与本地环境一致

## 学院和系数据

本项目使用实体类封装学院和系的数据，目前包含以下学院及其下属系：

1. **计算机学院**
   - 计算机科学与技术
   - 软件工程
   - 网络工程

2. **数学学院**
   - 应用数学
   - 统计学
   - 金融数学

3. **外国语学院**
   - 英语
   - 日语
   - 法语

4. **经济学院**
   - 经济学
   - 国际经济与贸易
   - 金融学

5. **艺术学院**
   - 音乐表演
   - 美术学
   - 设计学

## Tomcat兼容说明

本项目已更新为支持Tomcat 10.x版本，具体变更如下：

1. Web应用配置从JavaEE更新为Jakarta EE
   - web.xml文件使用Jakarta EE 5.0规范
   - Servlet API从javax.servlet包更新为jakarta.servlet包
   - 移除了控制器类上的@WebServlet注解，统一使用web.xml进行Servlet映射配置

2. 如需在Tomcat 9或更早版本部署，需回退以下更改：
   - web.xml文件命名空间改回为JavaEE 4.0
   - 控制器类中的jakarta.servlet包改回为javax.servlet

---

请严格按照上述架构和规范进行开发，后续所有功能、接口、参数、返回值等说明均会在本文件持续补充。 