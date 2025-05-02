package ssafy.StackFlow.Domain.RT.dto;

import lombok.Getter;
import ssafy.StackFlow.Domain.RT.entity.RT;
import ssafy.StackFlow.Domain.RT.entity.RtProduct;
import ssafy.StackFlow.Domain.product.entity.Product;

import java.time.LocalDateTime;

@Getter
public class RtInstructionStatusDto {
    private String productCode;
    private String productName;
    private String colorCode;
    private String productSize;
    private String responseStore;
    private int requestQuantity;
    private LocalDateTime requestDate;
    private String rtStatus;

    public RtInstructionStatusDto(RT rt) {
        RtProduct rtProduct = rt.getRtProducts().get(0); // assuming 1:1 for now
        Product product = rtProduct.getProduct();

        this.productCode = product.getProdCode();
        this.productName = product.getProdName();
        this.colorCode = product.getColorCode() != null ? product.getColorCode().getColorCode() : null;
        this.productSize = product.getSize() != null ? product.getSize().getSize() : null;
        this.responseStore = rt.getReqStore();
        this.requestQuantity = rtProduct.getReqQuant();
        this.requestDate = rt.getReqDate();
        this.rtStatus = rt.getStatus().name();
    }
}