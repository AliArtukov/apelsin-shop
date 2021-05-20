package uz.apelsin.apelsinshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.apelsin.apelsinshop.entity.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value =
            "select distinct on(c.id) * \n" +
            "from customer as c\n" +
            "inner join orders as o on c.id=o.cust_id\n" +
            "where c.id in(\n" +
            "\tselect c.id \n" +
            "\tfrom customer as c\n" +
            "\twhere c.id not in(\n" +
            "\t\tselect o.cust_id \n" +
            "\t\tfrom orders as o \n" +
            "\t\twhere o.date between '2016-01-01 00:00:00' and '2016-12-31 23:59:59.999'\n" +
            "\t)\n" +
            ")",
            nativeQuery = true)
    List<Customer> getCustomersWithoutOrders();

    @Query(value =
            "select distinct on(res.id) res.id, res.name, res.date \n" +
            "from(\n" +
            "\tselect c.id, c.name, o.date\n" +
            "\tfrom customer c\n" +
            "\tinner join orders o\n" +
            "\ton c.id=o.cust_id\n" +
            "\torder by o.date desc\n" +
            ") res",
    nativeQuery = true)
    List<String[]> getCustomersAndCustomersLastOrders();
}
