package ssafy.StackFlow.Domain.RT.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.RT.entity.RtProduct;
@Data
public class RtProductDto {


    private int request_quantity;

    public RtProductDto(RtProduct rtProduct) {
        request_quantity = rtProduct.getReqQuant();
    }
}

