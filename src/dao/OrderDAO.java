package dao;

import model.Order;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单数据访问类，负责从数据库表order中读写订单数据
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
}