package service;

import dao.UserDAO;
import model.Login;

import java.util.List;
import java.util.Map;

/**
 * 登录业务逻辑处理类，实现登录验证等功能
 */
public class LoginService {
  private UserDAO userDAO;

  public LoginService() {
    userDAO = new UserDAO();
  }

  /**
   * 验证用户登录
   * 
   * @param login             登录信息对象
   * @param sessionVerifyCode session中的验证码
   * @return 包含登录结果和错误信息的数组，[0]为布尔值表示是否成功，[1]为错误信息
   */
  public Object[] validateLogin(Login login, String sessionVerifyCode) {
    boolean isValid = false;
    String errorMsg = "";

    // 验证码校验
    if (sessionVerifyCode == null || !sessionVerifyCode.equalsIgnoreCase(login.getVerifyCode())) {
      errorMsg = "验证码错误";
      return new Object[] { isValid, errorMsg };
    }

    // 用户名和密码校验
    Login user = userDAO.validateUser(login.getUId(), login.getUPw());
    if (user == null) {
      errorMsg = "用户名或密码错误";
    } else {
      isValid = true;
    }

    return new Object[] { isValid, errorMsg };
  }

  /**
   * 获取所有学院列表
   * 
   * @return 学院列表
   */
  public List<String> getAllSchools() {
    // 使用SchoolDataService代替数据库查询
    return SchoolDataService.getAllSchoolNames();
  }

  /**
   * 根据学院获取所有系列表
   * 
   * @param school 学院名称
   * @return 系列表
   */
  public List<String> getDepartmentsBySchool(String school) {
    // 使用SchoolDataService代替数据库查询
    return SchoolDataService.getDepartmentNamesBySchool(school);
  }

  /**
   * 获取所有学院和对应的系
   * 
   * @return 学院和系的映射
   */
  public Map<String, List<String>> getAllSchoolsAndDepartments() {
    // 使用SchoolDataService代替数据库查询
    return SchoolDataService.getAllSchoolsAndDepartments();
  }
}