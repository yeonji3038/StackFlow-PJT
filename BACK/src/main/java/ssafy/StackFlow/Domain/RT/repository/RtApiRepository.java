package ssafy.StackFlow.Domain.RT.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.StackFlow.Domain.RT.entity.RT;

import java.util.List;

@Repository
@RequiredArgsConstructor

public class RtApiRepository {
    private final EntityManager em;

    public List<RT> findAllWithItem() {

        return em.createQuery(
                        "select distinct r from RT r" +
                                " join fetch r.rtProducts rp" +
                                " join fetch rp.product p", RT.class)
                .getResultList();
    }
}
