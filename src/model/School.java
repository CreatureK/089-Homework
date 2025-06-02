package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 学院实体类，用于封装学院信息
 */
public class School {
  private String name; // 学院名称
  private List<Department> departments; // 学院下属系列表

  // 无参构造函数
  public School() {
    this.departments = new ArrayList<>();
  }

  // 带参构造函数
  public School(String name) {
    this.name = name;
    this.departments = new ArrayList<>();
  }

  // Getter和Setter方法
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Department> getDepartments() {
    return departments;
  }

  public void setDepartments(List<Department> departments) {
    this.departments = departments;
  }

  // 添加系的方法
  public void addDepartment(Department department) {
    if (department != null && !departments.contains(department)) {
      departments.add(department);
    }
  }

  // 根据名称添加系的方法
  public void addDepartment(String departmentName) {
    Department department = new Department(departmentName, this);
    departments.add(department);
  }

  // 获取系名称列表的方法
  public List<String> getDepartmentNames() {
    List<String> names = new ArrayList<>();
    for (Department dept : departments) {
      names.add(dept.getName());
    }
    return names;
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
    School school = (School) obj;
    return name.equals(school.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}