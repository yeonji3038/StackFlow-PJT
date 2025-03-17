package ssafy.StackFlow.global.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ssafy.StackFlow.Domain.store.DTO.StoreDto;
import ssafy.StackFlow.Domain.store.DTO.StoreResponseDto;
import ssafy.StackFlow.global.response.ApiResponse;

import java.util.List;

@Tag(name = "[본사] 매장관리", description = "매장관리 API")
public interface StoreApiSpecification {

    @Operation(
            summary = "매장 등록",
            description = """
        💡매장 등록
        
        **[ 요청 필드 ]**
        - **storeName**: 매장 이름
        - **location**: 매장 위치
        - **email**: 매장 코드 받을 주소
        - 생성된 매장은 자동으로 `storeCode`가 부여됩니다.
        
        **[ 응답 필드 ]**
        - **id**: 매장 ID
        - **storeName**: 매장 이름
        - **storeCode**: 자동 생성된 매장 코드
        - **createdAt**: 매장 생성일
    """
    )

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<StoreResponseDto>> registerStore(@RequestBody StoreDto storeDto);


    @Operation(summary = "매장 전체 조회", description = "💡매장 전체 조회")
    @GetMapping("/list/all")
    public ResponseEntity<ApiResponse<List<StoreResponseDto>>> getAllStores();
}
