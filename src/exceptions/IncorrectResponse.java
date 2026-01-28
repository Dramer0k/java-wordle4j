package exceptions;

public class IncorrectResponse extends RuntimeException {

    public IncorrectResponse(String message) {
        super(message);
    }
}
