package interpreter.expression;

import interpreter.Context;


public class BracketExpression implements NumberExpression {
    private final NumberExpression expression;

    public BracketExpression(NumberExpression expression) {
        this.expression = expression;
    }

    @Override
    public double value(Context context) {
        return expression.value(context);
    }

    @Override
    public double lambdaValue(Object[] context) {
        return expression.lambdaValue(context);
    }

    @Override
    public String toString() {
        return "(" + expression + ")";
    }
}
