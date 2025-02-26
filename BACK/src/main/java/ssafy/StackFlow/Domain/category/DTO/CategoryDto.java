package ssafy.StackFlow.Domain.category.DTO;

import lombok.Data;

@Data
public class CategoryDto {
    private String cateCode; // 카테고리 코드
    private String cateName; // 카테고리 이름
    private Long groupId; // 카테고리 그룹 ID

}
