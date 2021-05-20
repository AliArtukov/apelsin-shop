package uz.apelsin.apelsinshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.apelsin.apelsinshop.entity.Detail;

import java.util.List;
import java.util.Optional;

public interface DetailRepository extends JpaRepository<Detail, Integer> {
    List<Detail> findAllByOrd_Id(Integer order_id);

    @Query(value =
            "select res.product_id, res.quantity \n" +
            "from(\n" +
            "\tselect p.id as product_id, (\n" +
            "\t\tselect sum(quantity) \n" +
            "\t\tfrom detail as d \n" +
            "\t\twhere d.pr_id=p.id\n" +
            "\t)as quantity \n" +
            "\tfrom product as p) as res \n" +
            "where res.quantity>10",
    nativeQuery = true)
    List<String[]> getHighDemandProducts();

    @Query(value =
            "select res.product_id, p.price\n" +
            "from product as p\n" +
            "inner join(\n" +
            "\tselect p.id as product_id, (\n" +
            "\t\tselect avg(d.quantity) \n" +
            "\t\tfrom detail as d \n" +
            "\t\twhere d.pr_id=p.id\n" +
            "\t) as avarage_quantity \n" +
            "\tfrom product as p\n" +
            ") as res on p.id=res.product_id\n" +
            "where res.avarage_quantity>=8",
    nativeQuery = true)
    List<String[]> getBulkProducts();
}
