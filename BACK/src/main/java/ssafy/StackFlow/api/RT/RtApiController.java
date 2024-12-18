package ssafy.StackFlow.api.RT;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.RT.RT;
import ssafy.StackFlow.Repository.RT.RtApiRepository;
import ssafy.StackFlow.Service.RT.RtService;
import ssafy.StackFlow.api.RT.dto.RtDto;
import ssafy.StackFlow.api.RT.dto.RtInstructionDto;
import ssafy.StackFlow.api.RT.dto.RtRequestDto;
import ssafy.StackFlow.api.RT.dto.RtResponseDto;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/rt")
@RequiredArgsConstructor
public class RtApiController {

    private final RtService rtService;
    private final RtApiRepository rtApiRepository;

    @PostMapping("/submit")
    public ResponseEntity<RtResponseDto> createInstructions(@RequestBody RtRequestDto request) {
        try {
            List<Long> rtIds = new ArrayList<>();

            for (RtInstructionDto instruction : request.getInstructions()) {
                Long rtId = rtService.createInstruction(
                        instruction.getProductId(),
                        instruction.getStoreId(),
                        instruction.getRequestQuantity()
                );
                rtIds.add(rtId);
            }

            return ResponseEntity.ok(new RtResponseDto("success", rtIds));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new RtResponseDto("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new RtResponseDto("error", "Unexpected error occurred"));
        }
    }

    @GetMapping("list")
    public List<RtDto> RtListApi() {
        List<RT> rts = rtApiRepository.findAllWithItem();
        List<RtDto> result = rts.stream()
                .map(o -> new RtDto(o))
                .collect(toList());

        return result;
    }
}
