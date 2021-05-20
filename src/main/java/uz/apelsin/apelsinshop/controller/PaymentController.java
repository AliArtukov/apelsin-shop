package uz.apelsin.apelsinshop.controller;

/*
Created by Ali Artukov
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.apelsin.apelsinshop.model.ApiResponse;
import uz.apelsin.apelsinshop.model.PaymentDto;
import uz.apelsin.apelsinshop.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;


    /**
     * Get payment details by payment id.
     * @param id Integer id
     * @return ResponseEntity<?>
     */
    @GetMapping("/details")
    public ResponseEntity<?> getPaymentDetailsById(@RequestParam Integer id){
        ApiResponse apiResponse = paymentService.getPaymentDetailsById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


    /**
     * Make Payment by Invoice id.
     * @param paymentDto PaymentDto paymentDto
     * @return ResponseEntity<?>
     */
    @PostMapping
    public ResponseEntity<?> makePayment(@RequestBody PaymentDto paymentDto){
        ApiResponse apiResponse = paymentService.makePayment(paymentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);

    }

}
