package ssafy.StackFlow.Domain;

public enum RtStatus {
    REQUEST("요청중"),
    APPROVAL("승인"),
    REFUSE("거절"),
    DELIVERY("배달중"),
    COMPLETE("배달완료");

    private final String koreanStatus;

    RtStatus(String koreanStatus) {
        this.koreanStatus = koreanStatus;
    }

    public String getKoreanStatus() {
        return koreanStatus;
    }
}
