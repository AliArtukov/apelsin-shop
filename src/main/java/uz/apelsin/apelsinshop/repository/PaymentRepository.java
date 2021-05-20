package uz.apelsin.apelsinshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.apelsin.apelsinshop.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
