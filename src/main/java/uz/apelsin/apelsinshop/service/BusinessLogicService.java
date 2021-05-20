package uz.apelsin.apelsinshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.apelsin.apelsinshop.entity.Customer;
import uz.apelsin.apelsinshop.entity.Invoice;
import uz.apelsin.apelsinshop.entity.Order;
import uz.apelsin.apelsinshop.model.*;
import uz.apelsin.apelsinshop.repository.CustomerRepository;
import uz.apelsin.apelsinshop.repository.DetailRepository;
import uz.apelsin.apelsinshop.repository.InvoiceRepository;
import uz.apelsin.apelsinshop.repository.OrderRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
Created by Ali Artukov
*/
@Service
public class BusinessLogicService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DetailRepository detailRepository;


    /**
     * Invoices issued after their due date. Return all attributes.
     * @return ApiResponse
     */
    public ApiResponse getExpiredInvoices() {
        List<Invoice> invoiceList = invoiceRepository.getExpiredInvoices();
        if (invoiceList.isEmpty())
            return new ApiResponse(false, "Not found expired invoices.", null);
        return new ApiResponse(true, "Expired invoice list.", invoiceList);
    }


    /**
     * Invoices that were issued before the date in which the order they refer to was placed.
     * Return the ID of the invoice, the date it was issued, the ID of the order associated with it
     * and the date the order was placed.
     * @return ApiResponse
     */
    public ApiResponse getWrongDateInvoices() {
        List<String[]> wrongDateInvoices = invoiceRepository.getWrongDateInvoices();
        if (wrongDateInvoices.isEmpty())
            return new ApiResponse(false, "Wrong date invoices not found.", null);
        List<WrongDateInvoicesDto> wrongDateInvoicesDtoList = new ArrayList<>();
        wrongDateInvoices.forEach(strings -> wrongDateInvoicesDtoList.add(new WrongDateInvoicesDto(Integer.parseInt(strings[0]), Timestamp.valueOf(strings[1]).toLocalDateTime(), Integer.parseInt(strings[2]), Timestamp.valueOf(strings[3]).toLocalDateTime())));
        return new ApiResponse(true, "Wrong date invoices.", wrongDateInvoicesDtoList);
    }


    /**
     * Orders that do not have a detail and were placed before 6 September 2016. Return all attributes.
     * @return ApiResponse
     */
    public ApiResponse getOrdersWithoutDetails() {
        LocalDateTime date = LocalDateTime.parse("2016-09-06T00:00:00");
        List<Order> orderList = orderRepository.getOrdersWithoutDetails(date);
        if (orderList.isEmpty())
            return new ApiResponse(false, "Orders without details not found.", null);
        return new ApiResponse(true, "Orders without details.", orderList);
    }


    /**
     * Customers who have not placed any orders in 2016. Return all attributes.
     * @return ApiResponse
     */
    public ApiResponse getCustomersWithoutOrders() {
        List<Customer> customerList = customerRepository.getCustomersWithoutOrders();
        if (customerList.isEmpty())
            return new ApiResponse(false, "Customers without orders in 2016year not found.", null);
        return new ApiResponse(true, "Customers without orders in 2016year.", customerList);
    }


    /**
     * ID and name of customers and the date of their last order. For customers who did not
     * place any orders, no rows must be returned. For each customer who placed more than
     * one order on the date of their most recent order, only one row must be returned.
     * @return ApiResponse
     */
    public ApiResponse getCustomersAndCustomersLastOrders() {
        List<String[]> customerList = customerRepository.getCustomersAndCustomersLastOrders();
        if (customerList.isEmpty())
            return new ApiResponse(false, "No customers found with the order.", null);
        List<CustomerLastOrderDto> customers = new ArrayList<>();
        customerList.forEach(strings -> customers.add(new CustomerLastOrderDto(Integer.parseInt(strings[0]), strings[1], Timestamp.valueOf(strings[2]).toLocalDateTime())));
        return new ApiResponse(true, "Customers with order and last order date.", customers);
    }


    /**
     * Invoices that have been overpaid. Observe that there may be more than one payment referring
     * to the same invoice. Return the invoice number and the amount that should be reimbursed.
     * @return ApiResponse
     */
    public ApiResponse getOverpaidInvoices(){
        List<String[]> overpaidInvoices = invoiceRepository.getOverpaidInvoices();
        if (overpaidInvoices.isEmpty())
            return new ApiResponse(false, "Overpaid invoices not found.", null);
        List<OverpaidInvoiceDto> overpaidInvoiceDtoList = new ArrayList<>();
        overpaidInvoices.forEach(strings -> overpaidInvoiceDtoList.add(new OverpaidInvoiceDto(Integer.parseInt(strings[0]), Double.parseDouble(strings[1]))));
        return new ApiResponse(true, "Overpaid invoices.", overpaidInvoiceDtoList);
    }


    /**
     * Products that were ordered more than 10 times in total, by taking into account the
     * quantities in which they appear in the order details. Return the product code and the
     * total number of times it was ordered.
     * @return ApiResponse
     */
    public ApiResponse getHighDemandProducts(){
        List<String[]> highDemandProducts = detailRepository.getHighDemandProducts();
        if (highDemandProducts.isEmpty())
            return new ApiResponse(false, "Products ordered more than 10 times were not found.", null);
        List<HighDemandProductsDto> highDemandProductsDtoList = new ArrayList<>();
        highDemandProducts.forEach(strings -> highDemandProductsDtoList.add(new HighDemandProductsDto(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]))));
        return new ApiResponse(true, "High demand products.", highDemandProductsDtoList);
    }


    /**
     * Products that are usually ordered in bulk: whenever one of these products is ordered,
     * it is ordered in a quantity that on average is equal to or greater than 8.
     * For each such product, return product code and price.
     * @return ApiResponse
     */
    public ApiResponse getBulkProducts(){
        List<String[]> bulkProducts = detailRepository.getBulkProducts();
        if (bulkProducts.isEmpty())
            return new ApiResponse(false, "Products normally ordered in bulk have not been found.", null);
        List<BulkProductsDto> bulkProductsDtoList = new ArrayList<>();
        bulkProducts.forEach(strings -> bulkProductsDtoList.add(new BulkProductsDto(Integer.parseInt(strings[0]), Double.parseDouble(strings[1]))));
        return new ApiResponse(true, "Bulk products.", bulkProductsDtoList);
    }


    /**
     * Total number of orders placed in 2016 by customers of each country. If all customers from a
     * specific country did not place any orders in 2016, the country will not appear in the output.
     * @return ApiResponse
     */
    public ApiResponse getNumberOfProductsInYear(){
        List<String[]> numberOfProductsInYear = orderRepository.getNumberOfProductsInYear();
        if (numberOfProductsInYear.isEmpty())
            return new ApiResponse(false, "In 2016, the product was not sold.", null);
        List<NumberOfProductsInYearDto> numberOfProductsInYearDtoList = new ArrayList<>();
        numberOfProductsInYear.forEach(strings -> numberOfProductsInYearDtoList.add(new NumberOfProductsInYearDto(strings[0], Integer.parseInt(strings[1]))));
        return new ApiResponse(true, "Country and number of products sold in 2016.", numberOfProductsInYearDtoList);
    }


    /**
     * For each order without invoice, list its ID, the date it was placed and the total price of the
     * products in its detail, taking into account the quantity of each ordered product and its unit
     * price. Orders without detail must not be included in the answers.
     * @return ApiResponse
     */
    public ApiResponse getOrdersWithoutInvoices(){
        List<String[]> ordersWithoutInvoices = orderRepository.getOrdersWithoutInvoices();
        if (ordersWithoutInvoices.isEmpty())
            return new ApiResponse(false, "Orders without invoice not found.", null);
        List<OrdersWithoutInvoicesDto> ordersWithoutInvoicesDtoList = new ArrayList<>();
        ordersWithoutInvoices.forEach(strings -> ordersWithoutInvoicesDtoList.add(new OrdersWithoutInvoicesDto(Integer.parseInt(strings[0]), Timestamp.valueOf(strings[1]).toLocalDateTime(), Double.parseDouble(strings[2]))));
        return new ApiResponse(true, "Orders without invoice.", ordersWithoutInvoicesDtoList);

    }

}
