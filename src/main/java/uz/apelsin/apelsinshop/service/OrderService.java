package uz.apelsin.apelsinshop.service;

/*
Created by Ali Artukov
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.apelsin.apelsinshop.ProjectProperties;
import uz.apelsin.apelsinshop.entity.*;
import uz.apelsin.apelsinshop.model.ApiResponse;
import uz.apelsin.apelsinshop.model.InvoiceDto;
import uz.apelsin.apelsinshop.model.OrderDetailsDto;
import uz.apelsin.apelsinshop.model.OrderDto;
import uz.apelsin.apelsinshop.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DetailRepository detailRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InvoiceRepository invoiceRepository;


    /**
     * Get order by order id.
     *
     * @param order_id Integer order_id
     * @return ApiResponse
     */
    public ApiResponse getOrderDetailsById(Integer order_id) {
        Optional<Order> optionalOrder = orderRepository.findById(order_id);
        if (!optionalOrder.isPresent())
            return new ApiResponse(false, "Order with the specified ID was not found.", null);
        Order order = optionalOrder.get();
        List<Detail> detailList = detailRepository.findAllByOrd_Id(order_id);
        OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
        orderDetailsDto.setOrderId(order.getId());
        orderDetailsDto.setCustomerId(order.getCust().getId());
        orderDetailsDto.setDate(order.getDate());
        orderDetailsDto.setDetails(detailList);

        return new ApiResponse(true, "Order details.", orderDetailsDto);
    }


    /**
     * Make Order, Details and Invoice.
     *
     * @param orderDto OrderDto orderDto
     * @return ApiResponse
     */
    public ApiResponse makeOrder(OrderDto orderDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(orderDto.getCustomerId());
        if (!optionalCustomer.isPresent()) {
            return new ApiResponse(false, "Customer with the specified ID was not found.", null);
        }
        Optional<Product> optionalProduct = productRepository.findById(orderDto.getProductId());
        if (!optionalProduct.isPresent()) {
            return new ApiResponse(false, "Product with the specified ID was not found.", null);
        }
        Product product = optionalProduct.get();

        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setCust(optionalCustomer.get());
        order = orderRepository.save(order);

        Detail detail = new Detail();
        detail.setOrd(order);
        detail.setPr(product);
        detail.setQuantity(orderDto.getQuantity());
        detailRepository.save(detail);

        Invoice invoice = new Invoice();
        invoice.setOrd(order);
        invoice.setAmount(product.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())));
        invoice.setIssued(LocalDateTime.now());
        invoice.setDue(LocalDateTime.now().plusHours(ProjectProperties.TIME_OF_INVOICE_IN_HOURS));
        invoice = invoiceRepository.save(invoice);
        InvoiceDto invoiceDto = new InvoiceDto(invoice.getId());
        return new ApiResponse(true, "Order, Details and Invoice created.", invoiceDto);
    }

}
