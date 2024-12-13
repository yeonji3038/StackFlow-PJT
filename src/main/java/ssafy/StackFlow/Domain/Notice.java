package ssafy.StackFlow.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // bigint에 맞추기

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;  // created_at 필드 추가
    private LocalDateTime updatedAt;  // updated_at 필드 추가

    // One-to-Many 관계 설정
    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<File> files;  // Notice가 여러 File을 가질 수 있도록

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

    public void settitle(String title) {
        this.title = title;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public void setcreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setupdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

