package ssafy.StackFlow.api.RT.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.RT.RtProduct;
@Data
public class RtProductDto {


    private int request_quantity;

    public RtProductDto(RtProduct rtProduct) {
        request_quantity = rtProduct.getReqQuant();
    }
}

