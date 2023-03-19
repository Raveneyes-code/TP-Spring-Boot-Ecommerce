package com.course.client.beans;


import java.util.List;

public class CartBean {

    private Long id;
    private List<CartItemBean> products;

    public CartBean(Long id, List<CartItemBean> products) {
        this.id = id;
        this.products = products;
    }
    public CartBean(List<CartItemBean> products) {
        this.products = products;
    }
    public CartBean(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItemBean> getProducts() {
        System.out.println("in product ill"+products.get(0).getIllustration());
        System.out.println("in product qte"+products.get(0).getQuantity());

        return products;
    }

    public void setProducts(List<CartItemBean> products) {
        this.products = products;
    }

}
