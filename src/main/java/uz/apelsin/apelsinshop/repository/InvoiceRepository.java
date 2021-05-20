package uz.apelsin.apelsinshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.apelsin.apelsinshop.entity.Invoice;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query(value =
            "select * \n" +
            "from invoice as i\n" +
            "where i.issued>i.due",
    nativeQuery = true)
    List<Invoice> getExpiredInvoices();

    @Query(value =
            "select i.id as invoice_id, i.issued as invoice_issued, o.id as order_id, o.date as order_date \n" +
            "from invoice as i \n" +
            "inner join orders as o on o.id=i.ord_id \n" +
            "where i.issued < o.date",
            nativeQuery = true)
    List<String[]> getWrongDateInvoices();

    @Query(value =
            "select main_res.invoice_id, main_res.repaid_summa\n" +
            "from(\n" +
            "\tselect inv.id as \"invoice_id\", (\n" +
            "    select sum(res.amount)-(select amount from invoice i where i.id=inv.id)as \"repaid_summa\"\n" +
            "    from (select p.amount from payment p where p.inv_id=inv.id)as res\n" +
            "    )\n" +
            "\tfrom invoice inv\n" +
            ")as main_res\n" +
            "where repaid_summa>0",
    nativeQuery = true)
    List<String[]> getOverpaidInvoices();

}
