package model;

import java.util.Date;
import java.util.List;
import dao.OrderDAO.OrderDetail;

/**
 * 订单实体类，用于封装订单信息
 */
public class Order {
  private int oId; // 订单ID
  private int uId; // 用户ID
  private Date oTime; // 订单时间
  private double totalPrice; // 订单总价
  private String status; // 订单状态：处理中、已完成、已取消
  private List<OrderDetail> orderDetails; // 订单详情列表

  // 无参构造函数
  public Order() {
  }

  // 带参构造函数
  public Order(int oId, int uId, Date oTime, double totalPrice, String status) {
    this.oId = oId;
    this.uId = uId;
    this.oTime = oTime;
    this.totalPrice = totalPrice;
    this.status = status;
  }

  // Getter和Setter方法
  public int getOId() {
    return oId;
  }

  public void setOId(int oId) {
    this.oId = oId;
  }

  public int getUId() {
    return uId;
  }

  public void setUId(int uId) {
    this.uId = uId;
  }

  public Date getOTime() {
    return oTime;
  }

  public void setOTime(Date oTime) {
    this.oTime = oTime;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<OrderDetail> getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(List<OrderDetail> orderDetails) {
    this.orderDetails = orderDetails;
  }

  @Override
  public String toString() {
    return "Order{" +
        "oId=" + oId +
        ", uId=" + uId +
        ", oTime=" + oTime +
        ", totalPrice=" + totalPrice +
        ", status='" + status + '\'' +
        ", orderDetails=" + orderDetails +
        '}';
  }
}