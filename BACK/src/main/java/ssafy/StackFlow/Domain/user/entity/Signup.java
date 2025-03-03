package ssafy.StackFlow.Domain.user.entity;


import jakarta.persistence.*;
import lombok.*;
import ssafy.StackFlow.Domain.store.entity.Store;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "signup")
public class Signup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 사용자 고유 ID

    @Column(nullable = false, unique = true)
    private String username; // ID

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    @Enumerated(EnumType.STRING)  // Enum 값을 문자열로 저장
    @Column(nullable = false)
    private Role role;  // 역할 (매장 관리자 또는 본사 관리자)

    @Column
    private Timestamp createdAt; // 생성일

    // Store 엔티티와의 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}