package interpreter.statement;


import interpreter.Context;
import interpreter.expression.Expression;

/**
 * Puts expression to be print into context
 */
public class OutExpressionStatement implements Statement {
    private final Expression expression;

    public OutExpressionStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute(Context context) {
        context.addOutPutString(String.valueOf(expression.value(context)));
    }
}
