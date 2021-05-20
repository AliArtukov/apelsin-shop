package uz.apelsin.apelsinshop.service;

/*
Created by Ali Artukov
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.apelsin.apelsinshop.entity.Invoice;
import uz.apelsin.apelsinshop.entity.Payment;
import uz.apelsin.apelsinshop.model.ApiResponse;
import uz.apelsin.apelsinshop.model.PaymentDto;
import uz.apelsin.apelsinshop.repository.InvoiceRepository;
import uz.apelsin.apelsinshop.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    InvoiceRepository invoiceRepository;


    /**
     * Get payment details by payment id.
     * @param id Integer id
     * @return ApiResponse
     */
    public ApiResponse getPaymentDetailsById(Integer id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (!optionalPayment.isPresent())
            return new ApiResponse(false, "Payment with the specified ID was not found.", null);
        return new ApiResponse(true, "Payment details.", optionalPayment.get());
    }


    /**
     * Make Payment by Invoice id.
     * @param paymentDto Integer invoice_id
     * @return ApiResponse
     */
    public ApiResponse makePayment(PaymentDto paymentDto) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(paymentDto.getInvoiceId());
        if (!optionalInvoice.isPresent())
            return new ApiResponse(false, "Invoice with the specified ID was not found.", null);
        Invoice invoice = optionalInvoice.get();
        Payment payment = new Payment();
        payment.setTime(LocalDateTime.now());
        payment.setAmount(invoice.getAmount());
        payment.setInv(invoice);
        payment = paymentRepository.save(payment);
        return new ApiResponse(true, "Payment details.", payment);
    }

}
