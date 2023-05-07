package com.demo.vo;

import java.io.Serializable;
import java.util.Date;
//javaBean
/**
 * food（t_food表对应的Java实体类）
 */
public class Food implements Serializable {
    private Long id;//主键
    private String foodName;//食品名称
    private String foodLan;//食品产地
    private String foodTing;//食品所属用户
    private String foodDate;//食品生产日期开始时间
    private String foodLong;//食品有效期
    private String foodPrice;//食品金额
    private String foodText;//备注
    private Date expireDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodLan() {
        return foodLan;
    }

    public void setFoodLan(String foodLan) {
        this.foodLan = foodLan;
    }

    public String getFoodTing() {
        return foodTing;
    }

    public void setFoodTing(String foodTing) {
        this.foodTing = foodTing;
    }

    public String getFoodDate() {
        return foodDate;
    }

    public void setFoodDate(String foodDate) {
        this.foodDate = foodDate;
    }

    public String getFoodLong() {
        return foodLong;
    }

    public void setFoodLong(String foodLong) {
        this.foodLong = foodLong;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodText() {
        return foodText;
    }

    public void setFoodText(String foodText) {
        this.foodText = foodText;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
