package kr.co.wikibook.gallery.order.service;

import jakarta.transaction.Transactional;
import kr.co.wikibook.gallery.cart.service.CartService;
import kr.co.wikibook.gallery.common.util.EncryptionUtils;
import kr.co.wikibook.gallery.item.dto.ItemRead;
import kr.co.wikibook.gallery.item.service.ItemService;
import kr.co.wikibook.gallery.order.dto.OrderRead;
import kr.co.wikibook.gallery.order.dto.OrderRequest;
import kr.co.wikibook.gallery.order.entity.Order;
import kr.co.wikibook.gallery.order.entity.OrderItem;
import kr.co.wikibook.gallery.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ItemService itemService;
    private final CartService cartService;

    @Override
    public List<OrderRead> findAll(Integer memberId) {
        return orderRepository.findAllByMemberIdOrderByIdDesc(memberId).stream().map(Order::toRead).toList();
    }

    @Override
    public OrderRead find(Integer orderId, Integer memberId) {
        Optional<Order> orderOptional = orderRepository.findByIdAndMemberId(orderId, memberId);
        if (orderOptional.isPresent()) {
            OrderRead order = orderOptional.get().toRead();
            List<OrderItem> orderItems = orderItemService.findAll(order.getId());
            List<Integer> orderItemIds = orderItems.stream().map(OrderItem::getItemId).toList();
            List<ItemRead> items = itemService.findAll(orderItemIds);
            order.setItems(items);
            return order;
        }
        return null;
    }

    @Override
    @Transactional
    public void order(OrderRequest orderReq, Integer memberId) {
        // 주문 상품의 최종 결제 금액을 계산
        List<ItemRead> items = itemService.findAll(orderReq.getItemIds());
        long amount = 0L;
        for (ItemRead item : items) {
            amount += item.getPrice() - item.getPrice().longValue() * item.getDiscountPer() / 100;
        }

        // 주문 요청에 최종 결제 금액 입력
        orderReq.setAmount(amount);

        // 결제 수단이 카드일 때 카드 번호 암호화
        if (orderReq.getPayment().equals("card")) {
            String cardNumber = orderReq.getCardNumber();
            String removeDashCardNumber = cardNumber.replaceAll("-", "");
            String encryptedCardNumber = EncryptionUtils.encrypt(removeDashCardNumber);
            orderReq.setCardNumber(encryptedCardNumber);
        }

        // 주문 저장
        Order order = orderRepository.save(orderReq.toEntity(memberId));

        // 주문 상품 데이터 생성
        List<OrderItem> newOrderItems = new ArrayList<>();

        // 상품 아이디만큼 주문 상품 추가
        orderReq.getItemIds().forEach(itemId -> {
            OrderItem newOrderItem = new OrderItem(order.getId(), itemId);
            newOrderItems.add(newOrderItem);
        });

        // 주문 상품 데이터 저장
        orderItemService.saveAll(newOrderItems);

        // 장바구니 데이터 삭제(특정 회원)
        cartService.removeAll(order.getMemberId());
    }
}
