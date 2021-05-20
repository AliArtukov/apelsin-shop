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
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne//(optional = false)
    private Order ord; // One Order Many OrderDetails XXX

    @ManyToOne(optional = false)
    private Product pr;

    @Column(nullable = false)
    private Short quantity;

}
