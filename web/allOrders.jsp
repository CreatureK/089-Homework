<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="java.text.SimpleDateFormat" %>
      <%@ page import="model.Login" %>
        <%@ page import="model.Order" %>
          <!DOCTYPE html>
          <html>

          <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>我的订单 - 在线外卖订餐</title>
            <link rel="stylesheet" href="css/allOrders.css">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
          </head>

          <body>
            <% // 获取登录用户信息和订单列表 Login user=(Login) session.getAttribute("user"); List<Order> orders = (List<Order>)
                session.getAttribute("orders");

                // 如果未登录，重定向到登录页
                if (user == null) {
                response.sendRedirect("LoginController");
                return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                %>

                <header class="header">
                  <div class="container header-content">
                    <div class="logo">
                      <i class="fas fa-utensils"></i> 在线外卖订餐
                    </div>
                    <div class="user-info">
                      <span class="user-name"><i class="fas fa-user"></i>
                        <%= user.getUId() %> (<%= user.getUSchool() %> - <%= user.getUDepartment() %>)
                      </span>
                      <a href="LoginController" class="btn">退出登录</a>
                    </div>
                  </div>
                </header>

                <div class="container orders-container">
                  <h2 class="section-title">我的订单</h2>

                  <% if (orders==null || orders.isEmpty()) { %>
                    <div style="text-align: center; padding: 50px 0;">
                      <p><i class="fas fa-shopping-basket"
                          style="font-size: 64px; color: #86868b; margin-bottom: 20px;"></i></p>
                      <p style="font-size: 24px; color: #1d1d1f; margin-bottom: 10px;">暂无订单</p>
                      <p style="color: #86868b;">您当前没有任何订单记录。</p>
                    </div>
                    <% } else { %>
                      <div class="order-cards">
                        <% for (Order order : orders) { %>
                          <div class="order-card">
                            <div class="order-header">
                              <span class="order-id">订单号：<%= order.getOId() %></span>
                              <span class="order-status <%= getStatusClass(order.getStatus()) %>">
                                <%= order.getStatus() %>
                              </span>
                            </div>
                            <div class="order-body">
                              <div class="order-info">
                                <div class="order-time">下单时间：<%= sdf.format(order.getOTime()) %>
                                </div>
                                <div class="order-price">总价：¥<%= String.format("%.2f", order.getTotalPrice()) %>
                                </div>
                              </div>
                              <a href="javascript:void(0);" class="order-details-btn">查看详情</a>
                            </div>
                          </div>
                          <% } %>
                      </div>
                      <% } %>
                </div>

                <%! // 根据订单状态返回对应的CSS类名 private String getStatusClass(String status) { if ("处理中".equals(status)) {
                  return "status-processing" ; } else if ("已完成".equals(status)) { return "status-completed" ; } else if
                  ("已取消".equals(status)) { return "status-canceled" ; } return "" ; } %>

                  <script src="js/allOrders.js"></script>
          </body>

          </html>