package uz.apelsin.apelsinshop.model;

/*
Created by Ali Artukov
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse {

    private boolean success;
    private String message;
    private Object obj;

}
