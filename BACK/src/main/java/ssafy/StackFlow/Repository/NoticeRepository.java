package ssafy.StackFlow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
