package ssafy.StackFlow.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;

    // Many-to-One 관계 설정
    @ManyToOne
    @JoinColumn(name = "notice_id")  // 외래 키 (notice_id) 컬럼명 설정
    private Notice notice;  // File이 참조하는 Notice

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.updatedAt = this.createdAt;  // 최초 생성 시 updatedAt도 설정
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();  // 수정 시 updatedAt 갱신
    }
}

