package sos.nagato.test;

import sos.haruhi.ioc.annotations.Component;
import sos.haruhi.ioc.annotations.Inject;

/**
 * @ClassName Order
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/17 20:46
 * @Version 10032
 **/
@Component("order_my_test")
public class Order {
    private int id;
    private String merchantNo;
    private String userId;
    private double totalPayment;

    @Inject
    private OrderDetail orderDetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
}
