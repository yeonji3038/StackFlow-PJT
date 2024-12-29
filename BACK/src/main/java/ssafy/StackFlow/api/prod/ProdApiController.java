package ssafy.StackFlow.api.prod;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.StackFlow.Service.product.ProductService;
import ssafy.StackFlow.api.RT.dto.RtInstructionDto;
import ssafy.StackFlow.api.RT.dto.RtRequestDto;
import ssafy.StackFlow.api.RT.dto.RtResponseDto;
import ssafy.StackFlow.api.prod.dto.ProductCreateDto;
import ssafy.StackFlow.api.prod.dto.ProductRequestDto;
import ssafy.StackFlow.api.prod.dto.ProductResponseDto;

import java.util.ArrayList;
import java.util.List;

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
}
