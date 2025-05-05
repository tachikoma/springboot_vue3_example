package kr.co.wikibook.gallery.block.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "blocks")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 250, nullable = false)
    private String token;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    public Block() {
    }

    public Block(String token) {
        this.token = token;
    }
}
