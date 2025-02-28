package ssafy.StackFlow.global.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.Retrieval.dto.*;
import ssafy.StackFlow.Domain.notice.DTO.NoticeDto;
import ssafy.StackFlow.Domain.notice.entity.Notice;

import java.security.Principal;
import java.util.List;

@Tag(name = "[본사/매장] 공지사항", description = "공지사항 API")
public interface NoticeApiSpecification {

    @Operation(summary = "공지사항 생성 [본사]", description = "💡 공지사항 생성 합니다.")
    @PostMapping("/create")
    public ResponseEntity<Notice> createNotice(@RequestBody NoticeDto request, Principal principal);


    @Operation(summary = "공지사항 목록 조회 [본사/매장]", description = "💡 공지사항 조회 합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<NoticeDto>> getNoticeList();


    @Operation(summary = "공지사항 상세 조회 [본사/매장]", description = "💡공지사항 상세 조회[본사/매장] 합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<NoticeDto> getNotice(@PathVariable("id") Long id);

    @Operation(summary = "공지사항 수정 [본사]", description = "💡 공지사항 수정[본사] 합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<Notice> modifyNotice(@PathVariable("id") Long id,
                                               @RequestBody NoticeDto request,
                                               Principal principal) ;

    @Operation(summary = "공지사항 삭제 [본사]", description = "💡 공지사항 삭제[본사] 합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable("id") Long id,
                                             Principal principal);



}
