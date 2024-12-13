package ssafy.StackFlow.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.StackFlow.Domain.File;
import ssafy.StackFlow.Domain.Notice;
import ssafy.StackFlow.Repository.NoticeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> getList() {
        return this.noticeRepository.findAll();
    }

    public Notice getNotice(Long id) {
        Optional<Notice> notice = this.noticeRepository.findById(id);
        if (notice.isPresent()) {
            return notice.get();
        } else {
            throw new DataNotFoundException("Notice not found");
        }
    }

    public void create(String title, String content) {
        Notice n = new Notice();
        n.settitle(title);
        n.setcontent(content);
        n.setcreatedAt(LocalDateTime.now());
        n.setupdatedAt(LocalDateTime.now());
        this.noticeRepository.save(n);
    }
}
