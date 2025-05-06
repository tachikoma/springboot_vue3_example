package kr.co.wikibook.gallery.order.service;

import kr.co.wikibook.gallery.order.dto.OrderRead;
import kr.co.wikibook.gallery.order.dto.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<OrderRead> findAll(Integer memberId, Pageable pageable); // <1>
    OrderRead find(Integer orderId, Integer memberId); // <2>
    void order(OrderRequest orderReq, Integer memberId); // <3>
}
