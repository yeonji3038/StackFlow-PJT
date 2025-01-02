package ssafy.StackFlow.Service.notice;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ssafy.StackFlow.Domain.notice.File;
import ssafy.StackFlow.Domain.notice.Notice;
import ssafy.StackFlow.Repository.notice.FileRepository;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    // 파일 업로드 메서드
    public void uploadFiles(List<MultipartFile> files, Notice notice) throws IOException {
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String fileType = getFileType(fileName);  // MIME 타입 대신 확장자만 저장

                // DB에 파일 정보만 저장
                File newFile = new File(fileName, fileType);  // 파일 경로를 null로 설정
                newFile.setNotice(notice);  // 파일이 속한 Notice 설정

                // DB에 파일 정보 저장
                fileRepository.save(newFile);
            }
        }
    }

    // 파일 확장자만 반환하도록 수정
    private String getFileType(String fileName) {
        // 파일 이름에서 확장자 추출
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return extension;  // 확장자만 반환
    }
}
