package service;

import model.School;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学院和系数据服务类
 * 提供静态的学院和系数据，不依赖数据库
 */
public class SchoolDataService {
  private static final List<School> schools = new ArrayList<>();
  private static boolean initialized = false;

  /**
   * 初始化学院和系的数据
   */
  private static void initializeData() {
    if (initialized) {
      return;
    }

    // 创建5个学院，每个学院下3个系
    School computerSchool = new School("计算机学院");
    computerSchool.addDepartment("计算机科学与技术");
    computerSchool.addDepartment("软件工程");
    computerSchool.addDepartment("网络工程");
    schools.add(computerSchool);

    School mathSchool = new School("数学学院");
    mathSchool.addDepartment("应用数学");
    mathSchool.addDepartment("统计学");
    mathSchool.addDepartment("金融数学");
    schools.add(mathSchool);

    School foreignLanguageSchool = new School("外国语学院");
    foreignLanguageSchool.addDepartment("英语");
    foreignLanguageSchool.addDepartment("日语");
    foreignLanguageSchool.addDepartment("法语");
    schools.add(foreignLanguageSchool);

    School economicsSchool = new School("经济学院");
    economicsSchool.addDepartment("经济学");
    economicsSchool.addDepartment("国际经济与贸易");
    economicsSchool.addDepartment("金融学");
    schools.add(economicsSchool);

    School artSchool = new School("艺术学院");
    artSchool.addDepartment("音乐表演");
    artSchool.addDepartment("美术学");
    artSchool.addDepartment("设计学");
    schools.add(artSchool);

    initialized = true;
  }

  /**
   * 获取所有学院列表
   * 
   * @return 学院名称列表
   */
  public static List<String> getAllSchoolNames() {
    initializeData();
    List<String> schoolNames = new ArrayList<>();
    for (School school : schools) {
      schoolNames.add(school.getName());
    }
    return schoolNames;
  }

  /**
   * 根据学院名称获取所有系列表
   * 
   * @param schoolName 学院名称
   * @return 系名称列表
   */
  public static List<String> getDepartmentNamesBySchool(String schoolName) {
    initializeData();
    for (School school : schools) {
      if (school.getName().equals(schoolName)) {
        return school.getDepartmentNames();
      }
    }
    return new ArrayList<>();
  }

  /**
   * 获取所有学院和对应的系
   * 
   * @return 学院和系的映射关系
   */
  public static Map<String, List<String>> getAllSchoolsAndDepartments() {
    initializeData();
    Map<String, List<String>> schoolDeptMap = new HashMap<>();
    for (School school : schools) {
      schoolDeptMap.put(school.getName(), school.getDepartmentNames());
    }
    return schoolDeptMap;
  }

  /**
   * 获取学院对象
   * 
   * @param schoolName 学院名称
   * @return 学院对象
   */
  public static School getSchoolByName(String schoolName) {
    initializeData();
    for (School school : schools) {
      if (school.getName().equals(schoolName)) {
        return school;
      }
    }
    return null;
  }

  /**
   * 获取所有学院
   * 
   * @return 学院列表
   */
  public static List<School> getAllSchools() {
    initializeData();
    return new ArrayList<>(schools);
  }
}