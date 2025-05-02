package ssafy.StackFlow.Domain.RT.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ssafy.StackFlow.Domain.RT.entity.RT;
import ssafy.StackFlow.Domain.RT.entity.RtStatus;

import java.util.List;

@Repository
public interface RtRepository extends JpaRepository<RT, Long> {
    List<RT> findByMyStore(String myStore);
    List<RT> findByReqStore(String reqStore);
    Page<RT> findByStatus(RtStatus status, Pageable pageable);
    Page<RT> findByMyStore(String myStore, Pageable pageable);

    @Query("select distinct r from RT r join fetch r.rtProducts rp join fetch rp.product p")
    List<RT> findAllWithProducts();

    @Query("select distinct r from RT r join fetch r.rtProducts rp join fetch rp.product p where r.myStore = :storeName")
    List<RT> findByMyStoreWithProducts(String storeName);

    @Query("select distinct r from RT r join fetch r.rtProducts rp join fetch rp.product p where r.reqStore = :storeName")
    List<RT> findByReqStoreWithProducts(String storeName);
}