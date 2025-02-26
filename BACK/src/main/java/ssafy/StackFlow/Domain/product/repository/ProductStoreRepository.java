package ssafy.StackFlow.Domain.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.product.entity.Product;
import ssafy.StackFlow.Domain.product.entity.ProductStore;

import java.util.Optional;

public interface ProductStoreRepository extends JpaRepository<ProductStore, Long> {
    Optional<ProductStore> findByStoreAndProduct(Store store, Product product);
    Optional<ProductStore> findByStoreIdAndProductId(Long storeId, Long productId);

}
