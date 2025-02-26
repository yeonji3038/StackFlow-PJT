package ssafy.StackFlow.Domain.RT.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.RT.entity.RT;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class OtherRtDto {
    private Long RT_id;
    private String product_code;
    private String product_name;
    private String color_code;
    private String product_size;
    private LocalDateTime request_date;
    private String request_store;
    private List<RtProductDto> rt_products;

    public OtherRtDto(RT rt) {
        RT_id = rt.getId();
        product_code = rt.getProdCode();
        product_name = rt.getProdName();
        color_code = rt.getColorCode();
        product_size = rt.getSize();
        request_store = rt.getMyStore();
        request_date = rt.getReqDate();
        rt_products = rt.getRtProducts().stream()
                .map(rtProduct -> new RtProductDto(rtProduct))
                .collect(toList());
    }
}