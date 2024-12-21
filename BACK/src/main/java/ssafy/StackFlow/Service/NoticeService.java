package ssafy.StackFlow.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ssafy.StackFlow.Domain.File;
import ssafy.StackFlow.Domain.Notice;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Repository.NoticeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public void create(String title, String content, Signup user) {
        Notice n = new Notice();
        n.settitle(title);
        n.setcontent(content);
        n.setcreatedAt(LocalDateTime.now());
        n.setupdatedAt(LocalDateTime.now());
        n.setAuthor(user);
        this.noticeRepository.save(n);
    }

    public Page<Notice> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.noticeRepository.findAll(pageable);
    }

    public void modify(Notice notice, String title, String content) {
        notice.settitle(title);
        notice.setcontent(content);
        notice.setupdatedAt(LocalDateTime.now());
        this.noticeRepository.save(notice);
    }
}
