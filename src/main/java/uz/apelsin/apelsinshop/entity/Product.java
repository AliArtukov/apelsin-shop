package uz.apelsin.apelsinshop.entity;

/*
Created by Ali Artukov
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)//, length = 10)
    private String name;

    @ManyToOne(optional = false)
    private Category category;

    @Column(length = 1024)
    private String description;

    @Column(nullable = false)
    private BigDecimal price = BigDecimal.valueOf(6,2);

    @Column(length = 1024)
    private String photo;

}
