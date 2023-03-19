package com.course.client.proxies;

import com.course.client.beans.CartBean;
import com.course.client.beans.OrderBean;
import com.course.client.beans.OrderItemBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "ms-order", url = "localhost:8093")
public interface MsOrderProxy {
    @GetMapping(value = "/orderItems")
    public List<OrderItemBean> list();

    @PostMapping(value = "/order")
    public ResponseEntity<CartBean> createNewOrder(@RequestBody OrderBean orderData);

    @GetMapping(value = "/order/search")
    public List<OrderBean> searchOrder();

    @GetMapping(value = "/order/{id}")
    public Optional<CartBean> getOrder(@PathVariable Long id);
}
