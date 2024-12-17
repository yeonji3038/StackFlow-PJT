package ssafy.StackFlow.api.RT;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.StackFlow.Service.RT.RtService;
import ssafy.StackFlow.api.RT.dto.RtInstructionDto;
import ssafy.StackFlow.api.RT.dto.RtRequestDto;
import ssafy.StackFlow.api.RT.dto.RtResponseDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rt")
@RequiredArgsConstructor
public class RtApiController {

    private final RtService rtService;

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
}
