package ssafy.StackFlow.Domain.RT.entity;

public enum RtStatus {
    REQUEST("미확정"),
    APPROVAL("확정"),
    REFUSE("불이행");

    private final String koreanStatus;

    RtStatus(String koreanStatus) {
        this.koreanStatus = koreanStatus;
    }

    public String getKoreanStatus() {
        return koreanStatus;
    }
}