package ssafy.StackFlow.Domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.store.entity.Store;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findByStoreCode(String storeCode);

    List<Store> findByLocationOrderByStoreCodeDesc(String location);

}
