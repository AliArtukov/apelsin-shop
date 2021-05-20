package uz.apelsin.apelsinshop.entity;
/*
Created by Ali Artukov
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)//, length = 14)
    private String name;

    @Column(nullable = false)//, length = 3)
    private String country;

    @Column(columnDefinition = "text")
    private String address;

    @Column(nullable = false, length = 50)
    private String phone;

}
