package kr.co.wikibook.gallery.item.repository;

import kr.co.wikibook.gallery.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByIdIn(List<Integer> ids);
}
