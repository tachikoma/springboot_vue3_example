package kr.co.wikibook.gallery.cart.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.wikibook.gallery.account.helper.AccountHelper;
import kr.co.wikibook.gallery.cart.dto.CartRead;
import kr.co.wikibook.gallery.cart.dto.CartRequest;
import kr.co.wikibook.gallery.cart.service.CartService;
import kr.co.wikibook.gallery.item.dto.ItemRead;
import kr.co.wikibook.gallery.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;
    private final AccountHelper accountHelper;

    @GetMapping("/api/cart/items")
    public ResponseEntity<?> readAll(HttpServletRequest req) {
        Integer memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.notFound().build();
        }
        List<CartRead> carts = cartService.findAll(memberId);
        List<Integer> itemIds = carts.stream().map(CartRead::getItemId).toList();
        List<ItemRead> items = itemService.findAll(itemIds);

        return ResponseEntity.ok(items);
    }

    @PostMapping("/api/carts")
    public ResponseEntity<?> push(HttpServletRequest req, @RequestBody CartRequest cartRequest) {
        Integer memberId = accountHelper.getMemberId(req);

        CartRead cartRead = cartService.find(memberId, cartRequest.getItemId());
        if (cartRead == null) {
            cartService.save(cartRequest.toEntity(memberId));
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity<?> remove(HttpServletRequest req, @PathVariable Integer itemId) {
        Integer memberId = accountHelper.getMemberId(req);
        cartService.remove(memberId, itemId);
        return ResponseEntity.ok().build();
    }
}
