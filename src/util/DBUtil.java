package util;

import java.sql.*;

/**
 * 数据库工具类，用于管理数据库连接
 */
public class DBUtil {
  // 数据库连接参数
  private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String URL = "jdbc:mysql://localhost:3306/089_JDBC?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
  private static final String USER = "root";
  private static final String PASSWORD = "root";

  // 静态块加载驱动
  static {
    try {
      Class.forName(DRIVER);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("数据库驱动加载失败", e);
    }
  }

  /**
   * 获取数据库连接
   * 
   * @return 数据库连接对象
   * @throws SQLException 如果连接失败则抛出异常
   */
  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

  /**
   * 关闭数据库连接资源
   * 
   * @param conn 数据库连接
   * @param stmt 语句对象
   * @param rs   结果集
   */
  public static void close(Connection conn, Statement stmt, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      if (stmt != null) {
        stmt.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      if (conn != null && !conn.isClosed()) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * 关闭数据库连接和语句对象
   * 
   * @param conn 数据库连接
   * @param stmt 语句对象
   */
  public static void close(Connection conn, Statement stmt) {
    close(conn, stmt, null);
  }
}