//Repository -> 인터페이스
package ssafy.StackFlow.Repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssafy.StackFlow.Domain.user.Signup;

@Repository
public interface UserRepository extends JpaRepository<Signup, Long> { //Signup 기본키 타입 Long
}
