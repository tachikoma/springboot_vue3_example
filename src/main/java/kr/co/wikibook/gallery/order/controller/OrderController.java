package kr.co.wikibook.gallery.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.wikibook.gallery.account.helper.AccountHelper;
import kr.co.wikibook.gallery.order.dto.OrderRead;
import kr.co.wikibook.gallery.order.dto.OrderRequest;
import kr.co.wikibook.gallery.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class OrderController {

    private final AccountHelper accountHelper;
    private final OrderService orderService;

    @GetMapping("/api/orders")
    public ResponseEntity<?> readAll(HttpServletRequest req) {
        // Get the member ID from the request
        Integer memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        // Fetch all orders for the member
        List<OrderRead> orders = orderService.findAll(memberId);

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/api/orders/{orderId}")
    public ResponseEntity<?> read(HttpServletRequest req, @PathVariable Integer orderId) {
        // Get the member ID from the request
        Integer memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        // Fetch the order details for the member
        OrderRead order = orderService.find(orderId, memberId);
        if (order == null) { // Order not found
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }

    @PostMapping("/api/orders")
    public ResponseEntity<?> add(HttpServletRequest req, @RequestBody OrderRequest orderReq) {
        // Get the member ID from the request
        Integer memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }

        // Process the order
        orderService.order(orderReq, memberId);

        return ResponseEntity.ok().build();
    }
}
