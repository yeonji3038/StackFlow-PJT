//Repository -> 인터페이스
package ssafy.StackFlow.Domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.user.entity.Role;
import ssafy.StackFlow.Domain.user.entity.Signup;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Signup, Long> { //Signup 기본키 타입 Long
    Optional<Signup> findByusername(String username); //사용자 ID로 Signup 엔티티를 조회하는 findByusername 메서드를 User 리포지터리에 추가

    Optional<Object> findByEmail(String email);

    Optional<Object> findByUsername(String username);

    boolean existsByUsername(String username);  // 아이디 중복 검사

    List<Signup> findAllByRole(Role role); // 가입한 매니저 조회
}
