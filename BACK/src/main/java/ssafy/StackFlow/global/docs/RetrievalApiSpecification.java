package ssafy.StackFlow.global.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ssafy.StackFlow.Domain.Retrieval.dto.*;

import java.util.List;

@Tag(name = "[본사/매장] 입출고", description = "입출고 API")
public interface RetrievalApiSpecification {

//    @Operation(summary = "입출고 상품 조회 [본사/매장]", description = "💡 입출고 상품 조회 합니다.")
//    @GetMapping("/product")
//    public List<RetrievalProdDto> RetProdListApi();
//
//
//    @Operation(summary = "입출고 내역 조회 [본사/매장]", description = "💡 입출고 내역 조회 합니다.")
//    @GetMapping("/list")
//    public List<RetrievalListDto> RetList1Api();


//    @Operation(summary = "입출고 요청 [본사]", description = "💡 입출고 요청[본사] 합니다.")
//    @PostMapping("/submit/admin")
//    public ResponseEntity<RetrievalResponseDto> createInstructions(@RequestBody RetrievalRequestDto request);
//
//    @Operation(summary = "입출고 요청 [매장]", description = "💡 입출고 요청[매장] 합니다.")
//    @PostMapping("/submit/user")
//    public ResponseEntity<ApiResponse<List<Long>>> createInstructionsUser(@RequestBody RetrievalRequestUserDto request);
//




}
