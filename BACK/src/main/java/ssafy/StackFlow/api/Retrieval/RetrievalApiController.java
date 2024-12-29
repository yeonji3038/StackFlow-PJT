package ssafy.StackFlow.api.Retrieval;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.Retrieval.Retrieval;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.product.ProductStore;
import ssafy.StackFlow.Repository.Retrieval.RetrievalRepository;
import ssafy.StackFlow.Repository.product.ProductRepo;
import ssafy.StackFlow.Repository.product.ProductStoreRepository;
import ssafy.StackFlow.Service.Retrieval.RetrievalService;
import ssafy.StackFlow.Service.product.ProductService;
import ssafy.StackFlow.Service.store.StoreService;
import ssafy.StackFlow.api.Retrieval.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class RetrievalApiController {
    private final RetrievalService retrievalService;
    private final ProductStoreRepository productStoreRepository;
    private final ProductRepo productRepo;
    private final RetrievalRepository retrievalRepository;
    private final StoreService storeService;
    private final ProductService productService;

    @GetMapping("/api/retrieval/product")
    public List<RetrievalProdDto> RetProdListApi() {
        Long headOfficeId = 1L;
        Store loginStore = retrievalService.getUserStore();

        List<Product> products = productRepo.findAll();

        List<RetrievalProdDto> result = products.stream()
                .map(product -> {
                    int headOfficeStock = getProductStore(headOfficeId, product.getId(), true).getStockQuantity();
                    int storeStock = getProductStore(loginStore.getId(), product.getId(), false).getStockQuantity();
                    ProductStockDto productStockDto = new ProductStockDto(product, headOfficeStock, storeStock);
                    return new RetrievalProdDto(product, productStockDto);
                })
                .collect(Collectors.toList());
        return result;
    }

    private ProductStore getProductStore(Long storeId, Long productId, boolean isMandatory) {
        return productStoreRepository.findByStoreAndProduct(
                storeService.findStoreById(storeId),
                productService.findProductById(productId)
        ).orElseGet(() -> {
            if (isMandatory) {
                throw new IllegalArgumentException("본사 재고가 존재하지 않습니다.");
            }
            ProductStore newStoreStock = new ProductStore();
            newStoreStock.setStore(storeService.findStoreById(storeId));
            newStoreStock.setProduct(productService.findProductById(productId));
            newStoreStock.setStockQuantity(0);
            return productStoreRepository.save(newStoreStock);
        });
    }


    @GetMapping("/api/retrieval/list")
    public List<RetrievalListDto> RetList1Api() {
        List<Retrieval> retList = retrievalRepository.findAll();
        List<RetrievalListDto> result = retList.stream()
                .map(o -> new RetrievalListDto(o))
                .collect(toList());
        return result;
    }

    @PostMapping("/api/retrieval/submit/admin")
    public ResponseEntity<RetrievalResponseDto> createInstructions(@RequestBody RetrievalRequestDto request) {
        try {
            List<Long> retIds = new ArrayList<>();
            for (RetrievalInstructionDto instruction : request.getInstructions()) {
                Long retId = retrievalService.createRetrievalInstruction(
                        instruction.getProductId(),
                        instruction.getStoreId(),
                        instruction.getRetrivalQuantity()
                ).getId();
                retrievalService.retrievalAdmin(instruction.getProductId(),1L,instruction.getStoreId(),instruction.getRetrivalQuantity());
                retIds.add(retId);
            }
            return ResponseEntity.ok(new RetrievalResponseDto("success", retIds));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new RetrievalResponseDto("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new RetrievalResponseDto("error", "Unexpected error occurred"));
        }
    }

    @PostMapping("/api/retrieval/submit/user")
    public ResponseEntity<RetrievalResponseDto> createInstructionsUser(@RequestBody RetrievalRequestUserDto request) {
        try {
            List<Long> retIds = new ArrayList<>();
            for (RetrievalInstructionUserDto instruction : request.getInstructions()) {
                Long retId = retrievalService.createRetrievalInstruction_User(
                        instruction.getProductId(),
                        instruction.getRetrivalQuantity()
                ).getId();
                retrievalService.retrievalUser(instruction.getProductId(),1L,instruction.getRetrivalQuantity());
                retIds.add(retId);
            }
            return ResponseEntity.ok(new RetrievalResponseDto("success", retIds));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new RetrievalResponseDto("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new RetrievalResponseDto("error", "Unexpected error occurred"));
        }
    }
}



