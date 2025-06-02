<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="java.text.SimpleDateFormat" %>
      <%@ page import="model.Login" %>
        <%@ page import="model.Order" %>
          <%@ page import="java.util.ArrayList" %>
            <%@ page import="dao.OrderDAO.OrderDetail" %>
              <!DOCTYPE html>
              <html>

              <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>全部订单 - 在线外卖订餐</title>
                <link rel="stylesheet" href="css/allOrders.css">
                <link rel="stylesheet"
                  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
              </head>

              <body>
                <% // 获取登录用户信息和订单列表
                    Login user=(Login) session.getAttribute("user"); List<Order> orders = (List<Order>)
                    session.getAttribute("orders");

                    // 确保订单列表不为null
                    if (orders == null) {
                    orders = new ArrayList<>();
                      }

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
                            <a href="LoginController" class="btn">退出登录</a>
                          </div>
                        </div>
                      </header>

                      <div class="container orders-container">
                        <div class="user-profile">
                          <i class="fas fa-user-circle"></i>
                          <div class="user-details">
                            <span class="user-detail">姓名：<%= user.getUName() %></span>
                            <span class="user-detail">学号：<%= user.getUId() %></span>
                            <span class="user-detail">学院：<%= user.getUSchool() %></span>
                            <span class="user-detail">专业：<%= user.getUDepartment() %></span>
                          </div>
                        </div>

                        <% // 显示成功消息（如果有）
                          String successMsg=(String) session.getAttribute("successMsg"); if (successMsg
                          !=null && !successMsg.isEmpty()) { // 显示成功消息后清除session中的消息
                          session.removeAttribute("successMsg"); %>
                          <div class="success-message">
                            <i class="fas fa-check-circle"></i>
                            <%= successMsg %>
                          </div>
                          <% } %>

                            <h2 class="section-title">全部订单</h2>

                            <% if (orders.isEmpty()) { %>
                              <div style="text-align: center; padding: 50px 0;">
                                <p><i class="fas fa-shopping-basket"
                                    style="font-size: 64px; color: #86868b; margin-bottom: 20px;"></i></p>
                                <p style="font-size: 24px; color: #1d1d1f; margin-bottom: 10px;">暂无订单</p>
                                <p style="color: #86868b;">您当前没有任何订单记录。</p>
                              </div>
                              <% } else { %>
                                <form id="orderForm" method="post">

                                  <div class="order-cards">
                                    <% for (Order order : orders) { %>
                                      <div class="order-card">
                                        <div class="order-header">
                                          <div style="display: flex; align-items: center;">
                                            <label class="order-checkbox-label">
                                              <input type="checkbox" name="selectedOrders" value="<%= order.getOId() %>"
                                                class="order-checkbox">
                                              <span class="order-id">订单ID：<%= order.getOId() %></span>
                                            </label>
                                          </div>
                                          <span class="order-status <%= getStatusClass(order.getStatus()) %>">
                                            <%= order.getStatus() %>
                                          </span>
                                        </div>
                                        <div class="order-body">
                                          <div class="order-info">
                                            <div class="order-time">下单时间：<%= sdf.format(order.getOTime()) %>
                                            </div>

                                            <% if (order.getOrderDetails() !=null && !order.getOrderDetails().isEmpty())
                                              { %>
                                              <div class="order-items">
                                                <div>订购菜肴：</div>
                                                <ul class="food-list">
                                                  <% for (OrderDetail detail : order.getOrderDetails()) { %>
                                                    <li>
                                                      <%= detail.getFood().getFName() %>
                                                        - ¥<%= String.format("%.2f", detail.getFood().getFPrice()) %>
                                                          × <%= detail.getQuantity() %>
                                                    </li>
                                                    <% } %>
                                                </ul>
                                              </div>
                                              <% } %>

                                                <div class="order-price">订单总价：¥<%= String.format("%.2f",
                                                    order.getTotalPrice()) %>
                                                </div>
                                          </div>
                                        </div>
                                      </div>
                                      <% } %>
                                  </div>

                                  <!-- 添加到订单卡片下方的操作按钮 -->
                                  <div class="order-actions order-actions-global">
                                    <button type="button" id="deleteOrderBtn" class="action-btn delete-btn">
                                      删除订单
                                    </button>
                                    <button type="button" id="cancelOrderBtn" class="action-btn cancel-btn">
                                      撤销订单
                                    </button>
                                  </div>
                                </form>
                                <% } %>
                      </div>

                      <%! // 根据订单状态返回对应的CSS类名
                        private String getStatusClass(String status) { if ("处理中".equals(status)) {
                        return "status-processing" ; } else if ("已完成".equals(status)) { return "status-completed" ; }
                        else if ("已取消".equals(status)) { return "status-canceled" ; } return "" ; } %>

                        <script src="js/allOrders.js"></script>
              </body>

              </html>