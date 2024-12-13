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

    @Query("SELECT p FROM Product p WHERE " +
           "(:cateGroup IS NULL OR p.prodCate.cateGroup = :cateGroup) AND " +
           "(:cateCode IS NULL OR p.prodCate.cateCode = :cateCode) AND " +
           "(:colorCode IS NULL OR p.colorCode.colorCode = :colorCode) AND " +
           "(:size IS NULL OR p.size = :size)")
    List<Product> findByFilters(
            @Param("cateGroup") String cateGroup,
            @Param("cateCode") String cateCode,
            @Param("colorCode") String colorCode,
            @Param("size") String size
    );
}