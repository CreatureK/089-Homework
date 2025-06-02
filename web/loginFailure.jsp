<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <!DOCTYPE html>
  <html>

  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录失败 - 在线外卖订餐</title>
    <link rel="stylesheet" href="css/loginFailure.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  </head>

  <body>
    <div class="container">
      <div class="error-container">
        <div class="error-icon">
          <i class="fas fa-exclamation-circle"></i>
        </div>

        <h1 class="error-title">登录失败</h1>

        <% String errorMsg=(String) request.getAttribute("errorMsg"); %>
          <p class="error-message">
            <% if (errorMsg !=null && !errorMsg.isEmpty()) { %>
              <%= errorMsg %>
                <% } else { %>
                  用户名或密码错误，请检查后重试。
                  <% } %>
          </p>

          <div class="error-tips">
            <% if (errorMsg !=null && errorMsg.contains("验证码")) { %>
              <p>提示：请确认您输入的验证码正确无误。如果看不清，可以点击验证码图片刷新。</p>
              <% } else if (errorMsg !=null && errorMsg.contains("用户名或密码")) { %>
                <p>提示：请确认您输入的学号和密码正确无误。</p>
                <% } else { %>
                  <p>提示：请确认所有信息填写正确，然后重试。</p>
                  <% } %>
          </div>

          <a href="LoginController" class="btn">重新登录</a>
      </div>
    </div>

    <script src="js/loginFailure.js"></script>
  </body>

  </html>