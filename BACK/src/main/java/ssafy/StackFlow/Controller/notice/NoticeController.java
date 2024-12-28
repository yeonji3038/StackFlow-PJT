package ssafy.StackFlow.Controller.notice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ssafy.StackFlow.Domain.notice.File;
import ssafy.StackFlow.Domain.notice.Notice;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Repository.notice.FileRepository;
import ssafy.StackFlow.Service.notice.NoticeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ssafy.StackFlow.Service.user.UserService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@RequestMapping("/notice")
@RequiredArgsConstructor
@Controller  // 백엔드 확인 (html 페이지 반환)
public class NoticeController {

    private final NoticeService noticeService;
    private final UserService userService;
    private final FileRepository fileRepository;

    @GetMapping("")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Notice> paging = this.noticeService.getList(page);
        model.addAttribute("paging", paging);

        List<Notice> noticeList = this.noticeService.getList();
        model.addAttribute("noticeList", noticeList);
        return "notice/notice";
    }

    @GetMapping(value = "/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Notice notice = this.noticeService.getNotice(id);

        model.addAttribute("notice", notice);
        return "notice/notice_detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    public String noticeCreate(NoticeForm noticeForm) {
        return "notice/notice_form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public String noticeCreate(@Valid NoticeForm noticeForm,
                               BindingResult bindingResult,
                               Principal principal,
                               @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
        if (bindingResult.hasErrors()) {
            return "notice/notice_form";
        }
        Signup signup = this.userService.getUser(principal.getName());
        Notice notice = this.noticeService.create(noticeForm.getTitle(), noticeForm.getContent(), signup);

        this.noticeService.uploadFiles(files, notice);

        return "redirect:/notice";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String noticeModify(NoticeForm noticeForm,
                               @PathVariable("id") Long id,
                               Principal principal) {
        Notice notice = this.noticeService.getNotice(id);
        if(!notice.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        noticeForm.setTitle(notice.getTitle());
        noticeForm.setContent(notice.getContent());
        return "notice/notice_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String noticeModify(@Valid NoticeForm noticeForm,
                               BindingResult bindingResult,
                               Principal principal,
                               @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "notice/notice_form";
        }
        Notice notice = this.noticeService.getNotice(id);
        if (!notice.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.noticeService.modify(notice, noticeForm.getTitle(), noticeForm.getContent());
        return String.format("redirect:/notice/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String noticeDelete(Principal principal,
                               @PathVariable("id") Long id) {
        Notice notice = this.noticeService.getNotice(id);
        if (!notice.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.noticeService.delete(notice);
        return "redirect:/notice";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));

        Path filePath = Paths.get(file.getFilePath());
        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found on server");
        }

        // 파일의 확장자에 따라 Content-Type을 설정 (PDF 파일인 경우)
        String contentType = "application/octet-stream";  // 기본은 일반 파일 타입
        if (file.getFileName().endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (file.getFileName().endsWith(".jpg") || file.getFileName().endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else if (file.getFileName().endsWith(".png")) {
            contentType = "image/png";
        } else if (file.getFileName().endsWith(".txt")) {
            contentType = "text/plain";
        }

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(resource);
    }

}
