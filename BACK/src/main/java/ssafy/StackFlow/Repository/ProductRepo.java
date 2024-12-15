package ssafy.StackFlow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssafy.StackFlow.Domain.Product;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByProdCodeContaining(String keyword);

    @Query("SELECT p FROM Product p " +
            "JOIN p.prodCate c " +
            "JOIN c.cateGroup cg " +
            "JOIN p.colorCode co " +
            "JOIN p.size s " +
            "WHERE (:categoryGroup IS NULL OR cg.groupName LIKE %:categoryGroup%) " +
            "AND (:categoryCode IS NULL OR c.cateCode LIKE %:categoryCode%) " +
            "AND (:colorCode IS NULL OR co.colorCode LIKE %:colorCode%) " +
            "AND (:size IS NULL OR s.size LIKE %:size%)")
    List<Product> findProductsByFilters(@Param("categoryGroup") String categoryGroup,
                                        @Param("categoryCode") String categoryCode,
                                        @Param("colorCode") String colorCode,
                                        @Param("size") String size);


}