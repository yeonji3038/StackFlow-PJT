package ssafy.StackFlow.global.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.notice.DTO.NoticeDto;
import ssafy.StackFlow.Domain.notice.entity.Notice;
import ssafy.StackFlow.Domain.product.dto.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "[본사/매장] 상품 관리", description = "상품 관리 API")
public interface ProductApiSpecification {

    @Operation(summary ="상품 등록 [본사]", description = "💡 상품 등록 합니다.")
    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProductions(@RequestBody ProductRequestDto request);


    @Operation(summary = "상품 목록 조회 [본사/매장]", description = "💡 상품 조회 합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<ProductListDto>> getAllProducts();


    @Operation(summary = "상품 상세 조회 [본사/매장]", description = "💡 상품 상세 조회[본사/매장] 합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDto> getProductById(@PathVariable Long id);

    @Operation(summary = "상품 수정 [본사]", description = "💡 상품 수정[본사] 합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDetailDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateDto updateDto) ;

    @Operation(summary = "상품 삭제 [본사]", description = "💡 상품 삭제[본사] 합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id);



}
