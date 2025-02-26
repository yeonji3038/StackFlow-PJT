package ssafy.StackFlow.Domain.Retrieval.dto;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }


}

