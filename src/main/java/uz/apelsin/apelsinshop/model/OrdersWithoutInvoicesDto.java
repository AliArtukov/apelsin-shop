package uz.apelsin.apelsinshop.model;

/*
Created by Ali Artukov
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdersWithoutInvoicesDto {

    private Integer orderId;
    private LocalDateTime date;
    private Double totalPrice;

}
