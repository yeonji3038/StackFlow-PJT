package ssafy.StackFlow.Domain.Retrieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String status; // success or error
    private String message; // 상세 메시지
    private T data; // 성공 시 반환할 데이터 (에러 시 null)
}

