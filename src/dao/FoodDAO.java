package dao;

import model.Food;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜肴数据访问类，负责从数据库表food中读写菜肴数据
 */
public class FoodDAO {

  /**
   * 获取所有在售菜肴
   * 
   * @return 在售菜肴列表
   */
  public List<Food> getAllAvailableFoods() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Food> foods = new ArrayList<>();

    try {
      conn = DBUtil.getConnection();
      String sql = "SELECT * FROM food WHERE fType = '在售' ORDER BY fId";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Food food = new Food();
        food.setFId(rs.getInt("fId"));
        food.setFName(rs.getString("fName"));
        food.setFPrice(rs.getDouble("fPrice"));
        food.setFType(rs.getString("fType"));
        foods.add(food);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return foods;
  }

  /**
   * 根据菜肴ID获取菜肴信息
   * 
   * @param fId 菜肴ID
   * @return 菜肴对象，如果不存在则返回null
   */
  public Food getFoodById(int fId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Food food = null;

    try {
      conn = DBUtil.getConnection();
      String sql = "SELECT * FROM food WHERE fId = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, fId);
      rs = pstmt.executeQuery();

      if (rs.next()) {
        food = new Food();
        food.setFId(rs.getInt("fId"));
        food.setFName(rs.getString("fName"));
        food.setFPrice(rs.getDouble("fPrice"));
        food.setFType(rs.getString("fType"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return food;
  }

  /**
   * 根据订单ID获取订单中的所有菜肴及数量
   * 
   * @param orderId 订单ID
   * @return 菜肴列表，包含数量和价格
   */
  public List<OrderFoodItem> getFoodsByOrderId(int orderId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<OrderFoodItem> orderFoods = new ArrayList<>();

    try {
      conn = DBUtil.getConnection();
      String sql = "SELECT f.fId, f.fName, f.fPrice, od.quantity, od.sum " +
          "FROM orderDetail od " +
          "JOIN food f ON od.fId = f.fId " +
          "WHERE od.oId = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, orderId);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        OrderFoodItem item = new OrderFoodItem();
        item.setFood(new Food(
            rs.getInt("fId"),
            rs.getString("fName"),
            rs.getDouble("fPrice"),
            null));
        item.setQuantity(rs.getInt("quantity"));
        item.setSum(rs.getDouble("sum"));
        orderFoods.add(item);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return orderFoods;
  }

  /**
   * 订单中的菜肴项，包含菜肴信息和数量
   */
  public static class OrderFoodItem {
    private Food food;
    private int quantity;
    private double sum;

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
}