package ssafy.StackFlow.Domain.Retrieval.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.Retrieval.repository.RetrievalRepository;
import ssafy.StackFlow.Domain.store.repository.StoreRepository;
import ssafy.StackFlow.Domain.product.repository.ProductRepo;
import ssafy.StackFlow.Domain.product.repository.ProductStoreRepository;
import ssafy.StackFlow.Domain.product.service.ProductService;
import ssafy.StackFlow.Domain.store.service.StoreService;
import ssafy.StackFlow.global.docs.RetrievalApiSpecification;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
public class RetrievalApiController implements RetrievalApiSpecification {
//    private final RetrievalService retrievalService;
    private final ProductStoreRepository productStoreRepository;
    private final ProductRepo productRepo;
    private final RetrievalRepository retrievalRepository;
    private final StoreService storeService;
    private final ProductService productService;
    private final StoreRepository storeRepository;


//    @GetMapping("/product")
//    public List<RetrievalProdDto> RetProdListApi() {
//        Long headOfficeId = 1L;
//        List<Product> products = productRepo.findAll();
//        List<Store> stores = storeRepository.findAll();
//        List<RetrievalProdDto> result = products.stream()
//                .map(product -> {
//                    int headOfficeStock = getProductStore(headOfficeId, product.getId(), true).getStockQuantity();
//
//                    List<StoreStockDto> storeStocks = stores.stream()
//                            .map(store -> {
//                                int storeStock = getProductStore(store.getId(), product.getId(), false).getStockQuantity();
//                                return new StoreStockDto(store.getId(), store.getStoreName(), storeStock);
//                            })
//                            .collect(Collectors.toList());
//                    return new RetrievalProdDto(product, headOfficeStock, storeStocks);
//                })
//                .collect(Collectors.toList());
//        return result;
//    }
//
//
//    private ProductStore getProductStore(Long storeId, Long productId, boolean isMandatory) {
//        return productStoreRepository.findByStoreAndProduct(
//                storeService.findStoreById(storeId),
//                productService.findProductById(productId)
//        ).orElseGet(() -> {
//            if (isMandatory) {
//                throw new IllegalArgumentException("본사 재고가 존재하지 않습니다.");
//            }
//            ProductStore newStoreStock = new ProductStore();
//            newStoreStock.setStore(storeService.findStoreById(storeId));
//            newStoreStock.setProduct(productService.findProductById(productId));
//            newStoreStock.setStockQuantity(0);
//            return productStoreRepository.save(newStoreStock);
//        });
//    }
//

//    @GetMapping("/list")
//    public List<RetrievalListDto> RetList1Api() {
//        List<Retrieval> retList = retrievalRepository.findAll();
//        List<RetrievalListDto> result = retList.stream()
//                .map(o -> new RetrievalListDto(o))
//                .collect(toList());
//        return result;
//    }
//
//    @PostMapping("/submit/admin")
//    public ResponseEntity<RetrievalResponseDto> createInstructions(@RequestBody RetrievalRequestDto request) {
//        try {
//            if (request == null || request.getInstructions() == null || request.getInstructions().isEmpty()) {
//                throw new IllegalArgumentException("Request or instructions cannot be null or empty");
//            }
//
//            List<Long> retIds = new ArrayList<>();
//            for (RetrievalInstructionDto instruction : request.getInstructions()) {
//                if (instruction.getProductId() == null || instruction.getStoreId() == null) {
//                    throw new IllegalArgumentException("Product ID and Store ID cannot be null");
//                }
//                Long retId = retrievalService.createRetrievalInstruction(
//                        instruction.getProductId(),
//                        instruction.getStoreId(),
//                        instruction.getRetrivalQuantity()
//                ).getId();
//
//                retrievalService.retrievalAdmin(
//                        instruction.getProductId(),
//                        1L,
//                        instruction.getStoreId(),
//                        instruction.getRetrivalQuantity()
//                );
//                retIds.add(retId);
//            }
//
//            return ResponseEntity.ok(new RetrievalResponseDto("success", retIds));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest()
//                    .body(new RetrievalResponseDto("error", e.getMessage()));
//        } catch (Exception e) {
//            e.printStackTrace(); // 스택 트레이스 로깅
//            return ResponseEntity.internalServerError()
//                    .body(new RetrievalResponseDto("error", "Unexpected error occurred: " + e.getMessage()));
//        }
//    }
//
//
//    @PostMapping("/submit/user")
//    public ResponseEntity<ApiResponse<List<Long>>> createInstructionsUser(@RequestBody RetrievalRequestUserDto request) {
//        if (request == null || request.getInstructions() == null || request.getInstructions().isEmpty()) {
//            throw new IllegalArgumentException("Request or instructions cannot be null or empty");
//        }
//
//        List<Long> retIds = new ArrayList<>();
//
//        for (RetrievalInstructionUserDto instruction : request.getInstructions()) {
//            if (instruction.getProductId() == null) {
//                throw new IllegalArgumentException("Product ID cannot be null");
//            }
//            if (instruction.getRetrivalQuantity() <= 0) {
//                throw new IllegalArgumentException("Retrieval quantity must be greater than 0");
//            }
//            try {
//                Long retId = retrievalService.createRetrievalInstruction_User(
//                        instruction.getProductId(),
//                        instruction.getRetrivalQuantity()
//                ).getId();
//                retrievalService.retrievalUser(
//                        instruction.getProductId(),
//                        1L,
//                        instruction.getRetrivalQuantity()
//                );
//                retIds.add(retId);
//            } catch (InsufficientStockException e) { // 재고 부족 시 예외 처리
//                return ResponseEntity.ok(
//                        new ApiResponse<>(400, "Insufficient stock for product: " + instruction.getProductId(), null)
//                );
//            }
//        }
//        return ResponseEntity.ok(new ApiResponse<>(200, "Instructions processed successfully", retIds));
//    }

}