package uz.apelsin.apelsinshop.model;

/*
Created by Ali Artukov
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.apelsin.apelsinshop.entity.Detail;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailsDto {

    private Integer orderId;
    private Integer customerId;
    private LocalDateTime date;
    private List<Detail> details;

}
