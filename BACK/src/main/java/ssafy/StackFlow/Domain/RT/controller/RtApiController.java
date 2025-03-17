package ssafy.StackFlow.Domain.RT.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.RT.dto.*;
import ssafy.StackFlow.Domain.RT.entity.RT;
import ssafy.StackFlow.Domain.RT.entity.RtStatus;
import ssafy.StackFlow.Domain.RT.service.RtService;
import ssafy.StackFlow.Domain.product.entity.Product;
import ssafy.StackFlow.global.docs.NoticeApiSpecification;
import ssafy.StackFlow.global.docs.RtApiSpecification;
import ssafy.StackFlow.global.response.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rt")
public class RtApiController implements RtApiSpecification {
    private final RtService rtService;

    /**
     * 인증 여부 확인
     */
    private void checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("인증되지 않은 사용자입니다");
        }
    }

    /**
     * 상품 검색
     */
    @GetMapping("/search")
    public ApiResponse<List<RtProdDto>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String categoryGroup,
            @RequestParam(required = false) String categoryCode,
            @RequestParam(required = false) String colorCode,
            @RequestParam(required = false) String size) {

        try {
            checkAuthentication();

            List<Product> products;

            if (keyword != null && !keyword.isEmpty()) {
                products = rtService.search(keyword);
            } else if (categoryGroup != null || categoryCode != null || colorCode != null || size != null) {
                products = rtService.searchByFilters(categoryGroup, categoryCode, colorCode, size);
            } else {
                products = new ArrayList<>();
            }

            List<RtProdDto> result = products.stream()
                    .map(RtProdDto::new)
                    .collect(Collectors.toList());

            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
    }

    /**
     * RT 요청 생성
     */
    @PostMapping("/submit")
    public ApiResponse<RtResponseDto> createInstructions(@RequestBody RtRequestDto request) {
        try {
            checkAuthentication();

            List<Long> rtIds = new ArrayList<>();
            for (RtInstructionDto instruction : request.getInstructions()) {
                Long rtId = rtService.createInstruction(
                        instruction.getProductId(),
                        instruction.getStoreId(),
                        instruction.getRequestQuantity()
                ).getId();
                rtIds.add(rtId);
            }

            RtResponseDto response = new RtResponseDto("success", rtIds);
            return ApiResponse.created("재고 요청이 성공적으로 생성되었습니다.", response);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "요청 처리 중 오류가 발생했습니다: " + e.getMessage(), null);
        }
    }

    /**
     * 내 매장에서 요청한 RT 목록 조회
     */
    @GetMapping("/meToOtherRtlist")
    public ApiResponse<List<MyRtDto>> getMyRTRequests() {
        try {
            checkAuthentication();

            List<RT> rtList = rtService.getMyRTRequests();
            List<MyRtDto> result = rtList.stream()
                    .map(MyRtDto::new)
                    .collect(Collectors.toList());

            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
    }

    /**
     * 다른 매장이 내 매장에 요청한 RT 목록 조회
     */
    @GetMapping("/OtherToMeRtlist")
    public ApiResponse<List<OtherRtDto>> getOtherRTRequests() {
        try {
            checkAuthentication();

            List<RT> rtList = rtService.getOtherRTRequests();
            List<OtherRtDto> result = rtList.stream()
                    .map(OtherRtDto::new)
                    .collect(Collectors.toList());

            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
    }

    /**
     * 모든 RT 목록 조회
     */
    @GetMapping("/RTAlllist")
    public ApiResponse<List<RTAllDto>> getAllRTs() {
        try {
            checkAuthentication();

            List<RT> rtList = rtService.getAllRTsWithProducts();
            List<RTAllDto> result = rtList.stream()
                    .map(RTAllDto::new)
                    .collect(Collectors.toList());

            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
    }

    /**
     * RT 상태 업데이트
     */
    @PutMapping("/RTstatus")
    public ApiResponse<Map<String, String>> updateRtStatus(@RequestBody RtStatusUpdateRequest request) {
        try {
            checkAuthentication();

            RtStatus newStatus = RtStatus.valueOf(request.getAction());
            rtService.updateRtStatus(request.getSelectedRequests(), newStatus);

            String message = newStatus == RtStatus.APPROVAL ?
                    "승인 처리가 완료되었습니다." : "거절 처리가 완료되었습니다.";

            return ApiResponse.success(message, Map.of("message", message));
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        }
    }
}