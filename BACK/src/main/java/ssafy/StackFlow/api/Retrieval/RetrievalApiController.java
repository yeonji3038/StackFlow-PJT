package ssafy.StackFlow.api.Retrieval;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.Retrieval.Retrieval;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Repository.Retrieval.RetrievalRepository;
import ssafy.StackFlow.Repository.product.ProductRepo;
import ssafy.StackFlow.Service.Retrieval.RetrievalService;
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
    private final ProductRepo productRepo;
    private final RetrievalRepository retrievalRepository;

    @GetMapping("/api/retrieval/product")
    public List<RetrievalProdDto> RetProdListApi() {
        List<Product> products = productRepo.findAll();
        List<RetrievalProdDto> result = products.stream()
                .map(o -> new RetrievalProdDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/retrieval/list")
    public List<RetrievalListDto> RetList1Api() {
        List<Retrieval> retList = retrievalRepository.findAll();
        List<RetrievalListDto> result = retList.stream()
                .map(o -> new RetrievalListDto(o))
                .collect(toList());
        return result;
    }
    @PostMapping("/api/retrieval/submit")
    public ResponseEntity<RetrievalResponseDto> createInstructions(@RequestBody RetrievalRequestDto request) {
        try {
            List<Long> retIds = new ArrayList<>();
            for (RetrievalInstructionDto instruction : request.getInstructions()) {
                Long retId = retrievalService.createRetrievalInstruction(
                        instruction.getProductId(),
                        instruction.getStoreId(),
                        instruction.getRetrivalQuantity()
                ).getId();
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

