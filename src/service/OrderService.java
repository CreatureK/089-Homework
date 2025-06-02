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

  /**
   * 删除订单（针对已完成状态的订单）
   * 
   * @param orderId 订单ID
   * @return 删除结果：true成功，false失败
   */
  public boolean deleteOrder(int orderId) {
    return orderDAO.deleteOrder(orderId);
  }

  /**
   * 批量删除订单（针对已完成状态的订单）
   * 
   * @param orderIds 订单ID数组
   * @return 成功删除的订单ID列表
   */
  public List<Integer> batchDeleteOrders(int[] orderIds) {
    return orderDAO.batchDeleteOrders(orderIds);
  }

  /**
   * 取消订单（针对处理中状态的订单）
   * 
   * @param orderId 订单ID
   * @return 取消结果：true成功，false失败
   */
  public boolean cancelOrder(int orderId) {
    return orderDAO.cancelOrder(orderId);
  }

  /**
   * 批量取消订单（针对处理中状态的订单）
   * 
   * @param orderIds 订单ID数组
   * @return 成功取消的订单ID列表
   */
  public List<Integer> batchCancelOrders(int[] orderIds) {
    return orderDAO.batchCancelOrders(orderIds);
  }
}