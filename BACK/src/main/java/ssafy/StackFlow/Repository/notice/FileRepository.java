package ssafy.StackFlow.Repository.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.notice.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
