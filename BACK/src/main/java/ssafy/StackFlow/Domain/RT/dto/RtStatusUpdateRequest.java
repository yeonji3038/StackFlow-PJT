package ssafy.StackFlow.Domain.RT.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RtStatusUpdateRequest {
    private List<Long> selectedRequests;
    private String action;
}
