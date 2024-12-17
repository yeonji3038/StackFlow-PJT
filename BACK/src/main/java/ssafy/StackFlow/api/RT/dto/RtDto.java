package ssafy.StackFlow.api.RT.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.RT.RT;
import ssafy.StackFlow.Domain.RT.RtStatus;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class RtDto{
    private Long RT_id;
    private String product_code;
    private String product_name;
    private String color_code;
    private String product_size;
    private String request_store;
    private LocalDateTime request_date;
    private RtStatus rt_status;
    private List<RtProductDto> rt_products;

    public RtDto(RT rt) {
        RT_id = rt.getId();
        product_code = rt.getProdCode();
        product_name = rt.getProdName();
        color_code = rt.getColorCode();
        product_size = rt.getSize();
        request_store = rt.getReqStore();
        request_date = rt.getReqDate();
        rt_status = rt.getStatus();
        rt_products = rt.getRtProducts().stream()
                .map(rtProduct -> new RtProductDto(rtProduct))
                .collect(toList());
    }
}