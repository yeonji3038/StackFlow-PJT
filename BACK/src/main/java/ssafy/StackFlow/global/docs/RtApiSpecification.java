package ssafy.StackFlow.global.docs;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ssafy.StackFlow.Domain.RT.dto.*;
import ssafy.StackFlow.global.response.ApiResponse;

import java.util.List;
import java.util.Map;

public interface RtApiSpecification {

    /**
     * 상품 검색
     */
    ApiResponse<List<RtProdDto>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String categoryGroup,
            @RequestParam(required = false) String categoryCode,
            @RequestParam(required = false) String colorCode,
            @RequestParam(required = false) String size);

    /**
     * RT 요청 생성
     */
    ApiResponse<RtResponseDto> createInstructions(@RequestBody RtRequestDto request);

    /**
     * 내 매장에서 요청한 RT 목록 조회
     */
    ApiResponse<List<MyRtDto>> getMyRTRequests();

    /**
     * 다른 매장이 내 매장에 요청한 RT 목록 조회
     */
    ApiResponse<List<OtherRtDto>> getOtherRTRequests();

    /**
     * 모든 RT 목록 조회
     */
    ApiResponse<List<RTAllDto>> getAllRTs();

    /**
     * RT 상태 업데이트
     */
    ApiResponse<Map<String, String>> updateRtStatus(@RequestBody RtStatusUpdateRequest request);
}