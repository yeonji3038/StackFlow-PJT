package ssafy.StackFlow.Domain.Retrieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.Retrieval.entity.Retrieval;

public interface RetrievalRepository extends JpaRepository<Retrieval, Long> {
}
