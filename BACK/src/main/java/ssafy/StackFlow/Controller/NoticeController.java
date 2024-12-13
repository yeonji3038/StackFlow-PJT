package ssafy.StackFlow.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.Notice;
import ssafy.StackFlow.Repository.NoticeRepository;
import ssafy.StackFlow.Service.DataNotFoundException;
import ssafy.StackFlow.Service.NoticeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String noticeCreate() {
        return "notice_form";
    }

    @PostMapping("/create")
    public String noticeCreate(@RequestParam(value = "title") String title, @RequestParam(value = "content") String content) {
        // 글 저장
        this.noticeService.create(title, content);
        return "redirect:/notice";
    }

}
