package kr.co.wikibook.gallery.order.service;

import kr.co.wikibook.gallery.order.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findAll(Integer orderId);

    void saveAll(List<OrderItem> orderItems);
}
