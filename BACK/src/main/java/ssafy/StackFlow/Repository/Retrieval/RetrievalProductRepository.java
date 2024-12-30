package ssafy.StackFlow.Repository.Retrieval;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.Retrieval.RetrievalProduct;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.product.Product;

import java.util.List;

public interface RetrievalProductRepository extends JpaRepository<RetrievalProduct, Long> {

}
