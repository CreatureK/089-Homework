<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="java.util.Map" %>
      <!DOCTYPE html>
      <html>

      <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>用户登录 - 在线外卖订餐</title>
        <link rel="stylesheet" href="css/login.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
      </head>

      <body>
        <div class="login-container">
          <div class="login-form">
            <div class="form-title">
              <h1>在线外卖订餐</h1>
            </div>

            <form id="loginForm" action="LoginController" method="post">
              <div class="form-group">
                <label for="uId" class="form-label">学号</label>
                <input type="text" id="uId" name="uId" class="form-control" placeholder="请输入学号" required>
              </div>

              <div class="form-group">
                <label for="uPw" class="form-label">密码</label>
                <div class="password-container">
                  <input type="password" id="uPw" name="uPw" class="form-control" placeholder="请输入密码" required>
                  <span class="password-toggle" id="passwordToggle">
                    <i class="fas fa-eye-slash"></i>
                  </span>
                </div>
              </div>

              <div class="form-group">
                <label for="uSchool" class="form-label">所在学院</label>
                <select id="uSchool" name="uSchool" class="form-control" required>
                  <option value="">请选择</option>
                  <% List<String> schools = (List<String>)request.getAttribute("schools");
                      if (schools != null) {
                      for (String school : schools) {
                      %>
                      <option value="<%= school %>">
                        <%= school %>
                      </option>
                      <% } } %>
                </select>
              </div>

              <div class="form-group">
                <label for="uDepartment" class="form-label">所在系</label>
                <select id="uDepartment" name="uDepartment" class="form-control" required>
                  <option value="">请选择</option>
                  <% List<String> departments = (List<String>)request.getAttribute("departments");
                      if (departments != null) {
                      for (String department : departments) {
                      %>
                      <option value="<%= department %>">
                        <%= department %>
                      </option>
                      <% } } %>
                </select>
              </div>

              <div class="form-group">
                <label for="verifyCode" class="form-label">验证码</label>
                <div class="verify-code-container">
                  <input type="text" id="verifyCode" name="verifyCode" class="form-control" placeholder="请输入验证码"
                    required>
                  <img id="verifyCodeImg" class="verify-code-img" src="VerifyCodeController" alt="验证码" title="点击刷新验证码">
                </div>
              </div>

              <button type="submit" class="btn form-submit">登录</button>
            </form>
          </div>
        </div>

        <script src="js/login.js"></script>
      </body>

      </html>