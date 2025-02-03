package ssafy.StackFlow.api.prod;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Service.product.ProductService;
import ssafy.StackFlow.api.RT.dto.RtInstructionDto;
import ssafy.StackFlow.api.RT.dto.RtRequestDto;
import ssafy.StackFlow.api.RT.dto.RtResponseDto;
import ssafy.StackFlow.api.prod.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ProdApiController {

    private final ProductService productService;

    @PostMapping("/api/product/create")
    public ResponseEntity<ProductResponseDto> createProductions(@RequestBody ProductRequestDto request) {
        try {
            List<Long> prodIds = new ArrayList<>();

            for (ProductCreateDto instruction : request.getCreate()) {
                Long prodId = productService.create(
                        instruction.getProduct_name(),
                        instruction.getProduct_detail(),
                        instruction.getProduct_code(),
                        instruction.getCategory_group(),
                        instruction.getCategory_code(),
                        instruction.getColor_code(),
                        instruction.getSize(),
                        instruction.getStock_price(),
                        instruction.getStock_quantity(),
                        instruction.getSell_price()
                ).getId();
                prodIds.add(prodId);
            }
            return ResponseEntity.ok(new ProductResponseDto("success", prodIds));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ProductResponseDto("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ProductResponseDto("error", "Unexpected error occurred"));
        }
    }

    // ✅ 상품 전체 조회
    @GetMapping("/api/product/list")
    public ResponseEntity<List<ProductListDto>> getAllProducts() {
        List<ProductListDto> products = productService.findAll().stream()
                .map(product -> new ProductListDto(
                        product.getId(),
                        product.getProdName(),
                        product.getProdCode(),
                        product.getProdDetail(),
                        product.getStockPrice(),
                        product.getSellPrice(),
                        product.getStockQuantity(),
                        new CategoryDto(  // DTO 변환 적용
                                product.getProdCate().getId(),
                                product.getProdCate().getCateCode(),
                                product.getProdCate().getCateName()
                        ),
                        new ColorDto(  // 🚀 ColorDto 적용
                                product.getColorCode().getId(),
                                product.getColorCode().getColorName(),
                                product.getColorCode().getColorCode()
                        ),
                        new SizeDto(  // 🚀 SizeDto 적용
                                product.getSize().getId(),
                                product.getSize().getSize()
                        ),
                        new CategoryGroupDto(  // DTO 변환 적용
                                product.getCateGroup().getId(),
                                product.getCateGroup().getGroupName()
                        )
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    // ✅ 상품 상세 조회
    @GetMapping("/api/product/{id}")
    public ResponseEntity<ProductDetailDto> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(new ProductDetailDto(
                        product.getId(),
                        product.getProdName(),
                        product.getProdCode(),
                        product.getProdDetail(),  //
                        product.getStockPrice(),
                        product.getSellPrice(),
                        product.getStockQuantity(),
                        new CategoryDto(  // DTO 변환 적용
                                product.getProdCate().getId(),
                                product.getProdCate().getCateCode(),
                                product.getProdCate().getCateName()
                        ),
                        new ColorDto(  // 🚀 ColorDto 적용
                                product.getColorCode().getId(),
                                product.getColorCode().getColorName(),
                                product.getColorCode().getColorCode()
                        ),
                        new SizeDto(  // 🚀 SizeDto 적용
                                product.getSize().getId(),
                                product.getSize().getSize()
                        ),
                        new CategoryGroupDto(  // DTO 변환 적용
                                product.getCateGroup().getId(),
                                product.getCateGroup().getGroupName()
                        )
                )))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ 상품 수정 API (PUT /api/product/{id})
    @PutMapping("/api/product/{id}")
    public ResponseEntity<ProductDetailDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateDto updateDto) {

        return productService.updateProduct(id, updateDto)
                .map(product -> ResponseEntity.ok(new ProductDetailDto(
                        product.getId(),
                        product.getProdName(),
                        product.getProdCode(),
                        product.getProdDetail(),
                        product.getStockPrice(),
                        product.getSellPrice(),
                        product.getStockQuantity(),
                        new CategoryDto(
                                product.getProdCate().getId(),
                                product.getProdCate().getCateCode(),
                                product.getProdCate().getCateName()
                        ),
                        new ColorDto(
                                product.getColorCode().getId(),
                                product.getColorCode().getColorName(),
                                product.getColorCode().getColorCode()
                        ),
                        new SizeDto(
                                product.getSize().getId(),
                                product.getSize().getSize()
                        ),
                        new CategoryGroupDto(
                                product.getCateGroup().getId(),
                                product.getCateGroup().getGroupName()
                        )
                )))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ 상품 삭제 API (DELETE /api/product/{id})
    @DeleteMapping("/api/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok("상품이 삭제되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
