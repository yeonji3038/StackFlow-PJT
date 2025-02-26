package ssafy.StackFlow.Domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.notice.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
