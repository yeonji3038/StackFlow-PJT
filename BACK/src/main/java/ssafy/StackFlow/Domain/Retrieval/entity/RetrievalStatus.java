package ssafy.StackFlow.Domain.Retrieval.entity;

public enum RetrievalStatus {
    REQUEST("처리중"),
    RETRIEVAL("출고"),
    APPROVAL("확정"),
    REFUSE("불이행");
    private final String koreanRetStatus;
    RetrievalStatus(String koreanRetStatus) {
        this.koreanRetStatus = koreanRetStatus;
    }
    public String getKoreanRetStatus() {
        return koreanRetStatus;
    }
}