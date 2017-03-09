package interpreter.statement;


import interpreter.Context;
import interpreter.expression.NumberExpression;

/**
 * Puts expression to be print into context
 */
public class OutNumberExpressionStatement implements Statement {
    private final NumberExpression expression;

    public OutNumberExpressionStatement(NumberExpression expression) {
        this.expression = expression;
    }

    @Override
    public void execute(Context context) {
        context.addOutPutString(String.valueOf(expression.value(context)));
    }
}
