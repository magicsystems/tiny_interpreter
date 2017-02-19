package interpreter.expression;

import interpreter.Context;


public class BracketExpression implements NumberExpression {
    private final Expression expression;

    public BracketExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public double value(Context context) {
        return expression.value(context);
    }

    @Override
    public String toString() {
        return "(" + expression + ")";
    }
}
