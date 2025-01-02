package ssafy.StackFlow.Domain.notice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String fileName;  // 파일 이름
    private String fileType;  // 파일 유형

    @ManyToOne
    private Notice notice;  // 어느 공지사항에 속하는 파일인지 연결

    // 파일 메타데이터만 저장하는 생성자
    public File(String fileName, String fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public File() {
    }
}
