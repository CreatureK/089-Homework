package model;

/**
 * 登录实体类，用于封装用户登录信息
 */
public class Login {
  private int uId; // 用户ID
  private String uPw; // 用户密码
  private String uSchool; // 用户所在学院
  private String uDepartment; // 用户所在系
  private String verifyCode; // 验证码

  // 无参构造函数
  public Login() {
  }

  // 带参构造函数
  public Login(int uId, String uPw) {
    this.uId = uId;
    this.uPw = uPw;
  }

  // 完整参数构造函数
  public Login(int uId, String uPw, String uSchool, String uDepartment, String verifyCode) {
    this.uId = uId;
    this.uPw = uPw;
    this.uSchool = uSchool;
    this.uDepartment = uDepartment;
    this.verifyCode = verifyCode;
  }

  // Getter和Setter方法
  public int getUId() {
    return uId;
  }

  public void setUId(int uId) {
    this.uId = uId;
  }

  public String getUPw() {
    return uPw;
  }

  public void setUPw(String uPw) {
    this.uPw = uPw;
  }

  public String getUSchool() {
    return uSchool;
  }

  public void setUSchool(String uSchool) {
    this.uSchool = uSchool;
  }

  public String getUDepartment() {
    return uDepartment;
  }

  public void setUDepartment(String uDepartment) {
    this.uDepartment = uDepartment;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  @Override
  public String toString() {
    return "Login{" +
        "uId=" + uId +
        ", uPw='" + uPw + '\'' +
        ", uSchool='" + uSchool + '\'' +
        ", uDepartment='" + uDepartment + '\'' +
        '}';
  }
}