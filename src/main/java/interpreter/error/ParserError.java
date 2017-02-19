package interpreter.error;


public class ParserError {
    private final String message;

    public ParserError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return "Error: " + message;
    }
}


