package ssafy.StackFlow.Repository.product;


import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.product.ProductStore;

import java.util.Optional;

public interface ProductStoreRepository extends JpaRepository<ProductStore, Long> {
    Optional<ProductStore> findByStoreAndProduct(Store store, Product product);
}
