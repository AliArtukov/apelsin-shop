package uz.apelsin.apelsinshop.controller;

/*
Created by Ali Artukov
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.apelsin.apelsinshop.model.ApiResponse;
import uz.apelsin.apelsinshop.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;


    /**
     * Get all products.
     * @return ResponseEntity<?>
     */
    @GetMapping("/list")
    public ResponseEntity<?> getAllProduct(){
        ApiResponse apiResponse = productService.getAllProduct();
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


    /**
     * Get product by product id.
     * @param product_id Integer product_id
     * @return ResponseEntity<?>
     */
    @GetMapping("/details")
    public ResponseEntity<?> getProductById(@RequestParam Integer product_id){
        ApiResponse apiResponse = productService.getProductById(product_id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
