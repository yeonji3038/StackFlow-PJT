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

    @Operation(summary = "매장 등록", description = "💡매장 등록")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<StoreResponseDto>> registerStore(@RequestBody StoreDto storeDto);


    @Operation(summary = "매장 전체 조회", description = "💡매장 전체 조회")
    @GetMapping("/list/all")
    public ResponseEntity<ApiResponse<List<StoreResponseDto>>> getAllStores();
}
