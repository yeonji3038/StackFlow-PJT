package ssafy.StackFlow.Service.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ssafy.StackFlow.Domain.notice.File;
import ssafy.StackFlow.Domain.notice.Notice;
import ssafy.StackFlow.Repository.notice.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    // 업로드 디렉토리 경로 설정
    private static final String UPLOAD_DIR = "uploads/";  // 경로를 상대 경로로 수정

    // 파일 업로드 메서드
    public void uploadFiles(List<MultipartFile> files, Notice notice) throws IOException {
        if (files != null && !files.isEmpty()) {
            // 업로드 경로 절대 경로로 설정
            String uploadPath = System.getProperty("user.dir") + "/" + UPLOAD_DIR;

            // Path 객체 생성 (uploadPath의 디렉토리)
            Path path = Paths.get(uploadPath);

            // 디렉토리가 없다면 생성
            if (Files.notExists(path)) {
                Files.createDirectories(path);  // 디렉토리 생성
                System.out.println("Directory created at: " + uploadPath);  // 디렉토리 생성 확인
            }

            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                Path filePath = path.resolve(fileName);  // 파일 경로 설정

                // 파일을 지정된 경로로 저장
                file.transferTo(filePath.toFile());  // 파일을 업로드 디렉토리에 저장

                // 파일 타입 설정 (파일 이름에서 확장자를 추출)
                String fileType = getFileType(fileName);  // MIME 타입 대신 확장자만 저장

                // File 엔티티 생성 및 관련 정보 설정
                File newFile = new File(fileName, filePath.toString(), fileType);
                newFile.setNotice(notice);  // 파일이 속한 Notice 설정

                // DB에 파일 정보 저장
                fileRepository.save(newFile);
            }
        }
    }

    // 단일 파일 업로드 메서드
    public void saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);  // 절대 경로로 설정하여 파일 저장

        // 디렉토리가 없다면 생성
        if (Files.notExists(path.getParent())) {
            Files.createDirectories(path.getParent());  // 디렉토리 생성
        }

        // 파일 저장
        file.transferTo(path.toFile());  // 실제로 파일을 지정한 경로로 저장

        // 파일 타입 설정 (파일 이름에서 확장자를 추출)
        String fileType = getFileType(fileName);  // MIME 타입 대신 확장자만 저장

        // DB에 파일 정보 저장
        File newFile = new File(fileName, path.toString(), fileType);  // 경로와 타입 저장
        fileRepository.save(newFile);
    }

    // 파일 확장자만 반환하도록 수정
    private String getFileType(String fileName) {
        // 파일 이름에서 확장자 추출
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return extension;  // 확장자만 반환
    }
}
