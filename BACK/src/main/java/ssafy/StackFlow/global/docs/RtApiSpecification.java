package ssafy.StackFlow.global.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ssafy.StackFlow.Domain.RT.dto.*;

import java.util.List;
import java.util.Map;

@Tag(name = "[본사] RT", description = "RT API")
public interface RtApiSpecification {
    @Operation(summary = "토큰 조회", description = "💡 토큰 조회 합니다.")
    @GetMapping("/csrf")
    public CsrfToken csrf(HttpServletRequest request);


    @Operation(summary = "상품 조회", description = "💡 상품 조회 합니다.")
    @GetMapping("/product")
    public List<RtProdDto> RtProdListApi();
    @Operation(summary = "카테고리 조회", description = "💡 카테고리 조회 합니다.")
    @GetMapping("/category")
    public List<RtCategoryDto> RtCateListApi();

    @Operation(summary = "카테고리 그룹 조회", description = "💡 카테고리 그룹 조회 합니다.")
    @GetMapping("/categoryGroup")
    public List<RtCategoryGroupDto> RtCateGroupListApi();

    @Operation(summary = "컬러 조회", description = "💡 컬러 조회 합니다.")
    @GetMapping("/color")
    public List<RtColorDto> RtColorListApi();

    @Operation(summary = "사이즈 조회", description = "💡 사이즈 조회 합니다.")
    @GetMapping("/size")
    public List<RtSizeDto> RtSizeListApi();

    @Operation(summary = "매장 조회", description = "💡 매장 조회 합니다.")
    @GetMapping("/store")
    public List<RtStoreDto> RtStoreListApi();

    @Operation(summary = "RT 품목 조회", description = "💡 RT 품목 합니다.")
    @GetMapping("/all")
    public Map<String, Map<Long, String>> RtAllListApi();

    @Operation(summary = "RT 지시 요청", description = "💡 RT 지시 요청 합니다.")
    @PostMapping("/submit")
    public ResponseEntity<RtResponseDto> createInstructions(@RequestBody RtRequestDto request);


    @Operation(summary = "RT 지시 조회[본인 요청]", description = "💡 RT 지시 조회[본인] 합니다.")
    @GetMapping("/meToOtherRtlist")

    public List<MyRtDto> RtList1Api();

    @Operation(summary = "RT 지시 조회[타매장 요청]", description = "💡 RT 지시 조회[타매장] 합니다.")
    @GetMapping("/OtherToMeRtlist")
    public List<OtherRtDto> RtList2Api();

    @Operation(summary = "RT 지시 조회[전체]", description = "💡 RT 지시 조회[전체] 합니다.")
    @GetMapping("/RTAlllist")
    public List<RTAllDto> RtList3Api();

    @Operation(summary = "RT 상태 변경", description = "💡 RT 상태 변경 합니다.")
    @PutMapping("/RTstatus")
    public ResponseEntity<?> updateRtStatus(
            @RequestBody RtStatusUpdateRequest request);



}
