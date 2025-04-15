package ssafy.StackFlow.global.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.RT.dto.*;
import ssafy.StackFlow.global.response.ApiResponse;

import java.util.List;
import java.util.Map;

@Tag(name = "[본사] RT", description = "RT API")
public interface RtApiSpecification {
    @Operation(summary = "RT 품목 조회", description = "💡 RT 품목 검색합니다.")
    @GetMapping("/search")
    public ApiResponse<List<RtProdDto>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String categoryGroup,
            @RequestParam(required = false) String categoryCode,
            @RequestParam(required = false) String colorCode,
            @RequestParam(required = false) String size);

    @Operation(summary = "RT 지시 요청", description = "💡 RT 지시 요청 합니다.")
    @PostMapping("/submit")
    public ApiResponse<RtResponseDto> createInstructions(@RequestBody RtRequestDto request);

    @Operation(summary = "RT 지시 조회[본인 요청]", description = "💡 RT 지시 조회[본인] 합니다.")
    @GetMapping("/meToOtherRtlist")
    public ApiResponse<List<MyRtDto>> getMyRTRequests();

    @Operation(summary = "RT 지시 조회[타매장 요청]", description = "💡 RT 지시 조회[타매장] 합니다.")
    @GetMapping("/OtherToMeRtlist")
    public ApiResponse<List<OtherRtDto>> getOtherRTRequests();

    @Operation(summary = "RT 지시 조회[전체]", description = "💡 RT 지시 조회[전체] 합니다.")
    @GetMapping("/RTAlllist")
    public ApiResponse<List<RTAllDto>> getAllRTs();

    @Operation(summary = "RT 상태 변경", description = "💡 RT 상태 변경 합니다.")
    @PutMapping("/RTstatus")
    public ApiResponse<Map<String, String>> updateRtStatus(@RequestBody RtStatusUpdateRequest request);


}