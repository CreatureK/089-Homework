package dao;

import model.Order;
import model.Food;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单数据访问类，负责从数据库表order、orderDetail中读写订单数据
 */
public class OrderDAO {

  /**
   * 根据用户ID查询该用户的所有订单
   * 
   * @param uId 用户ID
   * @return 该用户的所有订单列表
   */
  public List<Order> getOrdersByUserId(int uId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Order> orders = new ArrayList<>();

    try {
      System.out.println("查询用户订单: uId=" + uId);
      conn = DBUtil.getConnection();

      // 注意：order是MySQL关键字，需要用反引号
      String sql = "SELECT * FROM `order` WHERE uId = ? ORDER BY oTime DESC";
      System.out.println("执行SQL: " + sql + " [参数: " + uId + "]");

      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, uId);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        Order order = new Order();
        order.setOId(rs.getInt("oId"));
        order.setUId(rs.getInt("uId"));
        order.setOTime(rs.getTimestamp("oTime"));
        order.setTotalPrice(rs.getDouble("totalPrice"));
        order.setStatus(rs.getString("status"));

        orders.add(order);
      }
      System.out.println("查询到" + orders.size() + "条订单记录");
    } catch (SQLException e) {
      System.out.println("查询订单失败: " + e.getMessage());
      e.printStackTrace();

      // 尝试使用不带反引号的查询作为后备
      if (orders.isEmpty() && e.getMessage() != null && e.getMessage().contains("order")) {
        try {
          if (conn != null && !conn.isClosed()) {
            String fallbackSql = "SELECT * FROM order_table WHERE uId = ? ORDER BY oTime DESC";
            System.out.println("尝试后备SQL: " + fallbackSql);
            pstmt = conn.prepareStatement(fallbackSql);
            pstmt.setInt(1, uId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
              Order order = new Order();
              order.setOId(rs.getInt("oId"));
              order.setUId(rs.getInt("uId"));
              order.setOTime(rs.getTimestamp("oTime"));
              order.setTotalPrice(rs.getDouble("totalPrice"));
              order.setStatus(rs.getString("status"));
              orders.add(order);
            }
            System.out.println("后备查询到" + orders.size() + "条订单记录");
          }
        } catch (Exception fallbackEx) {
          System.out.println("后备查询也失败: " + fallbackEx.getMessage());
        }
      }
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return orders;
  }

  /**
   * 根据订单ID查询订单详情
   * 
   * @param oId 订单ID
   * @return 订单对象，如果不存在则返回null
   */
  public Order getOrderById(int oId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Order order = null;

    try {
      conn = DBUtil.getConnection();
      String sql = "SELECT * FROM `order` WHERE oId = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, oId);

      rs = pstmt.executeQuery();

      if (rs.next()) {
        order = new Order();
        order.setOId(rs.getInt("oId"));
        order.setUId(rs.getInt("uId"));
        order.setOTime(rs.getTimestamp("oTime"));
        order.setTotalPrice(rs.getDouble("totalPrice"));
        order.setStatus(rs.getString("status"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return order;
  }

  /**
   * 获取用户订单数量
   * 
   * @param uId 用户ID
   * @return 订单数量
   */
  public int getOrderCountByUserId(int uId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int count = 0;

    try {
      conn = DBUtil.getConnection();
      String sql = "SELECT COUNT(*) FROM `order` WHERE uId = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, uId);

      rs = pstmt.executeQuery();

      if (rs.next()) {
        count = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return count;
  }

  /**
   * 根据订单ID获取订单详情，包含订单中的所有菜品信息
   * 
   * @param oId 订单ID
   * @return 包含菜品信息的订单对象
   */
  public Order getOrderWithDetails(int oId) {
    Order order = getOrderById(oId);
    if (order == null) {
      return null;
    }

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<OrderDetail> orderDetails = new ArrayList<>();

    try {
      conn = DBUtil.getConnection();
      String sql = "SELECT od.*, f.fName, f.fPrice " +
          "FROM orderDetail od " +
          "JOIN food f ON od.fId = f.fId " +
          "WHERE od.oId = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, oId);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        OrderDetail detail = new OrderDetail();
        detail.setOId(rs.getInt("oId"));

        Food food = new Food();
        food.setFId(rs.getInt("fId"));
        food.setFName(rs.getString("fName"));
        food.setFPrice(rs.getDouble("fPrice"));

        detail.setFood(food);
        detail.setQuantity(rs.getInt("quantity"));
        detail.setSum(rs.getDouble("sum"));

        orderDetails.add(detail);
      }

      order.setOrderDetails(orderDetails);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return order;
  }

  /**
   * 获取所有订单及其详情
   * 
   * @return 包含详情的订单列表
   */
  public List<Order> getAllOrdersWithDetails() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Map<Integer, Order> orderMap = new HashMap<>();

    try {
      conn = DBUtil.getConnection();

      // 首先获取所有订单
      String orderSql = "SELECT * FROM `order` ORDER BY oTime DESC";
      pstmt = conn.prepareStatement(orderSql);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Order order = new Order();
        int orderId = rs.getInt("oId");
        order.setOId(orderId);
        order.setUId(rs.getInt("uId"));
        order.setOTime(rs.getTimestamp("oTime"));
        order.setTotalPrice(rs.getDouble("totalPrice"));
        order.setStatus(rs.getString("status"));
        order.setOrderDetails(new ArrayList<>());

        orderMap.put(orderId, order);
      }
      rs.close();
      pstmt.close();

      // 然后获取所有订单详情
      if (!orderMap.isEmpty()) {
        String detailSql = "SELECT od.*, f.fName, f.fPrice " +
            "FROM orderDetail od " +
            "JOIN food f ON od.fId = f.fId " +
            "WHERE od.oId IN (" +
            String.join(",", orderMap.keySet().stream().map(String::valueOf).toArray(String[]::new)) +
            ")";
        pstmt = conn.prepareStatement(detailSql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
          int orderId = rs.getInt("oId");
          Order order = orderMap.get(orderId);

          if (order != null) {
            OrderDetail detail = new OrderDetail();
            detail.setOId(orderId);

            Food food = new Food();
            food.setFId(rs.getInt("fId"));
            food.setFName(rs.getString("fName"));
            food.setFPrice(rs.getDouble("fPrice"));

            detail.setFood(food);
            detail.setQuantity(rs.getInt("quantity"));
            detail.setSum(rs.getDouble("sum"));

            order.getOrderDetails().add(detail);
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return new ArrayList<>(orderMap.values());
  }

  /**
   * 订单详情类，包含订单中的菜品信息
   */
  public static class OrderDetail {
    private int oId;
    private Food food;
    private int quantity;
    private double sum;

    public int getOId() {
      return oId;
    }

    public void setOId(int oId) {
      this.oId = oId;
    }

    public Food getFood() {
      return food;
    }

    public void setFood(Food food) {
      this.food = food;
    }

    public int getQuantity() {
      return quantity;
    }

    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }

    public double getSum() {
      return sum;
    }

    public void setSum(double sum) {
      this.sum = sum;
    }
  }

  /**
   * 删除订单（针对已完成状态的订单）
   * 
   * @param orderId 订单ID
   * @return 是否成功删除
   */
  public boolean deleteOrder(int orderId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean success = false;

    try {
      conn = DBUtil.getConnection();

      // 首先检查订单状态是否为"已完成"
      String checkSql = "SELECT status FROM `order` WHERE oId = ?";
      pstmt = conn.prepareStatement(checkSql);
      pstmt.setInt(1, orderId);
      rs = pstmt.executeQuery();

      if (rs.next() && "已完成".equals(rs.getString("status"))) {
        rs.close();
        pstmt.close();

        // 开启事务
        conn.setAutoCommit(false);

        // 先删除订单详情
        String deleteDetailSql = "DELETE FROM orderDetail WHERE oId = ?";
        pstmt = conn.prepareStatement(deleteDetailSql);
        pstmt.setInt(1, orderId);
        pstmt.executeUpdate();

        // 再删除订单主表记录
        String deleteOrderSql = "DELETE FROM `order` WHERE oId = ?";
        pstmt = conn.prepareStatement(deleteOrderSql);
        pstmt.setInt(1, orderId);
        int result = pstmt.executeUpdate();

        // 提交事务
        conn.commit();
        success = result > 0;
      }
    } catch (SQLException e) {
      try {
        if (conn != null) {
          conn.rollback(); // 发生异常时回滚事务
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.setAutoCommit(true); // 恢复自动提交
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      DBUtil.close(conn, pstmt, rs);
    }

    return success;
  }

  /**
   * 取消订单（针对处理中状态的订单）
   * 
   * @param orderId 订单ID
   * @return 是否成功取消
   */
  public boolean cancelOrder(int orderId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean success = false;

    try {
      conn = DBUtil.getConnection();

      // 首先检查订单状态是否为"处理中"
      String checkSql = "SELECT status FROM `order` WHERE oId = ?";
      pstmt = conn.prepareStatement(checkSql);
      pstmt.setInt(1, orderId);
      rs = pstmt.executeQuery();

      if (rs.next() && "处理中".equals(rs.getString("status"))) {
        rs.close();
        pstmt.close();

        // 更新订单状态为"已取消"
        String updateSql = "UPDATE `order` SET status = '已取消' WHERE oId = ?";
        pstmt = conn.prepareStatement(updateSql);
        pstmt.setInt(1, orderId);
        int result = pstmt.executeUpdate();
        success = result > 0;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return success;
  }

  /**
   * 批量删除订单（针对已完成状态的订单）
   * 
   * @param orderIds 订单ID数组
   * @return 成功删除的订单ID列表
   */
  public List<Integer> batchDeleteOrders(int[] orderIds) {
    List<Integer> successfulIds = new ArrayList<>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = DBUtil.getConnection();

      for (int orderId : orderIds) {
        // 检查订单状态是否为"已完成"
        String checkSql = "SELECT status FROM `order` WHERE oId = ?";
        pstmt = conn.prepareStatement(checkSql);
        pstmt.setInt(1, orderId);
        rs = pstmt.executeQuery();

        if (rs.next() && "已完成".equals(rs.getString("status"))) {
          rs.close();
          pstmt.close();

          // 开启事务
          conn.setAutoCommit(false);

          try {
            // 先删除订单详情
            String deleteDetailSql = "DELETE FROM orderDetail WHERE oId = ?";
            pstmt = conn.prepareStatement(deleteDetailSql);
            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();

            // 再删除订单主表记录
            String deleteOrderSql = "DELETE FROM `order` WHERE oId = ?";
            pstmt = conn.prepareStatement(deleteOrderSql);
            pstmt.setInt(1, orderId);
            int result = pstmt.executeUpdate();

            // 提交事务
            conn.commit();

            if (result > 0) {
              successfulIds.add(orderId);
            }
          } catch (SQLException e) {
            // 发生异常，回滚事务
            try {
              conn.rollback();
            } catch (SQLException ex) {
              ex.printStackTrace();
            }
            e.printStackTrace();
          } finally {
            try {
              conn.setAutoCommit(true); // 恢复自动提交
            } catch (SQLException ex) {
              ex.printStackTrace();
            }
          }
        } else {
          if (rs != null)
            rs.close();
          if (pstmt != null)
            pstmt.close();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return successfulIds;
  }

  /**
   * 批量取消订单（针对处理中状态的订单）
   * 
   * @param orderIds 订单ID数组
   * @return 成功取消的订单ID列表
   */
  public List<Integer> batchCancelOrders(int[] orderIds) {
    List<Integer> successfulIds = new ArrayList<>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = DBUtil.getConnection();

      for (int orderId : orderIds) {
        // 检查订单状态是否为"处理中"
        String checkSql = "SELECT status FROM `order` WHERE oId = ?";
        pstmt = conn.prepareStatement(checkSql);
        pstmt.setInt(1, orderId);
        rs = pstmt.executeQuery();

        if (rs.next() && "处理中".equals(rs.getString("status"))) {
          rs.close();
          pstmt.close();

          // 更新订单状态为"已取消"
          String updateSql = "UPDATE `order` SET status = '已取消' WHERE oId = ?";
          pstmt = conn.prepareStatement(updateSql);
          pstmt.setInt(1, orderId);
          int result = pstmt.executeUpdate();

          if (result > 0) {
            successfulIds.add(orderId);
          }
        } else {
          if (rs != null)
            rs.close();
          if (pstmt != null)
            pstmt.close();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return successfulIds;
  }
}