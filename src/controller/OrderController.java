package controller;

import model.Login;
import model.Order;
import service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单控制器，处理订单相关请求
 */
public class OrderController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private OrderService orderService;

  public OrderController() {
    super();
    orderService = new OrderService();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");

    // 验证用户是否登录
    HttpSession session = request.getSession();
    Login user = (Login) session.getAttribute("user");

    if (user == null) {
      response.sendRedirect("LoginController");
      return;
    }

    // 根据action参数执行对应的操作
    if (action == null) {
      // 默认加载用户的所有订单
      loadUserOrders(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String action = request.getParameter("action");

    // 验证用户是否登录
    HttpSession session = request.getSession();
    Login user = (Login) session.getAttribute("user");

    if (user == null) {
      response.sendRedirect("LoginController");
      return;
    }

    // 根据action参数执行对应的操作
    if ("deleteOrders".equals(action)) {
      deleteOrders(request, response);
    } else if ("cancelOrders".equals(action)) {
      cancelOrders(request, response);
    }
  }

  /**
   * 加载用户的所有订单
   */
  private void loadUserOrders(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();
    Login user = (Login) session.getAttribute("user");

    if (user != null) {
      // 获取用户的所有订单（包含菜肴详情）
      List<Order> orders = orderService.getUserOrdersWithDetails(user.getUId());

      // 将订单数据存入session
      session.setAttribute("orders", orders);

      // 跳转到订单列表页面
      request.getRequestDispatcher("/allOrders.jsp").forward(request, response);
    } else {
      response.sendRedirect("LoginController");
    }
  }

  /**
   * 处理删除订单请求
   */
  private void deleteOrders(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String[] orderIdStrArray = request.getParameterValues("selectedOrders");

    if (orderIdStrArray == null || orderIdStrArray.length == 0) {
      request.setAttribute("errorMsg", "请选择要删除的订单");
      request.getRequestDispatcher("/orderFailure.jsp").forward(request, response);
      return;
    }

    try {
      // 将字符串数组转换为整型数组
      int[] orderIds = Arrays.stream(orderIdStrArray)
          .mapToInt(Integer::parseInt)
          .toArray();

      // 批量删除订单
      List<Integer> successfulIds = orderService.batchDeleteOrders(orderIds);

      HttpSession session = request.getSession();
      Login user = (Login) session.getAttribute("user");

      if (successfulIds.isEmpty()) {
        // 如果没有成功删除的订单，跳转到失败页面
        request.setAttribute("errorMsg", "没有符合删除条件的订单。只有已完成状态的订单才能删除。");
        request.getRequestDispatcher("/orderFailure.jsp").forward(request, response);
      } else {
        // 如果有成功删除的订单
        String successMsg = "成功删除以下订单: " + successfulIds.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(", "));

        // 重新获取用户的所有订单
        List<Order> orders = orderService.getUserOrdersWithDetails(user.getUId());
        session.setAttribute("orders", orders);

        // 设置成功消息
        session.setAttribute("successMsg", successMsg);

        // 重定向到订单列表页面
        response.sendRedirect("OrderController");
      }
    } catch (NumberFormatException e) {
      request.setAttribute("errorMsg", "订单ID格式错误");
      request.getRequestDispatcher("/orderFailure.jsp").forward(request, response);
    }
  }

  /**
   * 处理取消订单请求
   */
  private void cancelOrders(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String[] orderIdStrArray = request.getParameterValues("selectedOrders");

    if (orderIdStrArray == null || orderIdStrArray.length == 0) {
      request.setAttribute("errorMsg", "请选择要取消的订单");
      request.getRequestDispatcher("/orderFailure.jsp").forward(request, response);
      return;
    }

    try {
      // 将字符串数组转换为整型数组
      int[] orderIds = Arrays.stream(orderIdStrArray)
          .mapToInt(Integer::parseInt)
          .toArray();

      // 批量取消订单
      List<Integer> successfulIds = orderService.batchCancelOrders(orderIds);

      HttpSession session = request.getSession();
      Login user = (Login) session.getAttribute("user");

      if (successfulIds.isEmpty()) {
        // 如果没有成功取消的订单，跳转到失败页面
        request.setAttribute("errorMsg", "没有符合取消条件的订单。只有处理中状态的订单才能取消。");
        request.getRequestDispatcher("/orderFailure.jsp").forward(request, response);
      } else {
        // 如果有成功取消的订单
        String successMsg = "成功取消以下订单: " + successfulIds.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(", "));

        // 重新获取用户的所有订单
        List<Order> orders = orderService.getUserOrdersWithDetails(user.getUId());
        session.setAttribute("orders", orders);

        // 设置成功消息
        session.setAttribute("successMsg", successMsg);

        // 重定向到订单列表页面
        response.sendRedirect("OrderController");
      }
    } catch (NumberFormatException e) {
      request.setAttribute("errorMsg", "订单ID格式错误");
      request.getRequestDispatcher("/orderFailure.jsp").forward(request, response);
    }
  }
}