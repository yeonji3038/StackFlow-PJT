package ssafy.StackFlow.Domain.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.notice.entity.Notice;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Notice findByTitle(String title);
    Notice findByTitleAndContent(String title, String content);
    List<Notice> findByTitleLike(String title);
    Page<Notice> findAll(Pageable pageable);
}
