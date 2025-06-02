package service;

import dao.OrderDAO;
import model.Order;

import java.util.List;

/**
 * 订单业务逻辑处理类，实现订单相关业务功能
 */
public class OrderService {
  private OrderDAO orderDAO;

  public OrderService() {
    orderDAO = new OrderDAO();
  }

  /**
   * 获取用户的所有订单
   * 
   * @param userId 用户ID
   * @return 订单列表
   */
  public List<Order> getUserOrders(int userId) {
    return orderDAO.getOrdersByUserId(userId);
  }

  /**
   * 获取订单详情
   * 
   * @param orderId 订单ID
   * @return 订单对象
   */
  public Order getOrderDetails(int orderId) {
    return orderDAO.getOrderById(orderId);
  }

  /**
   * 获取用户订单总数
   * 
   * @param userId 用户ID
   * @return 订单总数
   */
  public int getUserOrderCount(int userId) {
    return orderDAO.getOrderCountByUserId(userId);
  }

  /**
   * 获取用户的所有订单，包含订单中的菜肴详情
   * 
   * @param userId 用户ID
   * @return 包含菜肴详情的订单列表
   */
  public List<Order> getUserOrdersWithDetails(int userId) {
    // 获取用户的所有订单
    List<Order> orders = orderDAO.getOrdersByUserId(userId);

    // 为每个订单获取详情
    for (Order order : orders) {
      Order detailedOrder = orderDAO.getOrderWithDetails(order.getOId());
      if (detailedOrder != null && detailedOrder.getOrderDetails() != null) {
        order.setOrderDetails(detailedOrder.getOrderDetails());
      }
    }

    return orders;
  }
}