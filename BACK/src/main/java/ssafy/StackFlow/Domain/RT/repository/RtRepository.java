package ssafy.StackFlow.Domain.RT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssafy.StackFlow.Domain.RT.entity.RT;
import ssafy.StackFlow.Domain.RT.entity.RtStatus;

import java.util.List;

@Repository
public interface RtRepository extends JpaRepository<RT, Long> {

    // 내 매장이 요청한 RT 목록 조회
    List<RT> findByMyStore(String myStore);

    // 내 매장으로 요청된 RT 목록 조회
    List<RT> findByReqStore(String reqStore);

    // 상태별 RT 목록 조회
    List<RT> findByStatus(RtStatus status);

    // RT와 연관된 상품 정보를 함께 조회하는 JPQL 쿼리
    @Query("select distinct r from RT r join fetch r.rtProducts rp join fetch rp.product p")
    List<RT> findAllWithProducts();

    // 특정 매장이 요청한 RT와 연관 상품 정보를 함께 조회
    @Query("select distinct r from RT r join fetch r.rtProducts rp join fetch rp.product p where r.myStore = :storeName")
    List<RT> findByMyStoreWithProducts(@Param("storeName") String storeName);

    // 특정 매장으로 요청된 RT와 연관 상품 정보를 함께 조회
    @Query("select distinct r from RT r join fetch r.rtProducts rp join fetch rp.product p where r.reqStore = :storeName")
    List<RT> findByReqStoreWithProducts(@Param("storeName") String storeName);
}