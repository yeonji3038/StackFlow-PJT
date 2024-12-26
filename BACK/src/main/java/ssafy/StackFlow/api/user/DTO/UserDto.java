package ssafy.StackFlow.api.user.DTO;


import lombok.Data;


@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String storeCode;

}


//DTO(Data Transfer Object)
// 실제 엔터티를 객체를 통해 데이터를 전달하지 않고 DTO를 톻해 데이터를 주고 받음

//@NoArgsConstructor: 파라미터가 없는 기본 생성자를 자동으로 생성
//JPA 엔티티 클래스에서 사용 -> JPA 스펙에 따라 기본 생성자가 필요
//Bean 생성 ->  빈 등록 시 기본 생성자가 필요
//DTO 클래스 -> 기본 생성자를 이용해 객체를 생성한 후, setter를 통해 값을 설정해야 할 때 사용


//@AllArgsConstructor: 클래스의 모든 필드를 매개변수로 받는 생성자를 자동으로 생성
// 객체 생성시 모든 필드 한번에 초기화 할때 사용
// 값을 변경 할수 없는 클래스 설계 할때 생성자 통해 필드 초기회 할때 사용
// 테스트 및 간단한 초기화