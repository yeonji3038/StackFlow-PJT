package ssafy.StackFlow.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.StackFlow.Domain.Product;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepo {
    private final EntityManager em;

    public void save(Product product) {
        if (product.getId() == null) {
            em.persist(product);
        } else {
            em.merge(product);
        }
    }

    public Product findOne(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createQuery("select i from Product i", Product.class)
                .getResultList();
    }

}
