package interpreter.statement;


import interpreter.Context;

public class NoOpExpressionStatement implements Statement {

    @Override
    public void execute(Context context) {
    }
}
