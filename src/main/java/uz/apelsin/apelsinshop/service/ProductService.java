package uz.apelsin.apelsinshop.service;

/*
Created by Ali Artukov
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.apelsin.apelsinshop.entity.Product;
import uz.apelsin.apelsinshop.model.ApiResponse;
import uz.apelsin.apelsinshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;


    /**
     * Get all products.
     *
     * @return ApiResponse
     */
    public ApiResponse getAllProduct() {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty())
            return new ApiResponse(false, "Product list is empty", null);
        return new ApiResponse(true, "Product list", productList);
    }


    /**
     * Get product by product id.
     *
     * @param product_id Integer product_id
     * @return ApiResponse
     */
    public ApiResponse getProductById(Integer product_id) {
        Optional<Product> optionalProduct = productRepository.findById(product_id);
        if (optionalProduct.isPresent())
            return new ApiResponse(true, "Product details.", optionalProduct.get());
        return new ApiResponse(false, "Product with the specified ID was not found.", null);
    }

}
