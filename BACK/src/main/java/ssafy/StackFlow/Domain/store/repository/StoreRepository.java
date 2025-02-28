package ssafy.StackFlow.Domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.store.entity.Store;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByLocationOrderByStoreCodeDesc(String location);
    Optional<Store> findByStoreCode(String storeCode);

}
