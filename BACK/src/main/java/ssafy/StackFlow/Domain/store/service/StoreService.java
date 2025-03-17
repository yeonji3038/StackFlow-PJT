package ssafy.StackFlow.Domain.store.service;

import lombok.RequiredArgsConstructor;
import net.crizin.KoreanRomanizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.mail.MailService;
import ssafy.StackFlow.Domain.store.DTO.StoreDto;
import ssafy.StackFlow.Domain.store.DTO.StoreResponseDto;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.store.repository.StoreRepository;
import ssafy.StackFlow.Domain.user.DTO.UserDto;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true) // 이 서비스의 모든 메서드는 기본적으로 읽기 전용 트랜잭션을 사용
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 어노테이션
public class StoreService {
    private final StoreRepository storeRepository; // StoreRepository 주입
    private final MailService mailService; // MailService 주입

    // 매장 등록 및 코드 생성
    @Transactional
    public StoreResponseDto registerStore(StoreDto storeDto) {
        // DTO -> Entity 변환 후 저장 (매장 코드는 저장 시 자동 생성)
        Store store = StoreDto.toEntity(storeDto);

        // 매장 코드 자동 생성
        String generatedCode = generateStoreCode(storeDto.getLocation());

        store.setStoreCode(generatedCode);  // Entity에 생성된 매장 코드를 설정

        Store savedStore = storeRepository.save(store);

        // 매장 코드가 생성되면 이메일 전송
        String emailContent = "안녕하세요, 귀하의 매장 코드: " + generatedCode;
        mailService.sendSimpleMail(storeDto.getEmail(), emailContent); // 이메일 전송

        // Entity -> StoreResponseDto 변환 후 반환
        return StoreResponseDto.fromEntity(savedStore);  // 매장 코드 포함된 StoreResponseDto 반환
    }

    private String generateStoreCode(String storeLocation) {
        // 공백으로 분리하여 첫번째와 두번째 토큰만 사용 (예: "충청남도 천안시 서북구" -> ["충청남도", "천안시", ...])
        String[] tokens = storeLocation.trim().split(" ");

        // 첫번째 토큰: 행정구역 (ex: "부산광역시" or "충청남도")
        String adminArea = tokens[0];
        // 두번째 토큰: 도시/군/구 (ex: "해운대구" or "천안시")
        String subArea = (tokens.length >= 2) ? tokens[1] : "";

        // 로마자 변환 (예: "부산광역시" -> "BUSAN", "천안시" -> "CHEONAN")
        String romanAdmin = convertToRoman(adminArea);
        String romanSub = convertToRoman(subArea);

        // 각 부분의 첫 두 글자만 추출
        romanAdmin = (romanAdmin.length() >= 2) ? romanAdmin.substring(0, 2).toUpperCase() : romanAdmin.toUpperCase();
        romanSub = (romanSub.length() >= 2) ? romanSub.substring(0, 2).toUpperCase() : romanSub.toUpperCase();

        // 해당 지역의 매장 수를 기반으로 번호 생성 (01부터 시작)
        long storeCount = storeRepository.countByAdminAreaAndSubArea(adminArea, subArea);


        String storeNumber = String.format("%02d", storeCount + 1);

        // 최종 코드 생성 (예: "BUHD01" 또는 "CNCA01")
        return romanAdmin + romanSub + storeNumber;
    }

    private String convertToRoman(String korean) {
        // 한글 -> 로마자 변환하는 로직 구현 (예: "부산광역시" -> "BUSAN", "천안시" -> "CHEONAN")
        return KoreanRomanizer.romanize(korean);
    }

    //매장 전체 조회
    public List<StoreResponseDto> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(StoreResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

}
