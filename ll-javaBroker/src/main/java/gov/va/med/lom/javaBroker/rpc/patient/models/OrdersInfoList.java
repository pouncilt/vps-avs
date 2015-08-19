package gov.va.med.lom.javaBroker.rpc.patient.models;

import java.io.Serializable;

import gov.va.med.lom.javaBroker.rpc.BaseBean;

public class OrdersInfoList extends BaseBean implements Serializable {

  private int count;
  private int textView;
  private double fmDatetime;
  private OrderInfo[] ordersInfo;
  
  public OrdersInfoList() {
    this.count = 0;
    this.textView = 0;
    this.fmDatetime = 0;
    this.ordersInfo = null;
  }
  
  public int getCount() {
    return count;
  }
  
  public void setCount(int count) {
    this.count = count;
  }
  
  public double getFmDatetime() {
    return fmDatetime;
  }
  
  public void setFmDatetime(double fmDatetime) {
    this.fmDatetime = fmDatetime;
  }
  
  public int getTextView() {
    return textView;
  }
  
  public void setTextView(int textView) {
    this.textView = textView;
  }
  
  public OrderInfo[] getOrdersInfo() {
    return ordersInfo;
  }
  
  public void setOrdersInfo(OrderInfo[] ordersInfo) {
    this.ordersInfo = ordersInfo;
  }
}
