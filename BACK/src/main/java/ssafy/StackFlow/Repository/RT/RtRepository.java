package ssafy.StackFlow.Repository.RT;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.RT.RT;

import java.util.List;

@Repository
public interface RtRepository extends JpaRepository<RT, Long>{

        List<Product> findByProdCodeContaining(String keyword);
    }

