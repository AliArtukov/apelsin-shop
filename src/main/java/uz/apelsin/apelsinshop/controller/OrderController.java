package uz.apelsin.apelsinshop.controller;

/*
Created by Ali Artukov
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.apelsin.apelsinshop.model.ApiResponse;
import uz.apelsin.apelsinshop.model.OrderDto;
import uz.apelsin.apelsinshop.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;


    /**
     * Get order by order id.
     * @param order_id Integer order_id
     * @return ResponseEntity<?>
     */
    @GetMapping("/details")
    public ResponseEntity<?> getOrderDetailsById(@RequestParam Integer order_id){
        ApiResponse apiResponse = orderService.getOrderDetailsById(order_id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


    /**
     * Make Order, Details and Invoice.
     * @param orderDto OrderDto orderDto
     * @return ResponseEntity<?>
     */
    @PostMapping
    public ResponseEntity<ApiResponse> makeOrder(@RequestBody OrderDto orderDto){
        ApiResponse apiResponse = orderService.makeOrder(orderDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
