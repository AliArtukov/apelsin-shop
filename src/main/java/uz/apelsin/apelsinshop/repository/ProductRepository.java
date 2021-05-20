package uz.apelsin.apelsinshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.apelsin.apelsinshop.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
