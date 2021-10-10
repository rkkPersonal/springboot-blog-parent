package org.xr.happy.model;

import java.util.Date;
import javax.persistence.*;

public class Shopping {
    @Id
    private Long id;

    @Column(name = "shopping_name")
    private String shoppingName;

    @Column(name = "shopping_detail")
    private String shoppingDetail;

    @Column(name = "shopping_count")
    private Integer shoppingCount;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "updateBy")
    private String updateby;

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
     * @return shopping_name
     */
    public String getShoppingName() {
        return shoppingName;
    }

    /**
     * @param shoppingName
     */
    public void setShoppingName(String shoppingName) {
        this.shoppingName = shoppingName;
    }

    /**
     * @return shopping_detail
     */
    public String getShoppingDetail() {
        return shoppingDetail;
    }

    /**
     * @param shoppingDetail
     */
    public void setShoppingDetail(String shoppingDetail) {
        this.shoppingDetail = shoppingDetail;
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
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return updateBy
     */
    public String getUpdateby() {
        return updateby;
    }

    /**
     * @param updateby
     */
    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }
}