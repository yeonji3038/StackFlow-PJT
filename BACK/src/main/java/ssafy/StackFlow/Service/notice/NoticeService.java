package ssafy.StackFlow.Service.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ssafy.StackFlow.Domain.notice.File;
import ssafy.StackFlow.Domain.notice.Notice;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Repository.notice.NoticeRepository;
import ssafy.StackFlow.Repository.notice.FileRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;

    // 공지사항 목록 조회
    public List<Notice> getList() {
        return this.noticeRepository.findAll();
    }

    // 공지사항 상세 조회
    public Notice getNotice(Long id) {
        Optional<Notice> notice = this.noticeRepository.findById(id);
        if (notice.isPresent()) {
            return notice.get();
        } else {
            throw new DataNotFoundException("Notice not found");
        }
    }

    // 공지사항 생성
    public Notice create(String title, String content, Signup user) {
        Notice n = new Notice();
        n.setTitle(title);
        n.setContent(content);
        n.setCreatedAt(LocalDateTime.now());
        n.setUpdatedAt(LocalDateTime.now());
        n.setAuthor(user);
        this.noticeRepository.save(n);
        return n;
    }

    // 파일 업로드
    public void uploadFiles(List<MultipartFile> files, Notice notice) throws IOException {
        // 파일을 Notice에 연결하여 업로드 처리
        fileService.uploadFiles(files, notice);
    }

    // 공지사항 목록 조회 (페이징 처리)
    public Page<Notice> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createdAt")));
        return this.noticeRepository.findAll(pageable);
    }

    // 공지사항 수정
    public void modify(Notice notice, String title, String content) {
        notice.setTitle(title);
        notice.setContent(content);
        notice.setUpdatedAt(LocalDateTime.now());
        this.noticeRepository.save(notice);
    }

    // 공지사항 삭제
    public void delete(Notice notice) {
        this.noticeRepository.delete(notice);
    }

    // 파일 다운로드 처리
    public File getFile(Long fileId) {
        Optional<File> file = fileRepository.findById(fileId);
        if (file.isPresent()) {
            return file.get();
        } else {
            throw new DataNotFoundException("File not found");
        }
    }
}
