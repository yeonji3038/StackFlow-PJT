package ssafy.StackFlow.Repository.Retrieval;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.Retrieval.Retrieval;

public interface RetrievalRepository extends JpaRepository<Retrieval, Long> {
}
