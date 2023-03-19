package com.course.client.beans;

public class CartItemBean {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String illustration;
    public CartItemBean(Long id, Long productId, Integer quantity, String illustration) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.illustration=illustration;
    }
    public CartItemBean(Long productId,  Integer quantity ,String illustration) {
        this.productId = productId;
        this.quantity = quantity;
        this.illustration=illustration;
    }
    public CartItemBean(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getIllustration() {
        return illustration;
    }
}
