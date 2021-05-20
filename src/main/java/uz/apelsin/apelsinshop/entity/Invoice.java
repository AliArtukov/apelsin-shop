package uz.apelsin.apelsinshop.entity;

/*
Created by Ali Artukov
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(optional = false)
    private Order ord;

    @Column(nullable = false)
    private BigDecimal amount = BigDecimal.valueOf(8, 2);

    @Column(nullable = false)
    private LocalDateTime issued;

    @Column(nullable = false)
    private LocalDateTime due;

}
