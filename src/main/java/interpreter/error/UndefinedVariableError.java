package interpreter.error;



public class UndefinedVariableError extends ParserError {
    public UndefinedVariableError(String message) {
        super(message);
    }
}
