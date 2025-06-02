package controller;

import model.Login;
import model.Order;
import service.LoginService;
import service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 登录控制器，处理登录相关请求
 */
public class LoginController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private LoginService loginService;
  private OrderService orderService;

  public LoginController() {
    super();
    loginService = new LoginService();
    orderService = new OrderService();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");

    if (action == null) {
      // 默认加载登录页面所需数据
      loadLoginPageData(request, response);
    } else if ("getDepartments".equals(action)) {
      // 获取指定学院的系列表
      String school = request.getParameter("school");
      List<String> departments = loginService.getDepartmentsBySchool(school);

      // 将系列表转换为JSON格式并返回
      response.setContentType("application/json;charset=utf-8");
      StringBuilder json = new StringBuilder("[");
      for (int i = 0; i < departments.size(); i++) {
        json.append("\"").append(departments.get(i)).append("\"");
        if (i < departments.size() - 1) {
          json.append(",");
        }
      }
      json.append("]");
      response.getWriter().write(json.toString());
      return;
    }

    request.getRequestDispatcher("/login.jsp").forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    // 获取登录表单数据
    String uIdStr = request.getParameter("uId");
    String uPw = request.getParameter("uPw");
    String uSchool = request.getParameter("uSchool");
    String uDepartment = request.getParameter("uDepartment");
    String verifyCode = request.getParameter("verifyCode");

    // 获取Session中的验证码
    HttpSession session = request.getSession();
    String sessionVerifyCode = (String) session.getAttribute("verifyCode");

    // 数据校验
    if (uIdStr == null || uIdStr.trim().isEmpty() ||
        uPw == null || uPw.trim().isEmpty() ||
        verifyCode == null || verifyCode.trim().isEmpty()) {
      request.setAttribute("errorMsg", "请填写完整的登录信息");
      request.getRequestDispatcher("/loginFailure.jsp").forward(request, response);
      return;
    }

    int uId;
    try {
      uId = Integer.parseInt(uIdStr.trim());
    } catch (NumberFormatException e) {
      request.setAttribute("errorMsg", "用户ID必须是数字");
      request.getRequestDispatcher("/loginFailure.jsp").forward(request, response);
      return;
    }

    // 封装登录信息
    Login login = new Login();
    login.setUId(uId);
    login.setUPw(uPw);
    login.setUSchool(uSchool);
    login.setUDepartment(uDepartment);
    login.setVerifyCode(verifyCode);

    // 调用业务逻辑层验证登录
    Object[] result = loginService.validateLogin(login, sessionVerifyCode);
    boolean isValid = (Boolean) result[0];
    String errorMsg = (String) result[1];

    if (isValid) {
      // 登录成功，获取用户订单数据
      List<Order> orders = orderService.getUserOrders(uId);

      // 将用户信息和订单数据存入session
      session.setAttribute("user", login);
      session.setAttribute("orders", orders);

      // 跳转到订单列表页面
      response.sendRedirect(request.getContextPath() + "/allOrders.jsp");
    } else {
      // 登录失败，携带错误信息跳转到失败页面
      request.setAttribute("errorMsg", errorMsg);
      request.getRequestDispatcher("/loginFailure.jsp").forward(request, response);
    }
  }

  /**
   * 加载登录页面所需的数据
   */
  private void loadLoginPageData(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 获取所有学院
    List<String> schools = loginService.getAllSchools();

    // 获取第一个学院的系列表
    List<String> departments = schools != null && !schools.isEmpty()
        ? loginService.getDepartmentsBySchool(schools.get(0))
        : null;

    // 获取所有学院和系的映射关系用于前端JS
    Map<String, List<String>> schoolDeptMap = loginService.getAllSchoolsAndDepartments();

    // 将数据存入request
    request.setAttribute("schools", schools);
    request.setAttribute("departments", departments);
    request.setAttribute("schoolDeptMap", schoolDeptMap);
  }
}