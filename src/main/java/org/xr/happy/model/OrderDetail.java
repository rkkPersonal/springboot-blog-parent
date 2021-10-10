package org.xr.happy.model;

import javax.persistence.*;

@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "order_name")
    private String orderName;

    private String remark;

    @Column(name = "shopping_id")
    private Long shoppingId;

    @Column(name = "shopping_count")
    private Integer shoppingCount;

    private Integer status;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return order_name
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     * @param orderName
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return shopping_id
     */
    public Long getShoppingId() {
        return shoppingId;
    }

    /**
     * @param shoppingId
     */
    public void setShoppingId(Long shoppingId) {
        this.shoppingId = shoppingId;
    }

    /**
     * @return shopping_count
     */
    public Integer getShoppingCount() {
        return shoppingCount;
    }

    /**
     * @param shoppingCount
     */
    public void setShoppingCount(Integer shoppingCount) {
        this.shoppingCount = shoppingCount;
    }


    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}