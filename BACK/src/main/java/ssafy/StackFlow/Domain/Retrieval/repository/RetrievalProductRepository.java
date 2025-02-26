package ssafy.StackFlow.Domain.Retrieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.Retrieval.entity.RetrievalProduct;

public interface RetrievalProductRepository extends JpaRepository<RetrievalProduct, Long> {

}
