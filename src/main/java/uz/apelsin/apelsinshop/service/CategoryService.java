package uz.apelsin.apelsinshop.service;

/*
Created by Ali Artukov
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.apelsin.apelsinshop.entity.Category;
import uz.apelsin.apelsinshop.model.ApiResponse;
import uz.apelsin.apelsinshop.repository.CategoryRepository;
import uz.apelsin.apelsinshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;


    /**
     * Get all categories.
     *
     * @return ApiResponse
     */
    public ApiResponse getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        if (categoryList.isEmpty())
            return new ApiResponse(false, "Category list is empty.", null);
        return new ApiResponse(true, "Category list.", categoryList);
    }


    /**
     * Get category by product id.
     *
     * @param product_id Integer product_id
     * @return ApiResponse
     */
    public ApiResponse getCategoryByProductId(Integer product_id) {
        Optional<Category> optionalCategory = categoryRepository.findByProductId(product_id);
        if (optionalCategory.isPresent())
            return new ApiResponse(true, "Category details.", optionalCategory.get());
        return new ApiResponse(false, "Product with the specified ID was not found.", null);
    }

}
