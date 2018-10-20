package sos.nagato.test;

import sos.haruhi.ioc.annotations.Component;

import java.util.Date;

/**
 * @ClassName OrderDetail
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/17 20:51
 * @Version 10032
 **/
@Component("order_detail_my_test")
public class OrderDetail {
    private int id;
    private String productName;
    private double price;
    private int count;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
