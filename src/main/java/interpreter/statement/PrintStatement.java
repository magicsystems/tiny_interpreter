package interpreter.statement;


import interpreter.Context;

/**
 * Puts string constant to be print into context
 */
public class PrintStatement implements Statement {
    private final String message;

    public PrintStatement(String message) {
        this.message = message;
    }

    @Override
    public void execute(Context context) {
        context.addOutPutString(message);
    }
}
