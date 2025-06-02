<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <!DOCTYPE html>
  <html>

  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>操作失败 - 在线外卖订餐</title>
    <link rel="stylesheet" href="css/allOrders.css">
    <link rel="stylesheet" href="css/orderFailure.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  </head>

  <body>
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

    <div class="container">
      <div class="error-container">
        <p><i class="fas fa-exclamation-circle error-icon"></i></p>
        <h2 class="error-title">操作失败</h2>
        <% String errorMsg=(String) request.getAttribute("errorMsg"); %>
          <p class="error-message">
            <%= errorMsg !=null ? errorMsg : "处理您的订单请求时发生错误。" %>
          </p>
          <a href="OrderController" class="back-link">返回订单列表</a>
      </div>
    </div>
  </body>

  </html>