package ssafy.StackFlow.api.Notice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ssafy.StackFlow.Domain.notice.Notice;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Service.notice.NoticeService;
import ssafy.StackFlow.Service.user.UserService;

import java.security.Principal;
import java.util.List;

@RequestMapping("/notice")
@RequiredArgsConstructor
@RestController  // 프론트 api 넘겨주기 (json 응답 반환)
public class NoticeApiController {
    // ----------------------------------
    // **API 응답을 위한 엔드포인트 추가**
    // ----------------------------------

    private final NoticeService noticeService;
    private final UserService userService;

    // 공지사항 목록 조회 (API)
    @GetMapping("/api")
    public ResponseEntity<List<Notice>> getNoticeList() {
        List<Notice> notices = this.noticeService.getList();
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

    // 공지사항 상세 조회 (API)
    @GetMapping("/api/{id}")
    public ResponseEntity<Notice> getNotice(@PathVariable("id") Long id) {
        Notice notice = this.noticeService.getNotice(id);
        return new ResponseEntity<>(notice, HttpStatus.OK);
    }

    // 공지사항 생성 (API)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/create")
    public ResponseEntity<Notice> createNotice(@RequestParam("title") String title,
                                               @RequestParam("content") String content,
                                               Principal principal) {
        Signup signup = this.userService.getUser(principal.getName());
        Notice notice = this.noticeService.create(title, content, signup);
        return new ResponseEntity<>(notice, HttpStatus.CREATED);
    }

    // 공지사항 수정 (API)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/api/modify/{id}")
    public ResponseEntity<Notice> modifyNotice(@PathVariable("id") Long id,
                                               @RequestParam("title") String title,
                                               @RequestParam("content") String content,
                                               Principal principal) {
        Notice notice = this.noticeService.getNotice(id);
        if (!notice.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }
        this.noticeService.modify(notice, title, content);
        return new ResponseEntity<>(notice, HttpStatus.OK);
    }

    // 공지사항 삭제 (API)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/api/delete/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable("id") Long id,
                                             Principal principal) {
        Notice notice = this.noticeService.getNotice(id);
        if (notice == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다.");
        }
        if (!notice.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        }
        this.noticeService.delete(notice);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 삭제 성공
    }
}
