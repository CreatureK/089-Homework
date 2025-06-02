package dao;

import model.Login;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户数据访问类，负责从数据库表user中读写用户数据
 */
public class UserDAO {

  /**
   * 根据用户ID和密码验证用户
   * 
   * @param uId 用户ID
   * @param uPw 用户密码
   * @return 如果验证成功返回用户对象，否则返回null
   */
  public Login validateUser(int uId, String uPw) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Login login = null;

    try {
      System.out.println("开始验证用户: ID=" + uId + ", 密码=" + uPw);
      conn = DBUtil.getConnection();
      if (conn == null) {
        System.out.println("ERROR: 数据库连接为null");
        return null;
      }

      // 先测试简单查询确认用户是否存在
      String testSql = "SELECT * FROM user WHERE uId = ?";
      PreparedStatement testStmt = conn.prepareStatement(testSql);
      testStmt.setInt(1, uId);
      ResultSet testRs = testStmt.executeQuery();

      if (testRs.next()) {
        System.out.println("找到用户ID=" + uId + ", 数据库中密码=" + testRs.getString("uPw") + ", 提交的密码=" + uPw);
      } else {
        System.out.println("未找到用户ID=" + uId + ", 请检查数据库连接和用户表");
      }

      testRs.close();
      testStmt.close();

      // 执行实际的登录验证
      String sql = "SELECT * FROM user WHERE uId = ? AND uPw = ?";
      System.out.println("执行SQL: " + sql + " [uId=" + uId + ", uPw=" + uPw + "]");
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, uId);
      pstmt.setString(2, uPw);

      rs = pstmt.executeQuery();

      if (rs.next()) {
        login = new Login();
        login.setUId(rs.getInt("uId"));
        login.setUName(rs.getString("uName"));
        login.setUPw(rs.getString("uPw"));
        login.setUSchool(rs.getString("uSchool"));
        login.setUDepartment(rs.getString("uDepartment"));
        System.out.println("用户验证成功: " + login);
      } else {
        System.out.println("用户验证失败: 未找到匹配记录, 请检查密码是否正确或数据库字符编码");
      }
    } catch (SQLException e) {
      System.out.println("用户验证SQL异常: " + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("用户验证其他异常: " + e.getMessage());
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return login;
  }

  /**
   * 获取所有学院列表
   * 
   * @return 学院列表
   */
  public List<String> getAllSchools() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<String> schools = new ArrayList<>();

    try {
      conn = DBUtil.getConnection();
      String sql = "SELECT DISTINCT uSchool FROM user WHERE uSchool IS NOT NULL ORDER BY uSchool";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        schools.add(rs.getString("uSchool"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return schools;
  }

  /**
   * 根据学院获取所有系列表
   * 
   * @param school 学院名称
   * @return 系列表
   */
  public List<String> getDepartmentsBySchool(String school) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<String> departments = new ArrayList<>();

    try {
      conn = DBUtil.getConnection();
      String sql = "SELECT DISTINCT uDepartment FROM user WHERE uSchool = ? AND uDepartment IS NOT NULL ORDER BY uDepartment";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, school);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        departments.add(rs.getString("uDepartment"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(conn, pstmt, rs);
    }

    return departments;
  }

  /**
   * 获取所有学院和对应的系
   * 
   * @return 学院和系的映射
   */
  public Map<String, List<String>> getAllSchoolsAndDepartments() {
    Map<String, List<String>> schoolDeptMap = new HashMap<>();
    List<String> schools = getAllSchools();

    for (String school : schools) {
      List<String> departments = getDepartmentsBySchool(school);
      schoolDeptMap.put(school, departments);
    }

    return schoolDeptMap;
  }
}