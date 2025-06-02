package model;

/**
 * 系实体类，用于封装系信息
 */
public class Department {
  private String name; // 系名称
  private School school; // 所属学院

  // 无参构造函数
  public Department() {
  }

  // 带参构造函数
  public Department(String name) {
    this.name = name;
  }

  // 完整参数构造函数
  public Department(String name, School school) {
    this.name = name;
    this.school = school;
  }

  // Getter和Setter方法
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Department that = (Department) obj;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}