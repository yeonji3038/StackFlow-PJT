package ssafy.StackFlow.Service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Repository.StoreRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
     private final StoreRepository storeRepository;
     public List<Store> findAllStores() {
         return storeRepository.findAll();
     }

     public String generateStoreCode(String location) {
         String prefix = "";

         switch (location) {
             case "서면":
                 prefix = "BSSM"; // 부산 서면
                 break;
             case "해운대":
                 prefix = "BSHD"; // 부산 해운대
                 break;
             case "강남":
                 prefix = "SEGN"; // 강남
                 break;
             case "수원":
                 prefix = "GYSW"; // 수원
                 break;
             case "대전":
                 prefix = "CHDJ"; // 대전
                 break;
             case "광주":
                 prefix = "JLGJ"; // 광주
                 break;
             default:
                 throw new IllegalArgumentException("유효하지 않은 매장 위치입니다.");
         }

         // 매장 코드 생성 (예: BS-SM01)
         String storeCode = prefix + "01"; // 여기서 "01"은 매장 번호로 변경 가능
         return storeCode;
     }

     public void saveStore(Store store) {
         // 매장 저장 로직 (예: JPA Repository 사용)
     }
}