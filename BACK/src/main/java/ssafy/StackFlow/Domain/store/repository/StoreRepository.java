package ssafy.StackFlow.Domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.store.entity.Store;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {


    Optional<Object> findByStoreCode(String storeCode); //매장 위치

    long countByAdminAreaAndSubArea(String adminArea, String subArea);
}
