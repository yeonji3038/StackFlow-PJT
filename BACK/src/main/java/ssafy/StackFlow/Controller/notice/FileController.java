package ssafy.StackFlow.Controller.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.StackFlow.Service.notice.FileService;

import java.io.IOException;

@Controller
@RequestMapping("/files")  // 파일 관련 URL 매핑
public class FileController {

    @Autowired
    private FileService fileService;

    // 파일 업로드 페이지를 반환하는 메서드
    @GetMapping("/upload")
    public String showUploadPage() {
        return "uploadPage";  // 업로드 페이지를 보여주는 뷰 이름 (예: uploadPage.html)
    }

    // 파일 업로드 요청 처리
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            // fileService의 saveFile 메서드를 호출하여 파일 저장
            fileService.saveFile(file);
            model.addAttribute("message", "File uploaded successfully!");
            return "uploadPage";  // 업로드 완료 후 다시 업로드 페이지로 돌아가거나, 다른 페이지로 리다이렉션
        } catch (IOException e) {
            model.addAttribute("message", "Error uploading file!");
            return "uploadPage";  // 실패 시 업로드 페이지로 돌아갑니다.
        }
    }
}
