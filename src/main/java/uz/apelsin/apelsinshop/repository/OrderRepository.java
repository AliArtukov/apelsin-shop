package uz.apelsin.apelsinshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.apelsin.apelsinshop.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value =
            "select *\n" +
            "from orders o\n" +
            "inner join (\n" +
            "\tselect o.id \n" +
            "\tfrom orders o\n" +
            "\texcept\n" +
            "\tselect d.ord_id \n" +
            "\tfrom detail d\n" +
            ") res \n" +
            "on o.id=res.id\n" +
            "and o.date < ?1",
            nativeQuery = true)
    List<Order> getOrdersWithoutDetails(LocalDateTime date);

    @Query(value =
            "select res.country_name, res.total_orders\n" +
            "from(\n" +
            "\tselect distinct cus.country as country_name, (\n" +
            "\t\tselect sum(\n" +
            "\t\t\t(select count(*) \n" +
            "\t\t\t from orders as o \n" +
            "\t\t\t where o.cust_id=c.id and \n" +
            "\t\t\t date between '2016-01-01 00:00:00' and '2016-12-31 23:59:59.999')\n" +
            "\t\t) as all_orders \n" +
            "\t\tfrom customer as c \n" +
            "\t\twhere c.country=cus.country\n" +
            "\t) as total_orders \n" +
            "from customer as cus\n" +
            ") as res\n" +
            "where res.total_orders>0",
    nativeQuery = true)
    List<String[]> getNumberOfProductsInYear();

    @Query(value =
            "select distinct on(o.id) o.id as order_id, o.date as date, (\n" +
            "\tselect sum(res.total_price) \n" +
            "\tfrom(\n" +
            "\t\tselect (p.price * d.quantity) as total_price \n" +
            "\t\tfrom product as p\n" +
            "\t\tinner join detail as d on d.ord_id=o.id\n" +
            "\t\twhere p.id=d.pr_id) as res\n" +
            ") as total_price\n" +
            "from orders as o\n" +
            "inner join detail as d on d.ord_id=o.id \n" +
            "where o.id in(\n" +
            "\tselect id \n" +
            "\tfrom orders \n" +
            "\texcept \n" +
            "\tselect ord_id \n" +
            "\tfrom invoice\n" +
            ")",
    nativeQuery = true)
    List<String[]> getOrdersWithoutInvoices();

}
