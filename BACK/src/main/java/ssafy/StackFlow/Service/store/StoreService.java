package ssafy.StackFlow.Service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Repository.StoreRepository;

import java.util.List;

@Service
@Transactional(readOnly = true) // 이 서비스의 모든 메서드는 기본적으로 읽기 전용 트랜잭션을 사용
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 어노테이션
public class StoreService {
    private final StoreRepository storeRepository; // StoreRepository 주입

    @Transactional // 데이터를 수정하는 메서드이므로 @Transactional 추가
    public void saveStore(Store store) {
        // 매장 위치를 기반으로 매장 코드 생성
        String storeCode = generateStoreCode(store.getLocation());
        store.setStoreCode(storeCode); // 생성된 매장 코드를 설정
        
        // DB에 저장
        storeRepository.save(store); // 매장 정보를 데이터베이스에 저장
    }

    public String generateStoreCode(String location) {
        String prefix = ""; // 매장 코드의 접두사 초기화


        switch (location) {
            case "서면":
                prefix = "BSSM";
                break;
            case "해운대":
                prefix = "BSHD";
                break;
            case "강남":
                prefix = "SEGN";
                break;
            case "수원":
                prefix = "GYSW";
                break;
            case "대전":
                prefix = "CHDJ";
                break;
            case "광주":
                prefix = "JLGJ";
                break;
            default:
                throw new IllegalArgumentException("유효하지 않은 매장 위치입니다.");
        }
        // 해당 위치의 마지막 매장 번호를 찾아서 +1
        List<Store> existingStores = storeRepository.findByLocationOrderByStoreCodeDesc(location);
        String number = "01"; // 기본 매장 번호 설정
        
        if (!existingStores.isEmpty()) {
            String lastCode = existingStores.get(0).getStoreCode(); // 가장 최근 매장 코드 가져오기
            int lastNumber = Integer.parseInt(lastCode.substring(lastCode.length() - 2)); // 마지막 두 자리 숫자 추출
            number = String.format("%02d", lastNumber + 1); // 번호 +1 후 두 자리 형식으로 포맷
        }

        return prefix + number; // 최종 매장 코드 반환
    }

    public List<Store> findAllStores() {
        return storeRepository.findAll(); // 모든 매장 정보를 반환
    }
}