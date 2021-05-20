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
import uz.apelsin.apelsinshop.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    /**
     * Get all categories.
     * @return ResponseEntity<?>
     */
    @GetMapping("/list")
    public ResponseEntity<?> getAllCategory(){
        ApiResponse apiResponse = categoryService.getAllCategory();
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


    /**
     * Get category by product id.
     * @param product_id Integer product_id
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> getCategory(@RequestParam Integer product_id){
        ApiResponse apiResponse = categoryService.getCategoryByProductId(product_id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
