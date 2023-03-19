package com.course.client.controllers;

import com.course.client.beans.*;
import com.course.client.proxies.MsCartProxy;
import com.course.client.proxies.MsOrderProxy;
import com.course.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {
    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsCartProxy msCartProxy;

    @Autowired private MsOrderProxy msOrderProxy;

    Long currentCartId;

    @RequestMapping("/")
    public String index(Model model) {
        List<ProductBean> products = msProductProxy.list();
        model.addAttribute("products", products);
        return "index";
    }

    @PostMapping(value = "/{panierId}")
    public Long currentCartId(@PathVariable String panierId)
    {

        if(panierId.equals("undefined") || panierId.equals("null")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");
        }
        else {
            currentCartId = Long.parseLong(panierId);
            return currentCartId;
        }
    }
    @RequestMapping("/product-detail/{id}")
    public String description(Model model, @PathVariable Long id) {
        ProductBean product = msProductProxy.get(id).get();
        model.addAttribute("product",product);
        return "product-detail";
    }

    @RequestMapping(value = { "/product-detail/{id}/{panierId}" }, method = RequestMethod.POST)
    public ResponseEntity<CartBean> addToCart(Model model, @PathVariable Long id, @PathVariable String panierId,
                                              @RequestParam(value = "illustration", required = false) String illustration) {
        if(panierId.equals("undefined") || panierId.equals("null")) {
            CartItemBean cartItemBean = new CartItemBean(id, 1, illustration); // add illustration parameter to CartItemBean
            List<CartItemBean> cartItemBeansList = new ArrayList<>();
            cartItemBeansList.add(cartItemBean);
            CartBean cartData = new CartBean(cartItemBeansList);
            System.out.println(cartItemBeansList.get(0).getIllustration());
            return msCartProxy.createNewCart(cartData);
        }
        else{
            Long panierIdLong = Long.parseLong(panierId);
            CartItemBean cartItemBean = new CartItemBean(id, 1, illustration); // add illustration parameter to CartItemBean
            System.out.println(cartItemBean.getIllustration());
            return msCartProxy.addProductToCart(panierIdLong, cartItemBean);
        }
    }


    @RequestMapping("/cart/{panierId}")
    public String panier(Model model,  @PathVariable String panierId) {
        Long panierIdLong = Long.parseLong(panierId);
        CartBean cart = msCartProxy.getCart(panierIdLong).get();
        model.addAttribute("cart",cart);
        List<CartItemBean> items = cart.getProducts();
        model.addAttribute("cartItems", items);
        return "cart";
    }

    // function of order

    @RequestMapping("/orders")
    public String orderPage(Model model) {
        List<OrderBean> orderBeans = msOrderProxy.searchOrder();

        model.addAttribute("myorders",orderBeans);
        return "orders";
    }

    @RequestMapping("/order/{panierId}")
    public String order(Model model,  @PathVariable String panierId) {
        Long panierIdLong = Long.parseLong(panierId);
        CartBean cart = new CartBean();
        try{
            cart = msCartProxy.getCart(panierIdLong).get();
        }catch(Exception e){
            model.addAttribute("myorder",new OrderBean());
            return "order";
        }

        double totalPrice = 0.0;
        List<CartItemBean> items = cart.getProducts();
        List<Double> prices = new ArrayList<>();
        List<OrderItemBean> orderItems = new ArrayList<>();
        for(CartItemBean item : items){
            Long itemID = item.getProductId();
            Optional<ProductBean>  product = msProductProxy.get(itemID);
            double productPrice = product.get().getPrice();
            prices.add(productPrice);

            OrderItemBean orderItemBean = new OrderItemBean(itemID,item.getQuantity(),productPrice);
            orderItems.add(orderItemBean);
            totalPrice += productPrice * item.getQuantity();
        }


        OrderBean orderData = new OrderBean(panierIdLong,totalPrice,orderItems);
        msOrderProxy.createNewOrder(orderData);

        model.addAttribute("myorder",orderData);

        msCartProxy.deleteCart(panierIdLong);


        return "order";
    }

}
