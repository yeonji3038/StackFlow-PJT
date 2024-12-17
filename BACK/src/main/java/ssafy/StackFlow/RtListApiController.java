package ssafy.StackFlow.api.RT;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.StackFlow.Domain.RT.RT;
import ssafy.StackFlow.Repository.RT.RtApiRepository;
import ssafy.StackFlow.api.RT.dto.RtDto;
import java.util.*;
import static java.util.stream.Collectors.toList;


@RestController
@RequiredArgsConstructor
public class RtListApiController {
    private final RtApiRepository rtApiRepository;

    @GetMapping("/api/RT/list")
    public List<RtDto> RtListApi() {
        List<RT> rts = rtApiRepository.findAllWithItem();
        List<RtDto> result = rts.stream()
                .map(o -> new RtDto(o))
                .collect(toList());

        return result;
    }
}




