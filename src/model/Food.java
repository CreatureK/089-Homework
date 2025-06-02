package model;

/**
 * 菜肴实体类，用于封装菜肴信息
 */
public class Food {
  private int fId; // 菜肴ID
  private String fName; // 菜肴名称
  private double fPrice; // 菜肴价格
  private String fType; // 菜肴状态：在售、停售、缺货

  // 无参构造函数
  public Food() {
  }

  // 带参构造函数
  public Food(int fId, String fName, double fPrice, String fType) {
    this.fId = fId;
    this.fName = fName;
    this.fPrice = fPrice;
    this.fType = fType;
  }

  // Getter和Setter方法
  public int getFId() {
    return fId;
  }

  public void setFId(int fId) {
    this.fId = fId;
  }

  public String getFName() {
    return fName;
  }

  public void setFName(String fName) {
    this.fName = fName;
  }

  public double getFPrice() {
    return fPrice;
  }

  public void setFPrice(double fPrice) {
    this.fPrice = fPrice;
  }

  public String getFType() {
    return fType;
  }

  public void setFType(String fType) {
    this.fType = fType;
  }

  @Override
  public String toString() {
    return "Food{" +
        "fId=" + fId +
        ", fName='" + fName + '\'' +
        ", fPrice=" + fPrice +
        ", fType='" + fType + '\'' +
        '}';
  }
}