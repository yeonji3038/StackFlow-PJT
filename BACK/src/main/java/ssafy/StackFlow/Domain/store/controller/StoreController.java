package ssafy.StackFlow.Domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.StackFlow.Domain.store.DTO.StoreDto;
import ssafy.StackFlow.Domain.store.DTO.StoreResponseDto;
import ssafy.StackFlow.Domain.store.service.StoreService;
import ssafy.StackFlow.Domain.user.DTO.UserLoginRequestDto;
import ssafy.StackFlow.Domain.user.DTO.UserLoginResponseDto;
import ssafy.StackFlow.global.docs.StoreApiSpecification;
import ssafy.StackFlow.global.response.ApiResponse;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController implements StoreApiSpecification {
    private final StoreService storeService;

    //매장 등록
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<StoreResponseDto>> registerStore(@RequestBody StoreDto storeDto) {
        StoreResponseDto registeredStore = storeService.registerStore(storeDto);
        return ResponseEntity.ok(ApiResponse.success(registeredStore));
    }


}
