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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    private BigDecimal amount = BigDecimal.valueOf(8, 2);

    @ManyToOne(optional = false)
    private Invoice inv;

}
