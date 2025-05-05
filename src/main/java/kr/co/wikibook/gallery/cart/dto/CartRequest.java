package kr.co.wikibook.gallery.cart.dto;

import kr.co.wikibook.gallery.cart.entity.Cart;
import lombok.Getter;

@Getter
public class CartRequest {

    private Integer itemId;

    public Cart toEntity(Integer memberId) {
        return new Cart(memberId, itemId);
    }
}
