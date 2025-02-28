package ssafy.StackFlow.Domain.Retrieval.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.Retrieval.entity.Retrieval;
import ssafy.StackFlow.Domain.Retrieval.entity.RetrievalProduct;
import ssafy.StackFlow.Domain.Retrieval.entity.RetrievalStatus;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RetrievalListDto {
    private Long retrieval_id;
    private String product_code;
    private String request_store;
    private String retrieval_store;
    private LocalDateTime request_date;
    private RetrievalStatus retrieval_status;
    private int retrieval_quantity;

    public RetrievalListDto(Retrieval retrieval) {
        retrieval_id = retrieval.getId();
        product_code = retrieval.getProdCode();
        request_store= retrieval.getReqStore();
        retrieval_store = retrieval.getRetStore();
        request_date = retrieval.getRetDate();
        retrieval_status = retrieval.getRetStatus();

        List<RetrievalProduct> retrievalProducts = retrieval.getRetrievalProducts();
        if (retrievalProducts != null && !retrievalProducts.isEmpty()) {
            retrieval_quantity = retrievalProducts.get(0).getRetQuant();
        } else {
            retrieval_quantity = 0;
        }
    }
}
