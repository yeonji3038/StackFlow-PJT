package ssafy.StackFlow.Controller.notice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ssafy.StackFlow.Domain.Notice;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Service.NoticeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ssafy.StackFlow.Service.user.UserService;

import javax.naming.Binding;
import java.security.Principal;
import java.util.List;

@RequestMapping("/notice")
@RequiredArgsConstructor
@Controller  // 백엔드 확인 (html 페이지 반환)
public class NoticeController {

    private final NoticeService noticeService;
    private final UserService userService;

    @GetMapping("")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Notice> paging = this.noticeService.getList(page);
        model.addAttribute("paging", paging);

        List<Notice> noticeList = this.noticeService.getList();
        model.addAttribute("noticeList", noticeList);
        return "notice";
    }

    @GetMapping(value = "/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Notice notice = this.noticeService.getNotice(id);

        model.addAttribute("notice", notice);
        return "notice_detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    public String noticeCreate(NoticeForm noticeForm) {
        return "notice_form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public String noticeCreate(@Valid NoticeForm noticeForm,
                               BindingResult bindingResult,
                               Principal principal) {
        if (bindingResult.hasErrors()) {
            return "notice_form";
        }
        Signup signup = this.userService.getUser(principal.getName());
        this.noticeService.create(noticeForm.getTitle(),
                noticeForm.getContent(), signup);
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
        return "notice_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String noticeModify(@Valid NoticeForm noticeForm,
                               BindingResult bindingResult,
                               Principal principal,
                               @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "notice_form";
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
}
