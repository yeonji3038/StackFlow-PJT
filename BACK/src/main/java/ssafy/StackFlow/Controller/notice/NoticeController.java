package ssafy.StackFlow.Controller.notice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.Notice;
import ssafy.StackFlow.Service.NoticeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.Binding;
import java.util.List;

@RequestMapping("/notice")
@RequiredArgsConstructor
@Controller
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("")
    public String list(Model model) {
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

    @GetMapping("/create")
    public String noticeCreate(NoticeForm noticeForm) {
        return "notice_form";
    }

    @PostMapping("/create")
    public String noticeCreate(@Valid NoticeForm noticeForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "notice_form";
        }
        this.noticeService.create(noticeForm.getTitle(),
                noticeForm.getContent());
        return "redirect:/notice";
    }

}
