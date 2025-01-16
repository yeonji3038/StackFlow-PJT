package ssafy.StackFlow.Domain.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.user.UserRole;


@Getter
@Setter
@Entity

public class Signup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //엔티티의 기본 키를 자동으로 생성하는 방법을 지정
    //@GeneratedValue -> 해당 필드 값 데이터베이스가 자동으로 생성하도록 지정
    //strategy = GenerationType.IDENTITY  ->  IDENTITY 컬럼을 사용하여 기본 키 값을 생성
    private Long id;  //사용자 고유 ID

    @Column(unique = true)  //(unique = true) -> 유일한 값만 저장할수 있도록 설정, 중복X
    // 동일값이 저장  되는걸 막을수 있음

    private String username; //사용자 이름

    private String password; //비밀번호

    @Column(unique = true)
    private String email; //이메일

    @Column
    private String role; // 사용자 역할 추가

    // 매장 정보 참조
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id") // 외래키
    @JsonIgnore
    private Store store;

    // storeCode 필드 추가
    @Transient // JPA에서 관리하지 않는 필드
    private String storeCode;

    // Store의 storeCode를 가져오는 메서드
    public String getStoreCode() {
        return store != null ? store.getStoreCode() : null; // Store가 null이 아닐 경우 storeCode 반환

    }

    // Store의 storeCode를 가져오는 메서드
    public String getStoreName() {
        return store != null ? store.getStoreName() : null; // Store가 null이 아닐 경우 storeCode 반환


    }

    public boolean isAdmin() {
        return UserRole.ADMIN.getValue().equals(this.role);
    }

}
